package com.ex.timesincebootcl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.ex.server_service.AIDLBridge;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    AIDLBridge aidlBridge;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlBridge = AIDLBridge.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.textView)).setText("Elapsed time since boot:\n00:00");

        Intent intent = new Intent("BootTimeService");
        intent.setPackage("com.ex.server_service");

        bindService(intent, mConnection, BIND_AUTO_CREATE);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(view -> {
            try {
                String elapsedTime = aidlBridge.calculateTimeSinceBoot();
                String textView = "Elapsed time since boot:\n" + elapsedTime;
                ((TextView)findViewById(R.id.textView)).setText(textView);
            } catch (RemoteException e) {

            }

        });
    }
}