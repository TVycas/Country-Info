package com.tvycas.countyinfo.model;

import java.util.ArrayList;

public class CountryInfoWithMap extends CountryInfo {
    BoundingBox boundingBox;

    public CountryInfoWithMap(ArrayList<Currency> currencies, ArrayList<Language> lang, String name,
                              String countryCode, String capital, int population, String nativeName, BoundingBox boundingBox) {
        super(currencies, lang, name, countryCode, capital, population, nativeName);
        this.boundingBox = boundingBox;
    }

    public CountryInfoWithMap(CountryInfo countryInfo, BoundingBox boundingBox) {
        super(countryInfo.getCurrencies(), countryInfo.getLang(),
                countryInfo.getName(), countryInfo.getCountryCode(),
                countryInfo.getCapital(), countryInfo.getPopulation(),
                countryInfo.getNativeName());

        this.boundingBox = boundingBox;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
