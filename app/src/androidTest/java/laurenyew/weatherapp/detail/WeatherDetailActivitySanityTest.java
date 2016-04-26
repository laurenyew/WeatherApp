package laurenyew.weatherapp.detail;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/24/16.
 * <p>
 * Sanity Unit test for WeatherDetailPagerActivity
 * Tests navigation / default values
 */
public class WeatherDetailActivitySanityTest extends WeatherDetailActivityTestBase {

    /**
     * Main activity should not be null
     */
    public void testMainActivityNotNull() {
        solo.assertCurrentActivity("Expecting WeatherDetailPagerActivity.", WeatherDetailPagerActivity.class);
    }

    /**
     * Should load correct viewpager
     */
    public void testLoadsViewPager() {
        View viewPager = getActivity().findViewById(R.id.weather_detail_pager);
        assertTrue(viewPager instanceof ViewPager);
    }

    /**
     * Test default fragment is Current Conditions Fragment
     */
    public void testLoadsCurrentConditionsFragmentOnDefault() {
        checkOnCurrentConditionsDetailPage();
    }

    /**
     * Test that should be able to go to Forecast and back to Current Conditions
     * Weather detail using swipe.
     * Also should check that those views were populated
     */
    public void testWeatherDetailTabNavigation() {
        swipeRightToLeft();
        checkOnForecastDetailPage();
        swipeLeftToRight();
        checkOnCurrentConditionsDetailPage();
    }

    private void checkOnCurrentConditionsDetailPage() {
        TextView currentConditionsWeatherLocation = (TextView) solo.getView(R.id.weather_location);
        assertNotNull(currentConditionsWeatherLocation);
        assertEquals(EXPECTED_CITY, currentConditionsWeatherLocation.getText());
    }

    private void checkOnForecastDetailPage() {
        RecyclerView forecastRecyclerView = getForecastListRecyclerView();
        assertNotNull(forecastRecyclerView);
        assertNotSame(0, getListItemCount());
    }


}