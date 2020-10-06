package com.example.myapp.loca.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.loca.DateAndLocation;
import com.example.myapp.loca.R;

import java.util.List;

public class DateAndLocationsAdapter extends RecyclerView.Adapter<DateAndLocationsAdapter.DateAndLocationsViewHolder> {

    private List<DateAndLocation> data;
    private LocationsAdapter adapter;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public DateAndLocationsAdapter(List<DateAndLocation> dateAndLocations) {
        this.data = dateAndLocations;
    }

    class DateAndLocationsViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        RecyclerView locations;

        public DateAndLocationsViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.textViewDate);
            locations = itemView.findViewById(R.id.recyclerViewLocations);
        }
    }

    public void updateData(List<DateAndLocation> data) {
        this.data = data;
        notifyDataSetChanged();
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

        holder.date.setText(dateAndLocation.getDate());

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.locations.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(dateAndLocation.getLocations().size());

        LocationsAdapter locationsAdapter = new LocationsAdapter(dateAndLocation.getLocations());

        holder.locations.setLayoutManager(layoutManager);
        holder.locations.setAdapter(locationsAdapter);
        holder.locations.setRecycledViewPool(viewPool);

//        adapter.updateData(dateAndLocation.getLocations());
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }
}
