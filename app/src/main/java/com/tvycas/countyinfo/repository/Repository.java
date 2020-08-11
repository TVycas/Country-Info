package com.tvycas.countyinfo.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tvycas.countyinfo.database.CountryFullDao;
import com.tvycas.countyinfo.database.CountrySimpleDao;
import com.tvycas.countyinfo.model.CountryBase;
import com.tvycas.countyinfo.model.CountryFull;
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
    private CountrySimpleDao countrySimpleDao;
    private CountryFullDao countryFullDao;
    private Executor executor;

    @Inject
    public Repository(CountryInfoApiService countryInfoApiService, CountrySimpleDao countrySimpleDao, CountryFullDao countryFullDao, Executor executor) {
        this.countryInfoApiService = countryInfoApiService;
        this.countrySimpleDao = countrySimpleDao;
        this.countryFullDao = countryFullDao;
        this.executor = executor;
    }

    public LiveData<List<CountryBase>> getAllCountries() {
        refreshCountryInfo();
        return countrySimpleDao.getAllCountries();
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
                    countrySimpleDao.insertCountry(countryBase);
                }
            }
        });
    }

    public LiveData<CountryFull> getFullCountryInfo(String name) {
        addFullCountryInfo(name);
        return countryFullDao.getSpecificCountry(name);
    }

    private void addFullCountryInfo(String name) {
        Call<List<CountryFull>> call = countryInfoApiService.getFullCountryInfo(name, "name;capital;population;alpha3Code;nativeName;languages;currencies");

        call.enqueue(new Callback<List<CountryFull>>() {
            @Override
            public void onResponse(Call<List<CountryFull>> call, Response<List<CountryFull>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: NOT SUCCESSFUL " + response.code() + " " + call.request().url());
                    return;
                }

                CountryFull countryFull = response.body().get(0);
                Log.d(TAG, "onResponse: " + countryFull.getCurrencies().get(0));
                insertFullCountry(countryFull);
            }


            @Override
            public void onFailure(Call<List<CountryFull>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() + " " + call.request().url());

            }
        });
    }

    private void insertFullCountry(CountryFull countryFull) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                countryFullDao.insertCountry(countryFull);
            }
        });
    }
}
