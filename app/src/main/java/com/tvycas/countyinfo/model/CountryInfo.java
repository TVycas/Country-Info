package com.tvycas.countyinfo.model;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "country_info")
public class CountryInfo extends CountryBase {

    private String nativeName;
    private ArrayList<Currency> currencies;

    @SerializedName(value = "alpha3Code")
    private String countryCode;

    @SerializedName(value = "languages")
    private ArrayList<Language> lang;

    public CountryInfo(ArrayList<Currency> currencies, ArrayList<Language> lang, String name, String countryCode, String capital, int population, String nativeName) {
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
