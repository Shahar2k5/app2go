package com.example.android.app2go;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class LocationsActivity extends Activity {

    private static final String TAG = LocationsActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<LocationPoint> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        if (getIntent().getExtras() != null) {
            Log.d(TAG, "getIntent().getExtras");
            String jsonString = getIntent().getExtras().getString("json");
            parsePoints(jsonString);
        } else {
            Log.d(TAG, "getIntent().getExtras else");
            return;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new LocationsAdapter(points, this);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.startNavigationBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMyServiceRunning(TravelingManagerService.class)) {
                    Intent intent = new Intent(LocationsActivity.this, TravelingManagerService.class);
                    Route route = new Route(points);
                    intent.putExtra("points", route);
                    startService(intent);
                    startService(new Intent(LocationsActivity.this, OverlayService.class));
                }
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void parsePoints(String jsonString) {
        points = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        JsonArray jsonArray;
        JsonObject top = gson.fromJson(jsonString, JsonObject.class);
        jsonArray = gson.fromJson(top.getAsJsonArray("optimizedRoute"), JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            LocationPoint p = gson.fromJson(jsonObject, LocationPoint.class);
            Log.d(TAG, "Location point is  : " + p.toString());
            points.add(p);
        }
    }
}
