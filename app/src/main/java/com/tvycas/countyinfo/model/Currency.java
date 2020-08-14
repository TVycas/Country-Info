package com.tvycas.countyinfo.model;

import androidx.annotation.NonNull;

/**
 * A POJO for the information about the currency of a country.
 */
public class Currency {
    private String code;
    private String name;
    private String symbol;

    public Currency(String code, String name, String symbol) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
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
        return "Code: " + code + "; Name: " + name + "; Symbol: " + symbol;
    }
}
