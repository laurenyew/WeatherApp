package laurenyew.weatherapp.detail;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherDetailActivity extends AppCompatActivity {
    public static String ZIPCODE_KEY = "zipcode";
    private static String zipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        //Show back button to go back to the list
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            zipcode = getIntent().getStringExtra(ZIPCODE_KEY);

            //Update the fragment
            if (findViewById(R.id.weather_detail_fragment_container) != null) {
                WeatherDetailFragment weatherDetailFragment = new WeatherDetailFragment();
                Bundle args = new Bundle();
                args.putString(ZIPCODE_KEY, zipcode);
                weatherDetailFragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.weather_detail_fragment_container, weatherDetailFragment);
                transaction.commit();
            }

            //Change the title to the zipcode
            if (zipcode != null) {
                getSupportActionBar().setTitle(zipcode);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
