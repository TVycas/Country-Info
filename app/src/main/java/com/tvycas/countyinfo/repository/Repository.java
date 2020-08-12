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
    //TODO rename?
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

    public LiveData<List<CountryBase>> getAllCountries() {
        refreshCountryInfo();
        return countryBaseDao.getAllCountries();
    }

    private void refreshCountryInfo() {
        Call<List<CountryBase>> call = countryInfoApiService.getAllCountriesBaseInfo("name;capital;population");

        call.enqueue(new Callback<List<CountryBase>>() {
            @Override
            public void onResponse(Call<List<CountryBase>> call, Response<List<CountryBase>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: NOT SUCCESSFUL " + response.code() + " " + call.request().url());
                    return;
                }

                ArrayList<CountryBase> countryInfoList = new ArrayList<>(response.body());
                insertAllSimpleCountriesToDb(countryInfoList);
            }


            @Override
            public void onFailure(Call<List<CountryBase>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void insertAllSimpleCountriesToDb(ArrayList<CountryBase> countryInfoList) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (CountryBase countryBase : countryInfoList) {
                    countryBaseDao.insertCountry(countryBase);
                }
            }
        });
    }

    public LiveData<CountryInfoWithMap> getCountryInfoWithMap(String name) {
        addCountryInfoWithMap(name);
        return countryInfoWithMapDao.getSpecificCountry(name);
    }

    private void addCountryInfoWithMap(String name) {
        Call<List<CountryInfo>> countryInfoCall = countryInfoApiService.getFullCountryInfo(name, "name;capital;population;alpha3Code;nativeName;languages;currencies");
        Call<List<BoundingBox>> countryBoundingBoxCall = countryBoundingBoxApiService.getPosts(name);

        constructCountryInfoWithMap(countryInfoCall, countryBoundingBoxCall, new ApiCallback<CountryInfoWithMap>() {
            @Override
            public void onComplete(Result<CountryInfoWithMap> result) {
                if (result instanceof Result.Success) {
                    CountryInfoWithMap countryInfoWithMap = ((Result.Success<CountryInfoWithMap>) result).data;
                    Log.d(TAG, "onComplete: " + countryInfoWithMap.getBoundingBox());

                    countryInfoWithMapDao.insertCountry(countryInfoWithMap);
                } else {
                    //TODO error handling
                    ((Result.Error<CountryInfoWithMap>) result).exception.printStackTrace();
                }
            }
        });
    }

    private void constructCountryInfoWithMap(final Call<List<CountryInfo>> countryInfoCall,
                                             final Call<List<BoundingBox>> countryBoundingBoxCall,
                                             final ApiCallback<CountryInfoWithMap> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<List<CountryInfo>> countryInfoResponse = countryInfoCall.execute();
                    Response<List<BoundingBox>> countryBoundingBoxResponse = countryBoundingBoxCall.execute();

                    if (!countryInfoResponse.isSuccessful()) {
                        Log.d(TAG, "onResponse: CountryInfoResponse is NOT SUCCESSFUL " + countryInfoResponse.code() + " " + countryInfoCall.request().url());
                        callback.onComplete(new Result.Error<>(new Exception()));
                        return;
                    } else if (!countryBoundingBoxResponse.isSuccessful()) {
                        Log.d(TAG, "onResponse: countryBoundingBoxResponse is NOT SUCCESSFUL " + countryBoundingBoxResponse.code() + " " + countryBoundingBoxCall.request().url());
                        callback.onComplete(new Result.Error<>(new Exception()));
                        return;
                    }

                    CountryInfo countryInfo = countryInfoResponse.body().get(0);
                    BoundingBox boundingBox = countryBoundingBoxResponse.body().get(0);
                    CountryInfoWithMap countryInfoWithMap = new CountryInfoWithMap(countryInfo, boundingBox);

                    callback.onComplete(new Result.Success<>(countryInfoWithMap));
                } catch (IOException e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

//    private void insertFullCountry(CountryInfo countryInfo) {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                long response = countryInfoWithMapDao.insertCountry(countryInfo);
//                Log.d(TAG, "run: " + response);
//            }
//        });
//    }
}
