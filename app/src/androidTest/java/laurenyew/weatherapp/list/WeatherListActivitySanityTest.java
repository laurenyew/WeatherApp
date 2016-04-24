package laurenyew.weatherapp.list;

import android.support.v4.app.Fragment;
import android.view.View;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.detail.WeatherDetailActivity;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Sanity Unit test for WeatherListActivity
 * Tests navigation / default values
 */
public class WeatherListActivitySanityTest extends WeatherListActivityTestBase {

    /**
     * Main activity should not be null
     */
    public void testMainActivityNotNull() {
        solo.assertCurrentActivity("Expecting WeatherListActivity.", WeatherListActivity.class);
    }

    /**
     * Should load correct fragment class
     */
    public void testLoadsCorrectDefaultFragment() {
        Fragment fragment = getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.weather_list_fragment_container);
        assertTrue(fragment instanceof WeatherListFragment);
    }


    /**
     * Test default items
     */
    public void testLoadsCorrectNumDefaultListItems() {
        //check that recyclerview is no longer viewed
        assertEquals(View.VISIBLE, getListRecyclerView().getVisibility());
        assertEquals(View.GONE, getEmptyListView().getVisibility());

        //Check number of items
        assertEquals("List did not load with expected 3 items", DEFAULT_LIST_ITEMS.size(), getListItemCount());
    }

    /**
     * Sanity test go to weather detail
     */
    public void testClickZipcodeListEntryLoadsWeatherDetail() {

        // Click List item
        clickListItem(0);

        solo.waitForActivity(WeatherDetailActivity.class, TIMEOUT_IN_MS);
        solo.assertCurrentActivity("Should be taken to WeatherDetailActivity.", WeatherDetailActivity.class);

        WeatherDetailActivity detailActivity = (WeatherDetailActivity) solo.getCurrentActivity();
        // Check that Weather Detail Activity has the correct title
        assertEquals("Did not open correct list item", DEFAULT_LIST_ITEMS.get(0), detailActivity.getSupportActionBar().getTitle());
    }


}