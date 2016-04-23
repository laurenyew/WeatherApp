package laurenyew.weatherapp;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import laurenyew.weatherapp.list.WeatherListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Sanity Unit test for WeatherListActivity
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class OpenWeatherListSanityTest {

    private static List<String> DEFAULT_LIST_ITEMS;

    @Rule
    public ActivityTestRule<WeatherListActivity> mActivityRule = new ActivityTestRule<>(
            WeatherListActivity.class);

    @Before
    public void initDefaultListItems() {
        DEFAULT_LIST_ITEMS = new ArrayList<>();
        DEFAULT_LIST_ITEMS.add("75078");
        DEFAULT_LIST_ITEMS.add("78757");
        DEFAULT_LIST_ITEMS.add("92127");
    }

    @Test
    public void weatherListOpensWithDefaultValues() {
        //Check that we see the recycler list view not the empty list
        ViewInteraction emptyList = onView(withId(R.id.empty_zipcode_list_view));
        emptyList.check(ViewAssertions.doesNotExist());
        ViewInteraction listView = onView(withId(R.id.weather_recyler_list_view));
//        listView.perform()
//
//        //click the button to show the fragment
//        onView((withId(R.id.btnShowTestFragment))).perform(click());
//
//        //check the fragments text is now visible in the activity
//        fragmentText.check(ViewAssertions.matches(isDisplayed()));
//
//        onView(withId(R.id.recyclerView)).perform(
//                RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }


}
