package com.tvycas.countyinfo.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.tvycas.countyinfo.R;
import com.tvycas.countyinfo.model.BoundingBox;
import com.tvycas.countyinfo.model.CountryInfoWithMap;
import com.tvycas.countyinfo.model.Language;
import com.tvycas.countyinfo.viewmodel.CountryViewModel;

import java.text.DecimalFormat;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CountryInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = CountryInfoActivity.class.getName();
    private static final String MAPVIEW_BUNDLE_KEY = "map_bundle_key";
    private GoogleMap mMap;
    private MapView mapView;
    private CountryViewModel viewModel;

    private TextView name;
    private TextView capital;
    private TextView nativeName;
    private TextView population;
    private TextView currency;
    private TextView languages;
    private TextView countryCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_info);

        mapView = findViewById(R.id.map_view);

        name = findViewById(R.id.name);
        capital = findViewById(R.id.capital);
        countryCode = findViewById(R.id.code);
        population = findViewById(R.id.population);
        nativeName = findViewById(R.id.native_name);
        currency = findViewById(R.id.currency);
        languages = findViewById(R.id.languages);


        String countryName = "Country not found";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            countryName = extras.getString("country_name"); // retrieve the data using keyName
        }

        initGoogleMap(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CountryViewModel.class);
        observeCountryInfo(countryName);
    }

    private void observeCountryInfo(String name) {
        viewModel.getCountryInfoWithMap(name).observe(this, new Observer<CountryInfoWithMap>() {
            @Override
            public void onChanged(CountryInfoWithMap countryInfoWithMap) {
                if (countryInfoWithMap != null) {
                    Log.d(TAG, "onChanged: countryWithMap" + countryInfoWithMap.getName() + " " + countryInfoWithMap.getBoundingBox());
                    moveCameraToCountry(countryInfoWithMap.getBoundingBox());
                    updateTextViews(countryInfoWithMap);
                }
            }
        });
    }

    private void updateTextViews(CountryInfoWithMap countryInfoWithMap) {
        name.setText(countryInfoWithMap.getName());
        nativeName.setText(getString(R.string.native_name, countryInfoWithMap.getNativeName()));
        population.setText(getString(R.string.population, formatPopulation(countryInfoWithMap.getPopulation())));
        currency.setText(getString(R.string.currency, countryInfoWithMap.getCurrency().getName(), countryInfoWithMap.getCurrency().getSymbol()));
        countryCode.setText(getString(R.string.country_code, countryInfoWithMap.getCountryCode()));
        capital.setText(getString(R.string.capital, countryInfoWithMap.getCapital()));
        languages.setText(getString(R.string.languages, constructLangsString(countryInfoWithMap)));
    }

    public static String formatPopulation(int population) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(population);
    }

    private String constructLangsString(CountryInfoWithMap countryInfoWithMap) {
        StringBuilder sb = new StringBuilder();
        for (Language lang : countryInfoWithMap.getLangs()) {
            sb.append(lang.getName());
            sb.append(", ");
        }

        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }

    private void moveCameraToCountry(BoundingBox boundingBox) {
        if (boundingBox != null) {
            //Set the camera of the map
            double minLat = boundingBox.getMinLat();
            double maxLat = boundingBox.getMaxLat();
            double minLng = boundingBox.getMinLng();
            double maxLng = boundingBox.getMaxLng();

            LatLng northEast = new LatLng(minLat, minLng);
            LatLng southWest = new LatLng(maxLat, maxLng);

            LatLngBounds mMapBoundary = new LatLngBounds(
                    northEast,
                    southWest
            );

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.10); // offset from edges of the map 12% of screen
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary, width, height, padding));
        } else {
            Toast.makeText(this, R.string.no_map_info, Toast.LENGTH_LONG).show();
        }
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}