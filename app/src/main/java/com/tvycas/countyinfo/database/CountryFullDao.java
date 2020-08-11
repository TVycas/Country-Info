package com.tvycas.countyinfo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tvycas.countyinfo.model.CountryFull;

import java.util.List;

@Dao
public interface CountryFullDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountry(CountryFull countryFull);

    @Query("SELECT * FROM country_full")
    LiveData<List<CountryFull>> getAllCountries();

    @Query("SELECT * FROM country_full WHERE name= :name")
    LiveData<CountryFull> getSpecificCountry(String name);
}
