package com.tvycas.countyinfo.di;

import android.app.Application;

import androidx.room.Room;

import com.tvycas.countyinfo.database.CountryDb;
import com.tvycas.countyinfo.database.CountryFullDao;
import com.tvycas.countyinfo.database.CountrySimpleDao;

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
    public static CountrySimpleDao provideCountrySimpleDao(CountryDb countryDb) {
        return countryDb.countrySimpleDao();
    }

    @Provides
    @Singleton
    public static CountryFullDao provideCountryFullDao(CountryDb countryDb) {
        return countryDb.countryFullDao();
    }
}
