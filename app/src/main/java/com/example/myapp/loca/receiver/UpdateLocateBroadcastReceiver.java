package com.example.myapp.loca.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDateTime;

public class UpdateLocateBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "UpdateLocateBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        Log.i(TAG, nowDateTime.toString());

        Toast.makeText(context, "위치를 저장했습니다!", Toast.LENGTH_LONG).show();
    }
}
