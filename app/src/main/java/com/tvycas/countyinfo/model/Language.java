package com.tvycas.countyinfo.model;

import androidx.annotation.NonNull;

/**
 * A POJO for the information about the language of a country.
 */
public class Language {
    private String name;

    public Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + name;
    }
}
