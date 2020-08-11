package com.tvycas.countyinfo.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tvycas.countyinfo.database.CountryDao;
import com.tvycas.countyinfo.model.CountrySimple;
import com.tvycas.countyinfo.network.CountryInfoApiService;

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
    private CountryDao countryDao;
    private Executor executor;

    @Inject
    public Repository(CountryInfoApiService countryInfoApiService, CountryDao countryDao, Executor executor) {
        this.countryInfoApiService = countryInfoApiService;
        this.countryDao = countryDao;
        this.executor = executor;
    }

    public LiveData<List<CountrySimple>> getAllCountries() {
        refreshCountryInfo();
        return countryDao.getAllCountries();
    }

    private void refreshCountryInfo() {
        Call<List<CountrySimple>> call = countryInfoApiService.getCountryInfo("all", "name;capital;population");

        call.enqueue(new Callback<List<CountrySimple>>() {
            @Override
            public void onResponse(Call<List<CountrySimple>> call, Response<List<CountrySimple>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: NOT SUCCESSFUL " + response.code() + " " + call.request().url());
                    return;
                }

                ArrayList<CountrySimple> countryInfoList = new ArrayList<>(response.body());
                insertCountriesToDb(countryInfoList);
            }


            @Override
            public void onFailure(Call<List<CountrySimple>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    private void insertCountriesToDb(ArrayList<CountrySimple> countryInfoList) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (CountrySimple countrySimple : countryInfoList) {
                    long status = countryDao.insertCountry(countrySimple);
                }
            }
        });
    }
}
