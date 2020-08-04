package com.tvycas.countyinfo.model;

public class Country {
    private String name;
    private String nativeName;
    private String countryCode;
    private String capital;
    private int population;
    private String currency;
    private String lang;

    public Country(String name, String nativeName, String countryCode, String capital, int population, String currency, String lang) {
        this.name = name;
        this.nativeName = nativeName;
        this.countryCode = countryCode;
        this.capital = capital;
        this.population = population;
        this.currency = currency;
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCapital() {
        return capital;
    }

    public int getPopulation() {
        return population;
    }

    public String getCurrency() {
        return currency;
    }

    public String getLang() {
        return lang;
    }
}
