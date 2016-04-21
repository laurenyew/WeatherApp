package laurenyew.weatherapp.list;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Arrays;
import java.util.HashSet;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.cache.ZipcodeCache;
import laurenyew.weatherapp.util.AlertDialogUtil;

public class WeatherListActivity extends AppCompatActivity {
    private static final String[] defaultZipcodes = {"75078", "78757", "92127"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        if (savedInstanceState == null) {

            initListCache();
            //Update the fragment
            if (findViewById(R.id.weather_list_fragment_container) != null) {
                WeatherListFragment weatherListFragment = new WeatherListFragment();
                Bundle args = new Bundle();
                weatherListFragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.weather_list_fragment_container, weatherListFragment);
                transaction.commit();
            }
        }

        //Setup toolbar and Floating Action Button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new AddZipcodeOnClickListener());
    }

    /**
     * We store the list items in the sharedPreferences
     * (only keeping zipcode strings for now so SqliteDatabase is unnecessary overhead)
     * <p/>
     * Update list cache with the current sharedPreferences
     */
    private void initListCache() {

        //if the Shared Preferences default values have not already been set, set them
        String appName = getString(R.string.app_name);
        if (appName != null) {
            //Load up the ZipCode Cache with the Shared preferences values
            SharedPreferences weatherAppPref = getSharedPreferences(appName, MODE_PRIVATE);
            String zipcodeCacheKey = getString(R.string.shared_preferences_zipcode_cache_key);
            if (zipcodeCacheKey != null) {

                HashSet<String> defaultZipcodeSet = new HashSet<String>(Arrays.asList(defaultZipcodes));

                //Setup the Shared preference file if it has not already been set up
                //Setup the Zipcode Cache with the values
                if (!weatherAppPref.contains(zipcodeCacheKey)) {
                    SharedPreferences.Editor editor = weatherAppPref.edit();
                    editor.putStringSet(zipcodeCacheKey, defaultZipcodeSet);
                    editor.apply();
                    ZipcodeCache.getInstance().setCache(defaultZipcodeSet);
                } else {
                    ZipcodeCache.getInstance().setCache(weatherAppPref.getStringSet(zipcodeCacheKey, defaultZipcodeSet));
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * On click of the Floating Action Button, pop up a dialog to add a zipcode
     */
    private class AddZipcodeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            AlertDialogUtil.showAddZipcodeAlertDialog(
                    WeatherListActivity.this,
                    getString(R.string.add_zipcode_dialog_title),
                    R.layout.dialog_input_text_view,
                    getString(R.string.submit_button_title),
                    getString(R.string.cancel_button_title));
        }
    }
}
