package laurenyew.weatherapp.list;

import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.List;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Test base with helper methods / setup used across tests
 */
public class WeatherListActivityTestBase extends ActivityInstrumentationTestCase2<WeatherListActivity> {

    protected Solo solo;
    private final String APP_NAME = "laurenyew.weatherapp";

    protected List<String> DEFAULT_LIST_ITEMS;
    protected int TIMEOUT_IN_MS = 2000;

    public WeatherListActivityTestBase() {
        super("laurenyew.weatherapp", WeatherListActivity.class);
        DEFAULT_LIST_ITEMS = new ArrayList<>();
        DEFAULT_LIST_ITEMS.add("75078");
        DEFAULT_LIST_ITEMS.add("78757");
        DEFAULT_LIST_ITEMS.add("92127");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        //Start up the weather list activity
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {

        getActivity()._clearSharedPreferences();


        //Clean up activities
        solo.finishOpenedActivities();
        getActivity().finish();
        super.tearDown();
    }

    /**
     * Helper method to click list item at index
     *
     * @param index
     */
    protected void clickListItem(int index) {
        // Check if list has at least one item to click
        View item = getListRecyclerView().getChildAt(index + 1); //ignore header
        assertNotNull(item);
        TouchUtils.clickView(this, item);
    }

    protected RecyclerView getListRecyclerView() {
        return (RecyclerView) solo.getView(R.id.weather_recyler_list_view);
    }


    protected TextView getEmptyListView() {
        return (TextView) solo.getView(R.id.empty_zipcode_list_view);
    }

    /**
     * Helper method. -1 (ignores header on list)
     *
     * @return
     */
    protected int getListItemCount() {
        return getListRecyclerView().getAdapter().getItemCount() - 1;
    }
}