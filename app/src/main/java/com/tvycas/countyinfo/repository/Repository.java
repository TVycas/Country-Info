package com.tvycas.countyinfo.repository;

import android.util.Log;

import com.tvycas.countyinfo.model.CountrySimple;
import com.tvycas.countyinfo.network.CountryInfoApiService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static final String TAG = Repository.class.getName();
    private CountryInfoApiService countryInfoApiService;

    @Inject
    public Repository(CountryInfoApiService countryInfoApiService) {
        this.countryInfoApiService = countryInfoApiService;
    }

    public void apiCallTest() {
        Call<CountrySimple> call = countryInfoApiService.getCountryInfo("LTU", "name;capital;population");

        call.enqueue(new Callback<CountrySimple>() {
            @Override
            public void onResponse(Call<CountrySimple> call, Response<CountrySimple> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: NOT SUCCESSFUL " + response.code() + call.request().url());
                    return;
                }

                CountrySimple countryInfo = response.body();

                String responseStr = "Country name " + countryInfo.getName() + " capital " + countryInfo.getCapital() + " population " + countryInfo.getPopulation();

                Log.d(TAG, "onResponse: " + responseStr);
            }

            @Override
            public void onFailure(Call<CountrySimple> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }
}
