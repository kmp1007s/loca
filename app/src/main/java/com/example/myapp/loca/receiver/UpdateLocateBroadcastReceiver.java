package com.example.myapp.loca.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.myapp.loca.GpsTracker;
import com.example.myapp.loca.data.AppDatabase;
import com.example.myapp.loca.data.entity.Location;

import java.time.LocalDateTime;

public class UpdateLocateBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "UpdateLocateBroadcastReceiver";
    private AppDatabase db;

    @Override
    public void onReceive(Context context, Intent intent) {
        db = AppDatabase.getInstance(context);

        LocalDateTime nowDateTime = LocalDateTime.now();
        Log.i(TAG, nowDateTime.toString());

        GpsTracker tracker = new GpsTracker(context);

        double latitude = tracker.getLatitude();
        double longitude = tracker.getLongitude();

        Log.i(TAG, "latitude = " + latitude + ", longitude = " + longitude);

        Location locationToAdd = new Location(latitude, longitude, nowDateTime.toString());
        new AddLocationTask().execute(locationToAdd);

        Toast.makeText(context, "위치를 저장했습니다!", Toast.LENGTH_LONG).show();
    }

    class AddLocationTask extends AsyncTask<Location, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Location... locations) {
            db.getLocationDao().addLocation(locations[0]);
            return null;
        }
    }
}
