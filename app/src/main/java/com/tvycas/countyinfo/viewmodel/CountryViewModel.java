package com.tvycas.countyinfo.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tvycas.countyinfo.model.CountryBase;
import com.tvycas.countyinfo.model.CountryInfoWithMap;
import com.tvycas.countyinfo.repository.Repository;

import java.util.List;


public class CountryViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<CountryBase>> allCountries;

    @ViewModelInject
    public CountryViewModel(Repository repository) {
        this.repository = repository;
        this.allCountries = repository.getAllCountries();
    }

    public LiveData<List<CountryBase>> getAllCountries() {
        return allCountries;
    }

    public LiveData<CountryInfoWithMap> getCountryInfoWithMap(String name) {
        return repository.getCountryInfoWithMap(name);
    }
}
