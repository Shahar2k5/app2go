package com.example.android.app2go;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class LocationsActivity extends AppCompatActivity {

    private static final String TAG = LocationsActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<LocationPoint> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        parsePoints();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new LocationsAdapter(points);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void parsePoints() {
        points = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        JsonArray jsonArray;
        JsonObject top = gson.fromJson(json, JsonObject.class);
        jsonArray = gson.fromJson(top.getAsJsonArray("optimizedRoute"), JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            LocationPoint p = gson.fromJson(jsonObject, LocationPoint.class);
            Log.d(TAG, "Location point is  : " + p.toString());
            points.add(p);
        }


    }

    private final String json = "{\"optimizedRoute\":[{\"source\":\"כרם התימנים 12, תל אביב יפו\",\"destination\":\"גלבוע 11, כוכב יאיר צור יגאל\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":2570,\"durationText\":\"43 mins\"},{\"source\":\"גלבוע 11, כוכב יאיר צור יגאל\",\"destination\":\"רוטשילד 20, כפר סבא\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":1186,\"durationText\":\"20 mins\"},{\"source\":\"רוטשילד 20, כפר סבא\",\"destination\":\"כרם התימנים 12, תל אביב יפו\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":2152,\"durationText\":\"36 mins\"}],\"totalTime\":5908}";
}
