package com.tvycas.countyinfo.network;

import com.tvycas.countyinfo.model.CountryBase;
import com.tvycas.countyinfo.model.CountryInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CountryInfoApiService {
    @GET("all")
    Call<List<CountryBase>> getAllCountriesBaseInfo(@Query(value = "fields", encoded = true) String searchFields);

    //TODO Remove the list?
    @GET("name/{fullCountryName}")
    Call<List<CountryInfo>> getFullCountryInfo(@Path("fullCountryName") String name, @Query(value = "fields", encoded = true) String searchFields);
}
