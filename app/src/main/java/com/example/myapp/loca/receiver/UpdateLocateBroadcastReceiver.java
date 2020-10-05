package com.example.myapp.loca.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.myapp.loca.GpsTracker;
import com.example.myapp.loca.data.AppDatabase;
import com.example.myapp.loca.data.entity.Location;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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

        Geocoder geocoder = new Geocoder(context);
        List<Address> list = null;

        try {
            list = geocoder.getFromLocation(latitude, longitude, 10);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        if(list != null) {
            if(list.size() == 0) {
                Log.i(TAG, "해당되는 주소 정보가 없음");
            } else {
                Log.i(TAG, list.get(0).toString());
            }
        }
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
