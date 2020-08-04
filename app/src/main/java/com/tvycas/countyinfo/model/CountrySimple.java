package com.tvycas.countyinfo.model;

public class CountrySimple {
    String name;
    String capital;
    int population;

    public CountrySimple(String name, String capital, int population) {
        this.name = name;
        this.capital = capital;
        this.population = population;
    }

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
