package com.tvycas.countyinfo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tvycas.countyinfo.model.CountryBase;
import com.tvycas.countyinfo.model.CountryFull;

@Database(entities = {CountryBase.class, CountryFull.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CountryDb extends RoomDatabase {
    public abstract CountrySimpleDao countrySimpleDao();

    public abstract CountryFullDao countryFullDao();
}
