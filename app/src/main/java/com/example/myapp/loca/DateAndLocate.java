package com.example.myapp.loca;

import com.example.myapp.loca.data.entity.Location;

import java.util.List;

public class DateAndLocate {
    private String date;
    private List<Location> locations;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
