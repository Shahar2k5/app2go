package com.example.android.app2go;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, ChoosePlacesActivity.class);
        startActivity(intent);

//
//        //TODO: Remove and get from server
//        JSONObject serverResponse = null;
//        try {
//            serverResponse = new JSONObject("{\"optimizedRoute\":[{\"source\":\"כרם התימנים 12, תל אביב יפו\",\"destination\":\"גלבוע 11, כוכב יאיר צור יגאל\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":2570,\"durationText\":\"43 mins\"},{\"source\":\"גלבוע 11, כוכב יאיר צור יגאל\",\"destination\":\"רוטשילד 20, כפר סבא\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":1186,\"durationText\":\"20 mins\"},{\"source\":\"רוטשילד 20, כפר סבא\",\"destination\":\"כרם התימנים 12, תל אביב יפו\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":2152,\"durationText\":\"36 mins\"}],\"totalTime\":5908}");
//            Intent intent = new Intent(this, LocationsActivity.class);
//            intent.putExtra("json", serverResponse.toString());
//            startActivity(intent);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
}