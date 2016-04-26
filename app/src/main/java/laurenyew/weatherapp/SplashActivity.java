package laurenyew.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import laurenyew.weatherapp.list.WeatherListActivity;

/**
 * Created by laurenyew on 4/3/16.
 * <p>
 * Shows Bottle Rocket splash screen on launch, then when app is ready,
 * go immediately to the WeatherListActivity.
 * Showing a Splash screen on initial app startup is a suggested best
 * practice according to Android documentation.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, WeatherListActivity.class);
        startActivity(intent);
        finish();
    }
}