package com.tvycas.countyinfo.network;

import com.tvycas.countyinfo.model.CountrySimple;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CountryInfoApiService {

    @GET("{code}")
    Call<List<CountrySimple>> getCountryInfo(@Path("code") String countryCode,
                                             @Query(value = "fields", encoded = true) String searchFields);
}
