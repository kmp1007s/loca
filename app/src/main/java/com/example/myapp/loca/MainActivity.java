package com.example.myapp.loca;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.myapp.loca.adapter.DateAndLocationsAdapter;
import com.example.myapp.loca.data.AppDatabase;
import com.example.myapp.loca.data.entity.Location;
import com.example.myapp.loca.databinding.ActivityMainBinding;
import com.example.myapp.loca.service.ForegroundService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private AppDatabase db;

    private ActivityMainBinding binding;
    private DateAndLocationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(getApplicationContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewDateAndLocations.setLayoutManager(layoutManager);

        if (locationServiceEnabled()) {
            Log.i(TAG, "GPS 활성화 되있음");

            if (runTimePermissionGranted()) {
                Log.i(TAG, "Permission 허용 되있음");
            } else {
                requestRuntimePermission();
            }
        } else
            showDialogForEnableLocationService();

        startService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new LoadLocationsTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                if(locationServiceEnabled()) {
                    Log.i(TAG, "GPS 활성화 되있음");
                    return;
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {
            boolean allPermissionGranted = true;

            for(int result: grantResults) {
                if(result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionGranted = false;
                    break;
                }
            }

            if(allPermissionGranted) {

            } else {
                Toast.makeText(this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void requestRuntimePermission() {
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                PERMISSIONS_REQUEST_CODE);
    }

    private boolean runTimePermissionGranted() {
        boolean runTimePermissionGranted = false;

        int fineLocationPermissionSelfChecked =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermissionSelfChecked =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        boolean fineLocationPermissionOK =
                fineLocationPermissionSelfChecked == PackageManager.PERMISSION_GRANTED;
        boolean coarseLocationPermissionOK =
                coarseLocationPermissionSelfChecked == PackageManager.PERMISSION_GRANTED;

        if(fineLocationPermissionOK && coarseLocationPermissionOK)
            runTimePermissionGranted = true;

        return runTimePermissionGranted;
    }

    private boolean locationServiceEnabled() {
        boolean locationServiceEnabled = false;

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean gpsProviderEnabled =
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkProvider =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(gpsProviderEnabled || networkProvider)
            locationServiceEnabled = true;

        return locationServiceEnabled;
    }

    private void showDialogForEnableLocationService() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 서비스 활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", (dialog, id) -> {
            Intent callGPSEnableIntent =
                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(callGPSEnableIntent, GPS_ENABLE_REQUEST_CODE);
        });
        builder.setNegativeButton("취소", (dialog, id) -> {
            dialog.cancel();
        });
        builder.create().show();
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        startForegroundService(serviceIntent);
    }

    class LoadLocationsTask extends AsyncTask<Void, Void, List<Location>> {

        @Override
        protected void onPostExecute(List<Location> locations) {
            super.onPostExecute(locations);

            List<DateAndLocation> dateAndLocationsForView =
                    DateAndLocation.makeDateAndLocationList(locations);

            DateAndLocationsAdapter dateAndLocationsAdapter =
                    new DateAndLocationsAdapter(dateAndLocationsForView, getApplicationContext());

            binding.recyclerViewDateAndLocations.setAdapter(dateAndLocationsAdapter);
        }

        @Override
        protected List<Location> doInBackground(Void... voids) {
            return db.getLocationDao().getLocations();
        }
    }
}
