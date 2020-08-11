package com.tvycas.countyinfo.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "country_base")
public class CountryBase {

    @NonNull
    @PrimaryKey
    private String name;
    private String capital;
    private int population;

    public CountryBase(@NonNull String name, String capital, int population) {
        this.name = name;
        this.capital = capital;
        this.population = population;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public int getPopulation() {
        return population;
    }
}
