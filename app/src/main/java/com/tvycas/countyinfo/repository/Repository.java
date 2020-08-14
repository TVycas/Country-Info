package com.tvycas.countyinfo.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tvycas.countyinfo.database.CountryBaseDao;
import com.tvycas.countyinfo.database.CountryInfoWithMapDao;
import com.tvycas.countyinfo.model.BoundingBox;
import com.tvycas.countyinfo.model.CountryBase;
import com.tvycas.countyinfo.model.CountryInfo;
import com.tvycas.countyinfo.model.CountryInfoWithMap;
import com.tvycas.countyinfo.network.CountryBoundingBoxApiService;
import com.tvycas.countyinfo.network.CountryInfoApiService;
import com.tvycas.countyinfo.util.ApiCallback;
import com.tvycas.countyinfo.util.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static final String TAG = Repository.class.getName();

    private CountryInfoApiService countryInfoApiService;
    private CountryBoundingBoxApiService countryBoundingBoxApiService;

    private CountryBaseDao countryBaseDao;
    private CountryInfoWithMapDao countryInfoWithMapDao;

    private Executor executor;

    @Inject
    public Repository(CountryInfoApiService countryInfoApiService, CountryBoundingBoxApiService countryBoundingBoxApiService,
                      CountryBaseDao countryBaseDao, CountryInfoWithMapDao countryInfoWithMapDao, Executor executor) {
        this.countryInfoApiService = countryInfoApiService;
        this.countryBoundingBoxApiService = countryBoundingBoxApiService;

        this.countryBaseDao = countryBaseDao;
        this.countryInfoWithMapDao = countryInfoWithMapDao;

        this.executor = executor;
    }

    /**
     * While providing the list of countries, this method also refreshes the data of countries stored in the database asynchronously.
     *
     * @return A LiveData of List<CountryBase>.
     */
    public LiveData<List<CountryBase>> getAllCountries() {
        asyncRefreshCountryBaseInfo();
        return countryBaseDao.getAllCountries();
    }

    /**
     * Uses a Call.enqueue() method to asynchronously get the list of CountryBase objects and inserts it to the database.
     */
    private void asyncRefreshCountryBaseInfo() {
        Call<List<CountryBase>> call = countryInfoApiService.getAllCountriesBaseInfo("name;capital;population");

        //This call is executed asynchronously
        call.enqueue(new Callback<List<CountryBase>>() {
            @Override
            public void onResponse(Call<List<CountryBase>> call, Response<List<CountryBase>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: NOT SUCCESSFUL " + response.code() + " " + call.request().url());
                    return;
                }

                if (response.body() != null) {
                    ArrayList<CountryBase> countryInfoList = new ArrayList<>(response.body());
                    asyncInsertAllSimpleCountriesToDb(countryInfoList);
                }
            }


            @Override
            public void onFailure(Call<List<CountryBase>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    /**
     * Asynchronously inserts a list of CountryBase objects to the database.
     *
     * @param countryBaseList The list of CountryBase objects to insert into the database.
     */
    private void asyncInsertAllSimpleCountriesToDb(List<CountryBase> countryBaseList) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                countryBaseDao.insertAllCountries(countryBaseList);
            }
        });
    }

    /**
     * While providing further info on specific country, this method also refreshes the data of the country stored in the database asynchronously.
     *
     * @param name The name of the country to get the information for.
     * @return A LiveData of CountryInfoWithMap.
     */
    public LiveData<CountryInfoWithMap> getCountryInfoWithMap(String name) {
        addCountryInfoWithMap(name);
        return countryInfoWithMapDao.getSpecificCountry(name);
    }

    /**
     * Creates two calls to different REST api services to construct the CountryInfoWithMap POJO and stores it in the database.
     *
     * @param name The name of the country to get the information for.
     */
    private void addCountryInfoWithMap(String name) {
        Call<List<CountryInfo>> countryInfoCall = countryInfoApiService.getFullCountryInfo(name, "name;capital;population;alpha3Code;nativeName;languages;currencies");
        Call<List<BoundingBox>> countryBoundingBoxCall = countryBoundingBoxApiService.getPosts(name);

        asyncConstructCountryInfoWithMap(countryInfoCall, countryBoundingBoxCall, new ApiCallback<CountryInfoWithMap>() {
            @Override
            public void onComplete(Result<CountryInfoWithMap> result) {
                if (result instanceof Result.Success) {
                    CountryInfoWithMap countryInfoWithMap = ((Result.Success<CountryInfoWithMap>) result).data;

                    Log.d(TAG, "onComplete: Api response for " + countryInfoWithMap.getName() + " is successful");

                    countryInfoWithMapDao.insertCountry(countryInfoWithMap);
                } else {
                    //TODO error handling
                    Log.d(TAG, "onComplete: Api response is not successful");
                    ((Result.Error<CountryInfoWithMap>) result).exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Uses an executor to asynchronously execute calls to two REST api services and constructs a single CountryInfoWithMap object to store the data.
     *
     * @param countryInfoCall        A call object to the first api.
     * @param countryBoundingBoxCall A call object to the second api.
     * @param callback               A callback used to return either the created object or and exception.
     */
    private void asyncConstructCountryInfoWithMap(final Call<List<CountryInfo>> countryInfoCall,
                                                  final Call<List<BoundingBox>> countryBoundingBoxCall,
                                                  final ApiCallback<CountryInfoWithMap> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<List<CountryInfo>> countryInfoResponse = countryInfoCall.execute();
                    Response<List<BoundingBox>> countryBoundingBoxResponse = countryBoundingBoxCall.execute();

                    callback.onComplete(getResultOfCountryInfoWithMap(countryInfoResponse, countryBoundingBoxResponse));
                } catch (IOException e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }

            /**
             * Uses the two Response objects to determine if the api call was successful and if so, return a Result.Success object with the constructed CountryInfoWithMap POJO as the data.
             * Otherwise, returns Result.Error object with an exception.
             *
             * @param countryInfoResponse        The fist response object.
             * @param countryBoundingBoxResponse The second response object.
             * @return Either a Result.Success or Result.Error objects with CountryInfoWithMap POJO or an exception accordingly.
             */
            private Result<CountryInfoWithMap> getResultOfCountryInfoWithMap(Response<List<CountryInfo>> countryInfoResponse, Response<List<BoundingBox>> countryBoundingBoxResponse) {
                if (countryInfoResponse.body().size() == 0 || !countryInfoResponse.isSuccessful() || !countryBoundingBoxResponse.isSuccessful()) {
                    Log.d(TAG, "getResultOfCountryInfoWithMap: One of responses is NOT successful - " + countryInfoResponse.code() + " " + countryBoundingBoxResponse.code());
                    return new Result.Error<>(new Exception());
                }

                CountryInfo countryInfo = countryInfoResponse.body().get(0);
                CountryInfoWithMap countryInfoWithMap;

                // If the bounding box was found, add it to the returned object
                if (countryBoundingBoxResponse.body().size() != 0) {
                    BoundingBox boundingBox = countryBoundingBoxResponse.body().get(0);
                    countryInfoWithMap = new CountryInfoWithMap(countryInfo, boundingBox);
                } else {
                    countryInfoWithMap = new CountryInfoWithMap(countryInfo, null);
                }
                return new Result.Success<>(countryInfoWithMap);
            }
        });
    }
}
