package com.tvycas.countyinfo.util;

public interface ApiCallback<T> {
    void onComplete(Result<T> countryFull);
}
