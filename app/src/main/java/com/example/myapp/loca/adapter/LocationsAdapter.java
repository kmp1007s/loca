package com.example.myapp.loca.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.loca.DateTimeUtil;
import com.example.myapp.loca.R;
import com.example.myapp.loca.data.entity.Location;

import java.time.LocalDateTime;
import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder> {
    private List<Location> data;

    public LocationsAdapter(List<Location> data) {this.data = data;}

    class LocationsViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        TextView when;
        TextView latitude;
        TextView longitude;

        public LocationsViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.textViewAddress);
            when = itemView.findViewById(R.id.textViewWhen);
            latitude = itemView.findViewById(R.id.textViewLatitude);
            longitude = itemView.findViewById(R.id.textViewLongitude);
        }
    }

    @NonNull
    @Override
    public LocationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsViewHolder holder, int position) {
        Location location = data.get(position);

        LocalDateTime whenLocalDateTime = LocalDateTime.parse(location.when);
        String whenFormatted = DateTimeUtil.format(whenLocalDateTime);

        holder.when.setText(whenFormatted);
        holder.latitude.setText(Math.round(location.lat) + "");
        holder.longitude.setText(Math.round(location.lng) + "");
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }
}
