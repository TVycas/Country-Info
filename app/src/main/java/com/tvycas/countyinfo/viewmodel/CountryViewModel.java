package com.tvycas.countyinfo.viewmodel;

//TODO rename

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.tvycas.countyinfo.repository.Repository;


public class CountryViewModel extends ViewModel {

    Repository repository;

    @ViewModelInject
    public CountryViewModel(Repository repository) {
        this.repository = repository;
    }

    public void testApiCall() {
        repository.apiCallTest();
    }
}
