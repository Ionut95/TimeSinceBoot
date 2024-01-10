package com.ex.server_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.Log;

public class BootTimeService extends Service {
    public BootTimeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    private final AIDLBridge.Stub binder = new AIDLBridge.Stub() {
        @Override
        public String calculateTimeSinceBoot() throws RemoteException {
            String elapsedTime = DateUtils.formatElapsedTime(SystemClock.elapsedRealtime() / 1000);
            // Return the uptime in seconds
            return elapsedTime;
        }
    };
}