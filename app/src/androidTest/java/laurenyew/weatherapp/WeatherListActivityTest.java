package laurenyew.weatherapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.List;

import laurenyew.weatherapp.detail.WeatherDetailActivity;
import laurenyew.weatherapp.list.WeatherListActivity;
import laurenyew.weatherapp.list.WeatherListFragment;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Sanity Unit test for WeatherListActivity
 */
public class WeatherListActivityTest extends ActivityInstrumentationTestCase2<WeatherListActivity> {

    private Solo solo;

    private List<String> DEFAULT_LIST_ITEMS;
    private int TIMEOUT_IN_MS = 2000;

    public WeatherListActivityTest() {
        super("laurenyew.weatherapp", WeatherListActivity.class);
        DEFAULT_LIST_ITEMS = new ArrayList<>();
        DEFAULT_LIST_ITEMS.add("75078");
        DEFAULT_LIST_ITEMS.add("78757");
        DEFAULT_LIST_ITEMS.add("92127");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        Context context = getInstrumentation().getTargetContext();
        context.getSharedPreferences("laurenyew.weatherapp", Context.MODE_PRIVATE).edit().clear().commit();
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

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
     * Should show floating action button
     */
    public void testAddZipcodeFloatingActionButtonExists() {
        assertNotNull(getAddZipcodeButton());
    }

    /**
     * Test default items
     */
    public void testLoadsCorrectNumDefaultListItems() {
        //check that recyclerview is no longer viewed
        assertEquals(View.VISIBLE, getListRecyclerView().getVisibility());
        assertEquals(View.GONE, getEmptyListView().getVisibility());

        //Check number of items
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = getListRecyclerView().getAdapter();
        assertEquals("List did not load with expected 3 items", DEFAULT_LIST_ITEMS.size(), adapter.getItemCount() - 1);
    }

    /**
     * Test clear zipcode list option
     */
    public void testClearZipcodeList() {

        //Click the clear list button
        solo.sendKey(Solo.MENU);
        solo.clickOnMenuItem("Clear Zipcode List");

        solo.waitForActivity(WeatherListActivity.class);

        //check that recyclerview is no longer viewed
        assertEquals(View.GONE, getListRecyclerView().getVisibility());
        assertEquals(View.VISIBLE, getEmptyListView().getVisibility());

        //The adapter should have been updated to size of 0.
        assertEquals(0, getListRecyclerView().getAdapter().getItemCount());
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

    /**
     * Helper method to click list item at index
     *
     * @param index
     */
    private void clickListItem(int index) {
        // Check if list has at least one item to click
        View item = getListRecyclerView().getChildAt(index + 1); //ignore header
        assertNotNull(item);
        TouchUtils.clickView(this, item);
    }

    private RecyclerView getListRecyclerView() {
        return (RecyclerView) solo.getView(R.id.weather_recyler_list_view);
    }

    private View getAddZipcodeButton() {
        return getActivity().findViewById(R.id.fab);
    }

    private TextView getEmptyListView() {
        return (TextView) solo.getView(R.id.empty_zipcode_list_view);
    }
}