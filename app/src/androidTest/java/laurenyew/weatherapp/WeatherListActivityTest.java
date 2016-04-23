package laurenyew.weatherapp;

import android.app.Instrumentation;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.TextView;

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
    private Instrumentation mInstrumentation;
    private WeatherListActivity mListActivity;
    private WeatherListFragment mListFragment;
    private RecyclerView mListRecyclerView;
    private TextView mEmptyListView;
    private View mClearListButton;
    private View mAddZipcodeButton;
    private View mAddZipcodeDialog;

    private static List<String> DEFAULT_LIST_ITEMS;
    private static int TIMEOUT_IN_MS = 10000;

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

        //Set up activity
        setActivityInitialTouchMode(true);
        mInstrumentation = getInstrumentation();

        mListActivity = getActivity();

        //Use test fragment
        mListFragment = new WeatherListFragment();
        mListActivity._setFragment(mListFragment, WeatherListFragment.class.getSimpleName());


        //wait for fragment to show up
        mInstrumentation.waitForIdleSync();

        //Get Activity buttons
        mClearListButton = mListActivity.findViewById(R.id.action_clear_list);
        mAddZipcodeButton = mListActivity.findViewById(R.id.fab);

        //wait for fragment to show up
        mInstrumentation.waitForIdleSync();

        //Get Fragment views
        mListRecyclerView = (RecyclerView) mListFragment.getView().findViewById(R.id.weather_recyler_list_view);
        mEmptyListView = (TextView) mListFragment.getView().findViewById(R.id.empty_zipcode_list_view);
    }

    @Override
    protected void tearDown() throws Exception {
        mInstrumentation = null;
        mListActivity.finish();
        super.tearDown();
    }

    public void testMainActivityNotNull() {
        assertNotNull("Main Activity should not be null.", mListActivity);
    }

    /**
     * Should load correct fragment class
     */
    public void testLoadsCorrectDefaultFragment() {
        Fragment fragment = mListActivity.getSupportFragmentManager()
                .findFragmentById(R.id.weather_list_fragment_container);
        assertTrue(fragment instanceof WeatherListFragment);
    }

    public void testLoadsCorrectNumDefaultListItems() {
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = mListRecyclerView.getAdapter();
        assertEquals("List did not load with expected 3 items", DEFAULT_LIST_ITEMS.size(), adapter.getItemCount() - 1);
    }

    public void testClearListActionBarOptionExists() {
        View mainActivityDecorView = mListActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(mainActivityDecorView, mClearListButton);
    }

    public void _testAddZipcodeFloatingActionButtonExists() {
        View mainActivityDecorView = mListActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(mainActivityDecorView, mAddZipcodeButton);
    }

    public void _testClickZipcodeListEntryLoadsWeatherDetail() {
        // Set up Activity Monitor
        Instrumentation.ActivityMonitor weatherDetailActivityMonitor =
                mInstrumentation.addMonitor(WeatherDetailActivity.class.getName(),
                        null, false);

        // Click List item
        clickListItem(0);

        // Wait for the Detail Activity to Load
        WeatherDetailActivity receiverActivity = (WeatherDetailActivity)
                weatherDetailActivityMonitor.waitForActivityWithTimeout(TIMEOUT_IN_MS);

        // Check the Activity has exists
        assertNotNull("WeatherDetailActivity is null", receiverActivity);

        // Check the Activity has loaded
        assertEquals("Monitor for WeatherDetailActivity has not been called",
                1, weatherDetailActivityMonitor.getHits());

        // Check that Weather Detail Activity has the correct title
        assertEquals("Did not open correct list item", DEFAULT_LIST_ITEMS.get(0), receiverActivity.getTitle());

        // Cleanup: Remove the Activity Monitor
        getInstrumentation().removeMonitor(weatherDetailActivityMonitor);
    }

    /**
     * Helper method to click list item at index
     *
     * @param index
     */
    private void clickListItem(int index) {
        // Check if list has at least one item to click
        View item = mListRecyclerView.getChildAt(index + 1); //ignore header
        assertNotNull(item);
        TouchUtils.clickView(this, item);
    }
}