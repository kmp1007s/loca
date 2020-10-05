package com.example.myapp.loca.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Location {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public double lat;
    public double lng;
    public String when;

    public Location() {}

    public Location(double lat, double lng, String when) {
        this.lat = lat;
        this.lng = lng;
        this.when = when;
    }
}
