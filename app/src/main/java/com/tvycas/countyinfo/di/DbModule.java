package com.tvycas.countyinfo.di;

import android.app.Application;

import androidx.room.Room;

import com.tvycas.countyinfo.database.CountryDao;
import com.tvycas.countyinfo.database.CountryDb;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DbModule {

    @Provides
    public static Executor provideDbExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    public static CountryDb provideCountryDb(Application application) {
        return Room.databaseBuilder(application, CountryDb.class, "countries")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public static CountryDao providePokeDao(CountryDb countryDb) {
        return countryDb.countryDao();
    }
}
