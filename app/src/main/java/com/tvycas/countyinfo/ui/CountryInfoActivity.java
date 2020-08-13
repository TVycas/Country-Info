package com.tvycas.countyinfo.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.tvycas.countyinfo.R;

public class CountryInfoActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = CountryInfoActivity.class.getName();
    private static final String MAPVIEW_BUNDLE_KEY = "map_bundle_key";
    private GoogleMap mMap;
    private MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_info);

        mapView = findViewById(R.id.map_view);

        String countryName = "Country not found";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            countryName = extras.getString("country_name"); // retrieve the data using keyName
        }

        Log.d(TAG, "onCreate: " + countryName);

        initGoogleMap(savedInstanceState);
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
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
//        if(checkMapServices()){
//            if(mLocationPermissionGranted){
//                Toast.makeText(this, "PVYKO", Toast.LENGTH_SHORT).show();
//            }else{
//                getLocationPermission();
//            }
//        }
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
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        map.setMyLocationEnabled(true);

//        min latitude 39.6448625, max latitude 42.6610848, min longitude 19.1246095, max longitude 21.0574335
        // Conversion -
//        LatLng northEast = new LatLng(min latitude, min longitude);
//        LatLng southWest = new LatLng(max latitude, max longitude);


//        Set the camera of the map
        double minLat = 20.2145811;
        double maxLat = 45.7112046;
        double minLon = 122.7141754;
        double maxLon = 154.205541;

        LatLng northEast = new LatLng(minLat, minLon);
        LatLng southWest = new LatLng(maxLat, maxLon);

        LatLngBounds mMapBoundary = new LatLngBounds(
                northEast,
                southWest
        );

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.01); // offset from edges of the map 12% of screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary, width, height, padding));
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