package laurenyew.weatherapp.list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.cache.ZipcodeCache;
import laurenyew.weatherapp.util.AlertDialogUtil;

public class WeatherListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        if (savedInstanceState == null) {
            //Start up the cache
            ZipcodeCache.getInstance().initListCacheAndSharedPreferences(getApplicationContext());

            //Update the fragment
            WeatherListFragment weatherListFragment = new WeatherListFragment();
            if (findViewById(R.id.weather_list_fragment_container) != null) {
                Bundle args = new Bundle();
                weatherListFragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.weather_list_fragment_container, weatherListFragment, WeatherListFragment.class.getSimpleName());
                transaction.commit();
            }
        }

        //Setup toolbar and Floating Action Button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new AddZipcodeOnClickListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //User wants to clear the zipcode list
        if (id == R.id.action_clear_list) {
            ZipcodeCache.getInstance().clear(getApplicationContext());
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
                    getApplicationContext(),
                    getString(R.string.add_zipcode_dialog_title),
                    R.layout.dialog_input_text_view,
                    getString(R.string.submit_button_title),
                    getString(R.string.cancel_button_title));
        }
    }
}
