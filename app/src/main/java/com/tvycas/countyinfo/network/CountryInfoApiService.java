package com.tvycas.countyinfo.network;

import com.tvycas.countyinfo.model.CountrySimple;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CountryInfoApiService {

    @GET("{code}")
    Call<CountrySimple> getCountryInfo(@Path("code") String countryCode,
                                       @Query(value = "fields", encoded = true) String searchFields);
}
