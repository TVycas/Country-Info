package com.tvycas.countyinfo.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tvycas.countyinfo.R;
import com.tvycas.countyinfo.model.CountrySimple;
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
        testApiCall();
    }

    private void testApiCall() {
        countryViewModel.testApiCall().observe(this, new Observer<List<CountrySimple>>() {
            @Override
            public void onChanged(List<CountrySimple> countrySimples) {
//                for (CountrySimple countrySimple : countrySimples) {
//                    Log.d(TAG, "onChanged: " + countrySimple.getName());
//                }
                Log.d(TAG, "onChanged: " + countrySimples.size());
            }
        });
    }
}
