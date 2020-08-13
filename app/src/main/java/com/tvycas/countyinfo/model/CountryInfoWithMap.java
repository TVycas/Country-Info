package com.tvycas.countyinfo.model;

import androidx.room.Entity;

import java.util.ArrayList;

@Entity(tableName = "country_info")
public class CountryInfoWithMap extends CountryInfo {
    BoundingBox boundingBox;

    public CountryInfoWithMap(Currency currency, ArrayList<Language> langs, String name,
                              String countryCode, String capital, int population, String nativeName, BoundingBox boundingBox) {
        super(new ArrayList<Currency>() {{
            add(currency);
        }}, langs, name, countryCode, capital, population, nativeName);
        this.boundingBox = boundingBox;
    }

    public CountryInfoWithMap(CountryInfo countryInfo, BoundingBox boundingBox) {
        super(countryInfo.getCurrencies(), countryInfo.getLangs(),
                countryInfo.getName(), countryInfo.getCountryCode(),
                countryInfo.getCapital(), countryInfo.getPopulation(),
                countryInfo.getNativeName());

        this.boundingBox = boundingBox;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
