package com.example.android.app2go;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.List;

public class ChoosePlacesActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = ChoosePlacesActivity.class.getSimpleName();

    private Navigation navigation;
    private List<String> addresses;
    private GoogleApiClient mGoogleApiClient;
    private boolean isFirstAddress;

    private PlaceAutocompleteFragment autocompleteFragment;
    private TextView startNavigationBtn;
    private TextView addressesTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_places);
        navigation = new Navigation();
        addresses = new ArrayList<>();
        isFirstAddress = true;

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
                if(isFirstAddress) {
                    navigation.addStartAddress(place.getName().toString());
                    isFirstAddress = false;
                }
                else {
                    if(navigation.getEndAdd() != null && !navigation.getEndAdd().isEmpty()) {
//                        addresses.add(navigation.getEndAdd());
                        navigation.addAddress(navigation.getEndAdd());
                        navigation.addEndAdd(place.getName().toString());
                    }
                    else{
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
                String add = "";
                for(int i = 0; i < navigation.getAddresses().size(); i++){
                    add += navigation.getAddresses().get(i) + ", ";
                }
                String s = "Start address: " + navigation.getStartAdd() + "\n"
                        + add
                        + " End address: " + navigation.getEndAdd();

                addressesTxt.setText(s);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("ChhsePlaceesActivity", "Connection failed");
    }
}
