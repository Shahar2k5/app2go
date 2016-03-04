package com.example.android.app2go;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Intent intent = new Intent(MainActivity.this, ChoosePlacesActivity.class);
        //  startActivity(intent);
        startAnimaion();

       /* //TODO: Remove and get from server
        JSONObject serverResponse;
        try {
            serverResponse = new JSONObject("{\"optimizedRoute\":[{\"source\":\"כרם התימנים 12, תל אביב יפו\",\"destination\":\"גלבוע 11, כוכב יאיר צור יגאל\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":2570,\"durationText\":\"43 mins\"},{\"source\":\"גלבוע 11, כוכב יאיר צור יגאל\",\"destination\":\"רוטשילד 20, כפר סבא\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":1186,\"durationText\":\"20 mins\"},{\"source\":\"רוטשילד 20, כפר סבא\",\"destination\":\"כרם התימנים 12, תל אביב יפו\",\"endLatitude\":0,\"endLongitude\":0,\"duration\":2152,\"durationText\":\"36 mins\"}],\"totalTime\":5908}");
            Intent intent = new Intent(this, LocationsActivity.class);
            intent.putExtra("json", serverResponse.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    private void startAnimaion() {
        ImageView center = (ImageView) findViewById(R.id.center);
        center.setScaleX(0);
        center.setScaleY(0);
        center.animate().scaleX(1).scaleY(1).setDuration(2500).setInterpolator(new BounceInterpolator());

        ImageView left_up = (ImageView) findViewById(R.id.left_up);
        left_up.setScaleX(0);
        left_up.setScaleY(0);
        left_up.animate().scaleX(1).scaleY(1).setDuration(1500).setStartDelay(700).setInterpolator(new BounceInterpolator());

        ImageView left_center = (ImageView) findViewById(R.id.left_center);
        left_center.setScaleX(0);
        left_center.setScaleY(0);
        left_center.animate().scaleX(1).scaleY(1).setDuration(1700).setStartDelay(500).setInterpolator(new BounceInterpolator());

        ImageView left_down = (ImageView) findViewById(R.id.left_down);
        left_down.setScaleX(0);
        left_down.setScaleY(0);
        left_down.animate().scaleX(1).scaleY(1).setDuration(1500).setStartDelay(1700).setInterpolator(new BounceInterpolator());

        ImageView right_up = (ImageView) findViewById(R.id.right_up);
        right_up.setScaleX(0);
        right_up.setScaleY(0);
        right_up.animate().scaleX(1).scaleY(1).setDuration(1400).setStartDelay(1100).setInterpolator(new BounceInterpolator());

        ImageView right_center = (ImageView) findViewById(R.id.right_center);
        right_center.setScaleX(0);
        right_center.setScaleY(0);
        right_center.animate().scaleX(1).scaleY(1).setDuration(1500).setStartDelay(1300).setInterpolator(new BounceInterpolator());

        ImageView right_down = (ImageView) findViewById(R.id.right_down);
        right_down.setScaleX(0);
        right_down.setScaleY(0);
        right_down.animate().scaleX(1).scaleY(1).setDuration(1400).setStartDelay(1600).setInterpolator(new BounceInterpolator());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openLocations(null);
            }
        }, 3500);


    }

    public void openLocations(View v){
          Intent intent = new Intent(MainActivity.this, ChoosePlacesActivity.class);
          startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, OverlayService.class));
    }
}