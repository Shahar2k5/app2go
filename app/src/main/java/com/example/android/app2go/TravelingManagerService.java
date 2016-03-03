package com.example.android.app2go;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import fr.quentinklein.slt.LocationTracker;

public class TravelingManagerService extends Service {
    public static int NEXT_LOCATION_ROUTING_DELAY = 5000;

    public TravelingManagerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        List<LocationPoint> points = null;
        Route route = (Route) intent.getSerializableExtra("points");
        points = route.getPoints();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // You need to ask the user to enable the permissions
            } else {
                final List<LocationPoint> finalPoints = points;
                LocationTracker tracker = new LocationTracker(this) {
                    @Override
                    public void onLocationFound(Location location) {
                        Log.i("location", location.toString());
                        checkLocation(finalPoints, location);
                    }

                    @Override
                    public void onTimeout() {
                        Log.i("location tracker", "time out");
                    }
                };
                tracker.startListening();
            }
        } else {
            final List<LocationPoint> finalPoints1 = points;
            LocationTracker tracker = new LocationTracker(this) {
                @Override
                public void onLocationFound(Location location) {
                    Log.i("location", location.toString());
                    checkLocation(finalPoints1, location);
                }

                @Override
                public void onTimeout() {
                    Log.i("location tracker", "time out");
                }
            };
            tracker.startListening();
        }
        return START_NOT_STICKY;
    }

    private void checkLocation(final List<LocationPoint> points, Location location) {
        for (int i = 0; i < points.size(); i++) {
            LocationPoint p = points.get(i);
            double distance = p.getDestination(this).distanceTo(location);
            if (Math.abs(distance) < 50) {
                final int indexOfLocation = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(NEXT_LOCATION_ROUTING_DELAY);
                            openWayz(points.get(indexOfLocation));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }

    private void openWayz(LocationPoint locationPoint) {

    }
}
