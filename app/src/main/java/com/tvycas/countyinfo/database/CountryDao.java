package com.tvycas.countyinfo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tvycas.countyinfo.model.CountrySimple;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCountry(CountrySimple countrySimple);

    @Query("SELECT * FROM countries")
    LiveData<List<CountrySimple>> getAllCountries();
}
