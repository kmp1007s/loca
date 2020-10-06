package com.example.myapp.loca;

import com.example.myapp.loca.data.entity.Location;

import java.util.List;

public class DateAndLocation {
    private String date;
    private List<Location> locations;

    public DateAndLocation(String date, List<Location> locations) {
        this.date = date;
        this.locations = locations;
    }

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
