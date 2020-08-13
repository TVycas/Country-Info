package com.tvycas.countyinfo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tvycas.countyinfo.R;
import com.tvycas.countyinfo.model.CountryBase;

import java.util.ArrayList;
import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> implements Filterable {
    private LayoutInflater inflater;
    private List<CountryBase> countriesToDisplay;
    private OnCountryListener listener;
    private List<CountryBase> allCountries;
    //    private RecyclerView recyclerView;
    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountryBase> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allCountries);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                // Filter the list of all countries to see if the pattern is in any of the country names
                for (CountryBase country : allCountries) {
                    if (country.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(country);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // Update the displayed offers and refresh the recyclerView
            countriesToDisplay.clear();
            countriesToDisplay.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public CountryListAdapter(Context context, OnCountryListener listener) {
        inflater = LayoutInflater.from(context);
//        this.recyclerView = recyclerView;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CountryListAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.country_list_item, parent, false);
        return new CountryViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryListAdapter.CountryViewHolder holder, int position) {
        if (countriesToDisplay != null) {
            CountryBase currentCountry = countriesToDisplay.get(position);
            holder.nameTextView.setText(currentCountry.getName());
            holder.capitalTextView.setText(currentCountry.getCapital());
            holder.popTextView.setText(String.valueOf(currentCountry.getPopulation()));

        } else {
            holder.nameTextView.setText("Country unavailable");
        }
    }

    @Override
    public int getItemCount() {
        return countriesToDisplay == null ? 0 : countriesToDisplay.size();
    }

    public interface OnCountryListener {
        void onCountryClick(int position);
    }

    public void updateCountryList(List<CountryBase> countryList) {
        if (countryList != null) {
            this.countriesToDisplay = countryList;
            allCountries = new ArrayList<>(countryList);
            notifyDataSetChanged();
        }
    }

    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nameTextView;
        private final TextView capitalTextView;
        private final TextView popTextView;
        private final OnCountryListener onCountryListener;

        public CountryViewHolder(@NonNull View itemView, OnCountryListener onCountryListener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.country_name);
            capitalTextView = itemView.findViewById(R.id.capital);
            popTextView = itemView.findViewById(R.id.population);
            this.onCountryListener = onCountryListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCountryListener.onCountryClick(getAdapterPosition());
        }
    }
}
