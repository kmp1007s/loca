package com.example.myapp.loca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapp.loca.service.ForegroundService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService();
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        startForegroundService(serviceIntent);
    }
}
