package com.tvycas.countyinfo.di;

import com.tvycas.countyinfo.network.CountryBoundingBoxApiService;
import com.tvycas.countyinfo.network.CountryInfoApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    public static CountryInfoApiService provideCountryInfoApiService() {
        return new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountryInfoApiService.class);
    }

    @Provides
    @Singleton
    public static CountryBoundingBoxApiService provideCountryBoundingBoxApiService() {
        return new Retrofit.Builder()
                .baseUrl("https://nominatim.openstreetmap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountryBoundingBoxApiService.class);
    }
}
