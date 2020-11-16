package com.example.myapp.loca.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.loca.DateAndLocation;
import com.example.myapp.loca.R;
import com.example.myapp.loca.data.entity.Location;

import java.util.List;

public class DateAndLocationsAdapter extends RecyclerView.Adapter<DateAndLocationsAdapter.DateAndLocationsViewHolder> {

    private List<DateAndLocation> data;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private Context context;

    public DateAndLocationsAdapter(List<DateAndLocation> dateAndLocations, Context context) {
        this.data = dateAndLocations;
        this.context = context;
    }

    class DateAndLocationsViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        RecyclerView locations;
        boolean itemShown;

        public DateAndLocationsViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.textViewDate);
            locations = itemView.findViewById(R.id.recyclerViewLocations);

            itemShown = true;
        }
    }

    @NonNull
    @Override
    public DateAndLocationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_and_location, parent, false);
        return new DateAndLocationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateAndLocationsViewHolder holder, int position) {
        DateAndLocation dateAndLocation = data.get(position);
        List<Location> emptyLocations = null;

        holder.date.setText(dateAndLocation.getDate());

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.locations.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(dateAndLocation.getLocations().size());

        LocationsAdapter locationsAdapter = new LocationsAdapter(dateAndLocation.getLocations(), context);

        holder.locations.setLayoutManager(layoutManager);
        holder.locations.setAdapter(locationsAdapter);
        holder.locations.setRecycledViewPool(viewPool);

        holder.itemView.setOnClickListener(v -> {
            final List<Location> locations = holder.itemShown ?
                    emptyLocations : dateAndLocation.getLocations();
            final LocationsAdapter newLocationsAdapter =
                    new LocationsAdapter(locations, context);

            holder.locations.setLayoutManager(layoutManager);
            holder.locations.setAdapter(newLocationsAdapter);
            holder.locations.setRecycledViewPool(viewPool);

            holder.itemShown = !holder.itemShown;
        });
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }
}
