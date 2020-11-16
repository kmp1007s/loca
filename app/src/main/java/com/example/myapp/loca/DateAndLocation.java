package com.example.myapp.loca;

import com.example.myapp.loca.data.entity.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public static List<DateAndLocation> makeDateAndLocationList(List<Location> locations) {
        List<DateAndLocation> dateAndLocationsForView = new ArrayList<>();
        List<Location> locationsForDateAndLocations = new ArrayList<>();

        int lastLoop = locations.size() - 1;

        for(int i = 0; i < locations.size(); i++) {
            Location currentLocation = locations.get(i);

            if(i == 0) {
                locationsForDateAndLocations.add(currentLocation);
                continue;
            }

            Location beforeLocation = locations.get(i - 1);

            LocalDate currentLocationDate =
                    LocalDateTime.parse(currentLocation.when).toLocalDate();
            LocalDate beforeLocationDate =
                    LocalDateTime.parse(beforeLocation.when).toLocalDate();

            if(currentLocationDate.equals(beforeLocationDate)) {
                locationsForDateAndLocations.add(currentLocation);
            } else {
                DateAndLocation dateAndLocation = new DateAndLocation(
                        DateUtil.format(beforeLocationDate),
                        locationsForDateAndLocations
                );

                dateAndLocationsForView.add(dateAndLocation);

                locationsForDateAndLocations = new ArrayList<>();
                locationsForDateAndLocations.add(currentLocation);
            }

            if(i == lastLoop) {
                DateAndLocation dateAndLocation = new DateAndLocation(
                        DateUtil.format(currentLocationDate),
                        locationsForDateAndLocations
                );

                dateAndLocationsForView.add(dateAndLocation);
            }
        }
        return dateAndLocationsForView;
    }

    public List<Location> getLocationsByDate(String date, List<DateAndLocation> dateAndLocations) {
        for(int i = 0; i < dateAndLocations.size(); i++) {
            if(dateAndLocations.get(i).getDate().equals(date))
                return dateAndLocations.get(i).getLocations();
        }
        return null;
    }
}
