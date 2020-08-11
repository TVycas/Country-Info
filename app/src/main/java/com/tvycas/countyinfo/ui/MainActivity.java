package com.tvycas.countyinfo.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tvycas.countyinfo.R;
import com.tvycas.countyinfo.model.CountryBase;
import com.tvycas.countyinfo.model.CountryFull;
import com.tvycas.countyinfo.viewmodel.CountryViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private CountryViewModel countryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);
        testAdditionalCall("Lithuania");
    }

    private void testApiCall() {
        countryViewModel.getAllCountries().observe(this, new Observer<List<CountryBase>>() {
            @Override
            public void onChanged(List<CountryBase> countryBases) {
//                for (CountryBase countrySimple : countryBases) {
//                    Log.d(TAG, "onChanged: " + countrySimple.getName());
//                }
                Log.d(TAG, "onChanged: " + countryBases.size());
            }
        });
    }

    private void testAdditionalCall(String name) {
        countryViewModel.getFullCountryInfo(name).observe(this, new Observer<CountryFull>() {
            @Override
            public void onChanged(CountryFull countryFull) {
//                Log.d(TAG, "onChanged: " + countryFull.getCountryCode());
            }
        });
    }
}
