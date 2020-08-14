package com.tvycas.countyinfo.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;

/**
 * A POJO for storing the basic information about a country.
 */
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

    /*
    A helper method to format the population integer to a more human-readable form
    */
    public String formatPopulation() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(population);
    }
}
