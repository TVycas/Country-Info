package com.tvycas.countyinfo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tvycas.countyinfo.model.CountryInfo;

import java.util.List;

@Dao
public interface CountryInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCountry(CountryInfo countryInfo);

    @Query("SELECT * FROM country_info")
    LiveData<List<CountryInfo>> getAllCountries();

    @Query("SELECT * FROM country_info WHERE name = :name")
    LiveData<CountryInfo> getSpecificCountry(String name);
}
