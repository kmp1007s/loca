package com.example.myapp.loca.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapp.loca.data.dao.LocationDao;
import com.example.myapp.loca.data.entity.Location;

@Database(entities = {Location.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao getLocationDao();

    private static AppDatabase instance;
    public static AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "db"
            ).build();
        }
        return instance;
    }
}
