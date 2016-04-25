package laurenyew.weatherapp.detail;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/24/16.
 * <p/>
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
        TextView currentConditionsWeatherLocation = (TextView) solo.getView(R.id.weather_location);
        assertNotNull(currentConditionsWeatherLocation);
        assertEquals(EXPECTED_CITY, currentConditionsWeatherLocation.getText());
    }


}