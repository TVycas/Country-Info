package com.tvycas.countyinfo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tvycas.countyinfo.model.CountryBase;
import com.tvycas.countyinfo.model.CountryInfoWithMap;

@Database(entities = {CountryBase.class, CountryInfoWithMap.class}, version = 6, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CountryDb extends RoomDatabase {
    public abstract CountryBaseDao countrySimpleDao();

    public abstract CountryInfoWithMapDao countryInfoWithMapDao();
}
