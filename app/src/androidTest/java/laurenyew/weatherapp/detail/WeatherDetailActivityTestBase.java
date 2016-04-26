package laurenyew.weatherapp.detail;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.robotium.solo.Solo;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.cache.CurrentWeatherConditionsCache;
import laurenyew.weatherapp.cache.ForecastProjectionCache;

/**
 * Created by laurenyew on 4/24/16.
 * <p>
 * Test base with helper methods / setup used across tests
 */
public class WeatherDetailActivityTestBase extends ActivityInstrumentationTestCase2<WeatherDetailPagerActivity> {

    protected Solo solo;

    protected final static String ZIPCODE_KEY = "zipcode";
    protected final static String ZIPCODE = "75078";
    protected final static String EXPECTED_CITY = "Prosper, TX";
    protected int TIMEOUT_IN_MS = 2000;


    public WeatherDetailActivityTestBase() {
        super("laurenyew.weatherapp", WeatherDetailPagerActivity.class);
    }

    /**
     * Setup the Weather Detail Activity with a given zipcode
     *
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent();
        intent.putExtra(ZIPCODE_KEY, ZIPCODE);
        setActivityIntent(intent);

        //Start up the weather list activity
        solo = new Solo(getInstrumentation(), getActivity());
        getInstrumentation().waitForIdleSync();
    }

    @Override
    protected void tearDown() throws Exception {

        //Clear the detail caches to reset state
        ForecastProjectionCache.getInstance()._clear();
        CurrentWeatherConditionsCache.getInstance()._clear();

        //Clean up activities
        solo.finishOpenedActivities();
        getActivity().finish();
        super.tearDown();
    }

    protected void swipeLeftToRight() {
        solo.scrollViewToSide(solo.getView(R.id.weather_detail_pager), Solo.LEFT);
        getInstrumentation().waitForIdleSync();
    }

    protected void swipeRightToLeft() {
        solo.scrollViewToSide(solo.getView(R.id.weather_detail_pager), Solo.RIGHT);
        getInstrumentation().waitForIdleSync();
    }

    protected RecyclerView getForecastListRecyclerView() {
        return (RecyclerView) solo.getView(R.id.forecast_recyler_list_view);
    }


    protected TextView getForecastEmptyListView() {
        return (TextView) solo.getView(R.id.empty_forecast_list_view);
    }

    protected int getListItemCount() {
        return getForecastListRecyclerView().getAdapter().getItemCount();
    }
}