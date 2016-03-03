package com.example.android.app2go;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ChoosePlacesActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = ChoosePlacesActivity.class.getSimpleName();
    private static final float DEFAULT_BACKOFF_MULT = 1.0f;
    private static final String URL = "http://195.28.181.78:83/api/Navigation/Complex";

    private Navigation navigation;
    private GoogleApiClient mGoogleApiClient;
    private boolean isFirstAddress;
    private JSONObject serverResponse;

    private PlaceAutocompleteFragment autocompleteFragment;
    private TextView startNavigationBtn;
    private TextView addressesTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_places);
        navigation = new Navigation();
        navigation.setId("0");
        isFirstAddress = true;
        serverResponse = new JSONObject();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        startNavigationBtn = (TextView) findViewById(R.id.startNavigationBtn);
        addressesTxt = (TextView) findViewById(R.id.addressesTxt);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName());
                if (isFirstAddress) {
                    navigation.addStartAddress(place.getName().toString());
                    isFirstAddress = false;
                } else {
                    if (navigation.getEndAdd() != null && !navigation.getEndAdd().isEmpty()) {
                        navigation.addAddress(navigation.getEndAdd());
                        navigation.addEndAdd(place.getName().toString());
                    } else {
                        navigation.addEndAdd(place.getName().toString());
                    }
                }
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        startNavigationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getRoute(navigation, URL);
                    Log.i(TAG, serverResponse.toString());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("ChhsePlaceesActivity", "Connection failed");
    }

    public void getRoute(Navigation nav, String url) throws ExecutionException, InterruptedException, JSONException {
        final long start = System.nanoTime();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<String, String>();
        Gson gson = new Gson();
        String jsonReq = gson.toJson(nav);
        params.put("navigation", jsonReq);

        JSONObject json = new JSONObject(jsonReq);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                long elapsedTime = System.nanoTime() - start;
                Log.i("Time", String.valueOf(elapsedTime / 1000000000) + " Sec");
                serverResponse = response;
                Intent intent = new Intent(ChoosePlacesActivity.this, LocationsActivity.class);
                intent.putExtra("json", serverResponse.toString());
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                try {
                    serverResponse = new JSONObject("{\"optimizedRoute\":[{\"source\":\"כרם התימנים 12, תל אביב יפו\",\"destination\":\"גלבוע 11, כוכב יאיר צור יגאל\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":2570,\"durationText\":\"43 mins\"},{\"source\":\"גלבוע 11, כוכב יאיר צור יגאל\",\"destination\":\"רוטשילד 20, כפר סבא\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":1186,\"durationText\":\"20 mins\"},{\"source\":\"רוטשילד 20, כפר סבא\",\"destination\":\"כרם התימנים 12, תל אביב יפו\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":2152,\"durationText\":\"36 mins\"}],\"totalTime\":5908}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(ChoosePlacesActivity.this, LocationsActivity.class);
                intent.putExtra("json", serverResponse.toString());
                startActivity(intent);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                return headers;
            }
        };
        req.setTag("");
        req.setRetryPolicy(new DefaultRetryPolicy(5000, 3, DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }
}
