package com.tvycas.countyinfo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tvycas.countyinfo.R;
import com.tvycas.countyinfo.model.CountryBase;

import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {
    private LayoutInflater inflater;
    private List<CountryBase> countriesToDisplay;
    private OnCountryListener listener;

    public CountryListAdapter(Context context, List<CountryBase> countriesToDisplay, OnCountryListener listener) {
        inflater = LayoutInflater.from(context);
        this.countriesToDisplay = countriesToDisplay;
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

    public void updateCountryList(List<CountryBase> countryList) {
        countriesToDisplay = countryList;
        notifyDataSetChanged();
    }

    public interface OnCountryListener {
        void onCountryClick(int position);
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
            listener.onCountryClick(getAdapterPosition());
        }
    }
}
