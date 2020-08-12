package com.tvycas.countyinfo.network;

import com.tvycas.countyinfo.model.BoundingBox;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountryBoundingBoxApiService {
    @GET("search?format=json&limit=1")
    Call<List<BoundingBox>> getPosts(@Query("country") String name);
}
