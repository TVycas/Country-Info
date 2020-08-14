package com.tvycas.countyinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tvycas.countyinfo.R;
import com.tvycas.countyinfo.adapters.CountryListAdapter;
import com.tvycas.countyinfo.model.CountryBase;
import com.tvycas.countyinfo.viewmodel.CountryViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

import static com.tvycas.countyinfo.util.Constants.COUNTRY_NAME_BUNDLE_KEY;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements CountryListAdapter.OnCountryListener {
    private static final String TAG = MainActivity.class.getName();

    private CountryViewModel countryViewModel;
    private List<CountryBase> countryList;
    private CountryListAdapter countryListAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);

        setUpRecyclerView();
        observeCountryList();
        setUpSearchView();
    }

    /**
     * Sets up the SearchView to update the adapter when the user inputs a query string.
     */
    private void setUpSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!s.equals("")) {
                    countryListAdapter.getFilter().filter(s);
                    recyclerView.scrollToPosition(0);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                countryListAdapter.getFilter().filter(s);
                recyclerView.scrollToPosition(0);
                return false;
            }
        });
    }

    /**
     * Sets up the Activity to observe and display a simple list of all countries that will be displayed.
     */
    private void observeCountryList() {
        countryViewModel.getAllCountries().observe(this, new Observer<List<CountryBase>>() {
            @Override
            public void onChanged(List<CountryBase> countryBaseList) {
                Log.d(TAG, "onChanged: Observed " + countryBaseList.size() + " countries");
                countryList = countryBaseList;
                countryListAdapter.updateCountryList(countryList);
            }
        });
    }

    /*
    Sets up the RecyclerView to use a custom RecyclerView Adapter and a Layout Manager
     */
    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.country_list_recyclerview);
        countryListAdapter = new CountryListAdapter(this, this);
        recyclerView.setAdapter(countryListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Launches a new CountryInfoActivity once the user presses one of the countries in the RecyclerView list
     *
     * @param position The position of the country in the RecyclerView list.
     */
    @Override
    public void onCountryClick(int position) {
        Intent intent = new Intent(this, CountryInfoActivity.class);
        intent.putExtra(COUNTRY_NAME_BUNDLE_KEY, countryList.get(position).getName());
        startActivity(intent);
    }
}
