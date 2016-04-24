package laurenyew.weatherapp.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.detail.current_conditions.CurrentConditionsWeatherDetailFragment;
import laurenyew.weatherapp.detail.forecast.SevenDayForecastWeatherDetailFragment;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherDetailPagerActivity extends AppCompatActivity {
    public static String ZIPCODE_KEY = "zipcode";
    private static String zipcode;

    //Views
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //Share action
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get zipcode value
        if (savedInstanceState == null) {
            zipcode = getIntent().getStringExtra(ZIPCODE_KEY);
        } else {
            zipcode = savedInstanceState.getString(ZIPCODE_KEY, zipcode);
        }

        //Setup View
        setContentView(R.layout.activity_weather_detail_pager);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar(mToolbar);

        //Set up tab view pager
        mViewPager = (ViewPager) findViewById(R.id.weather_detail_pager);
        setupViewPager(mViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.weather_detail_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ZIPCODE_KEY, zipcode);
    }

    /**
     * Set up the toolbar
     *
     * @param toolbar
     */
    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        //Show back button to go back to the list
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Change the title to the zipcode
        if (zipcode != null) {
            getSupportActionBar().setTitle(zipcode);
        }
    }

    /**
     * Set up the View pager adapter (holds the fragment),
     * and give it to the View Pager to handle
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        Bundle args = new Bundle();
        args.putString(ZIPCODE_KEY, zipcode);
        WeatherDetailViewPagerAdapter adapter = new WeatherDetailViewPagerAdapter(getSupportFragmentManager(), args);
        adapter.addFragment(new CurrentConditionsWeatherDetailFragment(), getString(R.string.current_conditions_tab_title));
        adapter.addFragment(new SevenDayForecastWeatherDetailFragment(), getString(R.string.seven_day_forecast_tab_title));
        viewPager.setAdapter(adapter);
    }

    /**
     * Set up toolbar menu and share action provider
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_weather_detail, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);
        // Fetch reference to the share action provider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        // Return true to display menu
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Helper method creates an intent for sharing forecast data
     *
     * @return
     */
    public void setWeatherDetailShareIntent(String data) {

        if (mShareActionProvider != null) {
            //Clear shared intents -- the share action provider should
            //do nothing if clicked
            if (data == null) {
                mShareActionProvider.setShareIntent(null);
            }
            //Update shared intents
            else {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Forecast Data");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, data);
                mShareActionProvider.setShareIntent(sharingIntent);
            }
        }
    }


    /**
     * Setup the back button to exit detail and go to list
     *
     * @param item
     * @return
     */
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