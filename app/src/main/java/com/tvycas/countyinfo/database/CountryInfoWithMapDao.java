package com.tvycas.countyinfo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tvycas.countyinfo.model.CountryInfoWithMap;

import java.util.List;

@Dao
public interface CountryInfoWithMapDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountry(CountryInfoWithMap countryInfo);

    @Query("SELECT * FROM country_info")
    LiveData<List<CountryInfoWithMap>> getAllCountries();

    @Query("SELECT * FROM country_info WHERE name = :name")
    LiveData<CountryInfoWithMap> getSpecificCountry(String name);
}
