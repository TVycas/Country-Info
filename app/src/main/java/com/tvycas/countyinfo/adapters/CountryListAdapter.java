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

/**
 * A custom RecyclerView.Adapter for displaying the CountryBase objects in a RecyclerView list.
 */
public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> implements Filterable {
    private LayoutInflater inflater;
    private List<CountryBase> countriesToDisplay;
    private OnCountryListener listener;
    private List<CountryBase> allCountries;

    /**
     * A filter used to filter the list on the user constraint.
     */
    private Filter countryFilter = new Filter() {
        /**
         * Filters the list of all countries to see if the pattern is in any of the country names.
         *
         * @param constraint The user provided query.
         * @return A list of filtered countryBase objects.
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountryBase> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allCountries);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

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

        /**
         * Updates the displayed countries and refreshes the RecyclerView.
         *
         * @param charSequence  The user provided query.
         * @param filterResults A list of filtered countryBase objects.
         */
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            countriesToDisplay.clear();
            countriesToDisplay.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public CountryListAdapter(Context context, OnCountryListener listener) {
        inflater = LayoutInflater.from(context);
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
            Context context = holder.itemView.getContext();
            CountryBase currentCountry = countriesToDisplay.get(position);

            //Set the ViewHolder withe the correct data
            holder.nameTextView.setText(currentCountry.getName());
            holder.capitalTextView.setText(context.getString(R.string.capital_list_item, currentCountry.getCapital()));
            holder.popTextView.setText(context.getString(R.string.population_list_item, currentCountry.formatPopulation()));
        } else {
            holder.nameTextView.setText(R.string.country_unavailable);
        }
    }

    @Override
    public int getItemCount() {
        return countriesToDisplay == null ? 0 : countriesToDisplay.size();
    }

    /**
     * Updates the list of countries displayed in the RecyclerView.
     *
     * @param countryList The list of countries to display.
     */
    public void updateCountryList(List<CountryBase> countryList) {
        if (countryList != null) {
            this.countriesToDisplay = countryList;
            allCountries = new ArrayList<>(countryList); // Make another copy of the list for filter functionality.
            notifyDataSetChanged();
        }
    }

    /**
     * Interface used to listen for user clicks on the list items.
     */
    public interface OnCountryListener {
        void onCountryClick(int position);
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
