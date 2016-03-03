package com.example.android.app2go;


import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class TravelingManagerService extends Service {
    public static int NEXT_LOCATION_ROUTING_DELAY = 5000;

    public TravelingManagerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        List<LocationPoint> points = null;
        Route route = (Route) intent.getSerializableExtra("points");
        points = route.getPoints();
        lastDestination = points.get(0).getDestination();
        sendRoutingIntent(points.get(0));
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        }*/
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
                            sendRoutingIntent(points.get(indexOfLocation));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }

    private void sendRoutingIntent(final LocationPoint locationPoint) {
        Location source = locationPoint.getSource(getApplicationContext());
        Location destination = locationPoint.getDestination(getApplicationContext());
        String intentUrl = "http://maps.google.com/maps?saddr=" + source.getLatitude() + "," + source.getLongitude() +
                "&daddr=" + destination.getLatitude() + "," + destination.getLongitude();
               // "&daddr=32.2334178,35.0017312";
        Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(intentUrl));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i("routing url", intentUrl);
        startActivity(i);
    }

    public static String lastDestination = "";
}
