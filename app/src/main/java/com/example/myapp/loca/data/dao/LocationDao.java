package com.example.myapp.loca.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverter;

import com.example.myapp.loca.data.entity.Location;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert
    public long addLocation(Location location);

    @Query("select * from Location")
    @TypeConverter
    public List<Location> getLocations();
}
