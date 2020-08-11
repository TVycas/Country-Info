package com.tvycas.countyinfo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tvycas.countyinfo.model.CountrySimple;

@Database(entities = {CountrySimple.class}, version = 1, exportSchema = false)
public abstract class CountryDb extends RoomDatabase {
    public abstract CountryDao countryDao();
}
