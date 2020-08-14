package com.tvycas.countyinfo.model;

import androidx.annotation.NonNull;

/**
 * A POJO for the information about the currency of a country.
 */
public class Currency {
    private String name;
    private String symbol;

    public Currency(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + name + "; Symbol: " + symbol;
    }
}
