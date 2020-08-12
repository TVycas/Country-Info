package com.tvycas.countyinfo.viewmodel;

//TODO rename

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tvycas.countyinfo.model.CountryBase;
import com.tvycas.countyinfo.model.CountryInfo;
import com.tvycas.countyinfo.repository.Repository;

import java.util.List;


public class CountryViewModel extends ViewModel {
    private static final String TAG = CountryViewModel.class.getName();
    Repository repository;

    @ViewModelInject
    public CountryViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<CountryBase>> getAllCountries() {
        return repository.getAllCountries();
    }

    public LiveData<CountryInfo> getFullCountryInfo(String name) {
        return repository.getFullCountryInfo(name);
    }
}
