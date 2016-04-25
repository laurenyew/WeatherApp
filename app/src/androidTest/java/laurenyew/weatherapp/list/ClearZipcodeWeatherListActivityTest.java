package laurenyew.weatherapp.list;

import android.view.View;

import com.robotium.solo.Solo;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/23/16.
 * <p>
 * Sanity Unit test for Clearing the Zipcode list
 */
public class ClearZipcodeWeatherListActivityTest extends WeatherListActivityTestBase {

    /**
     * Test clear zipcode list option
     */
    public void testClearZipcodeList() {

        //Click the clear list button
        solo.sendKey(Solo.MENU);
        solo.clickOnText(solo.getString(R.string.action_clear_list));

        solo.waitForActivity(WeatherListActivity.class);

        //check that recyclerview is no longer viewed
        assertEquals(View.GONE, getListRecyclerView().getVisibility());
        assertEquals(View.VISIBLE, getEmptyListView().getVisibility());

        //The adapter should have been updated to size of 0.
        assertEquals(0, getListItemCount());

    }

}