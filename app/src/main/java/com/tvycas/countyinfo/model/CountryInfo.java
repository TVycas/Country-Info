package com.tvycas.countyinfo.model;

import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryInfo extends CountryBase {

    private String nativeName;
    private Currency currency;
    @Ignore
    private ArrayList<Currency> currencies;

    @SerializedName(value = "alpha3Code")
    private String countryCode;

    @SerializedName(value = "languages")
    private ArrayList<Language> langs;

    public CountryInfo(ArrayList<Currency> currencies, ArrayList<Language> langs, String name, String countryCode, String capital, int population, String nativeName) {
        super(name, capital, population);
        this.nativeName = nativeName;
        this.countryCode = countryCode;
        this.langs = langs;
        this.currency = currencies.get(0);
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Currency getCurrency() {
        return currency;
    }

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public ArrayList<Language> getLangs() {
        return langs;
    }
}
