package com.tvycas.countyinfo.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tvycas.countyinfo.R;
import com.tvycas.countyinfo.viewmodel.CountryViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private CountryViewModel countryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);
        testApiCall();
    }

    private void testApiCall() {

        countryViewModel.testApiCall();

    }
}
