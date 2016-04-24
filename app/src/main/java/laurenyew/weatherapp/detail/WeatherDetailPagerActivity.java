package laurenyew.weatherapp.detail;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherDetailPagerActivity extends AppCompatActivity {
    public static String ZIPCODE_KEY = "zipcode";
    private static String zipcode;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get zipcode value
        if (savedInstanceState == null) {
            zipcode = getIntent().getStringExtra(ZIPCODE_KEY);
        } else {
            zipcode = savedInstanceState.getString(ZIPCODE_KEY, zipcode);
        }

        setContentView(R.layout.activity_weather_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar(mToolbar);

        //Set up tab view pager
        mViewPager = (ViewPager) findViewById(R.id.weather_detail_pager);
        setupViewPager(mViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
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