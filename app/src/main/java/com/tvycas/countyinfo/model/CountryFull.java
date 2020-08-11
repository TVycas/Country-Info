package com.tvycas.countyinfo.model;

import androidx.room.Entity;

import java.util.ArrayList;

@Entity(tableName = "country_full")
public class CountryFull extends CountryBase {

    private String nativeName;
    private String countryCode;
    private ArrayList<Currency> currencies;
    private ArrayList<Language> lang;

    public CountryFull(ArrayList<Currency> currencies, ArrayList<Language> lang, String name, String countryCode, String capital, int population, String nativeName) {
        super(name, capital, population);
        this.nativeName = nativeName;
        this.countryCode = countryCode;
        this.currencies = currencies;
        this.lang = lang;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public ArrayList<Language> getLang() {
        return lang;
    }
}
