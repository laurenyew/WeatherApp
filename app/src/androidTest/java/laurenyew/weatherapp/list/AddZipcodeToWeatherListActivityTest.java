package laurenyew.weatherapp.list;

import android.view.View;
import android.widget.EditText;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.detail.WeatherDetailPagerActivity;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Sanity Unit test for Adding Zipcodes to the Zipcode List
 */
public class AddZipcodeToWeatherListActivityTest extends WeatherListActivityTestBase {

    /**
     * Should show floating action button
     */
    public void testAddZipcodeFloatingActionButtonExists() {
        assertNotNull(solo.getView(R.id.fab));
    }

    public void testSanityAddZipcodeListEntry() {
        final String ZIPCODE_TO_ADD = "75024";
        DEFAULT_LIST_ITEMS.add(0, ZIPCODE_TO_ADD);

        //open add zipcode dialog
        clickOnAddZipcodeFloatingActionButton();
        enterZipcode(ZIPCODE_TO_ADD);

        //expect to be taken to detail
        solo.waitForActivity(WeatherDetailPagerActivity.class, TIMEOUT_IN_MS);
        solo.assertCurrentActivity("Should be in Weather Detail Activity.", WeatherDetailPagerActivity.class);
        WeatherDetailPagerActivity detailActivity = (WeatherDetailPagerActivity) solo.getCurrentActivity();
        // Check that Weather Detail Activity has the correct title
        assertEquals("Did not open correct list item", ZIPCODE_TO_ADD, detailActivity.getSupportActionBar().getTitle());

        //Go back to list
        solo.goBack();
        solo.waitForActivity(WeatherListActivity.class, TIMEOUT_IN_MS);
        solo.assertCurrentActivity("Should be in Weather List Activity.", WeatherListActivity.class);

        //Check that list has correct number of items
        assertEquals(DEFAULT_LIST_ITEMS.size(), getListItemCount());//ignore header
    }

    public void testSanityAddNonZipcodeListEntry() {
        final String ZIPCODE_TO_ADD = "asdf";

        //open add zipcode dialog
        clickOnAddZipcodeFloatingActionButton();
        enterZipcode(ZIPCODE_TO_ADD);
        assertEquals("Should see input error message. ", View.VISIBLE, solo.getView(R.id.dialog_input_error).getVisibility());

        //click the cancel button
        solo.clickOnButton("CANCEL");

        //expect to be taken to list
        solo.waitForActivity(WeatherListActivity.class, TIMEOUT_IN_MS);
        solo.assertCurrentActivity("Should be in Weather List Activity.", WeatherListActivity.class);
    }


    public void testSanityZipcodeNotInAPIDatabaseListEntry() {
        final String ZIPCODE_TO_ADD = "11111";
        DEFAULT_LIST_ITEMS.add(0, ZIPCODE_TO_ADD);

        //open add zipcode dialog
        clickOnAddZipcodeFloatingActionButton();
        enterZipcode(ZIPCODE_TO_ADD);

        //expect to be taken to detail
        solo.waitForActivity(WeatherDetailPagerActivity.class, TIMEOUT_IN_MS);
        solo.assertCurrentActivity("Should be in Weather Detail Activity.", WeatherDetailPagerActivity.class);
        WeatherDetailPagerActivity detailActivity = (WeatherDetailPagerActivity) solo.getCurrentActivity();

        // Check that we got an error message
        assertTrue("Could not find the dialog!", solo.searchText("Zipcode not found in server database."));
        solo.clickOnButton("OK");

        //Go back to list
        solo.goBack();
        solo.waitForActivity(WeatherListActivity.class, TIMEOUT_IN_MS);
        solo.assertCurrentActivity("Should be in Weather List Activity.", WeatherListActivity.class);

        //Check that list has correct number of items (item added anyways)
        assertEquals(DEFAULT_LIST_ITEMS.size(), getListItemCount());//ignore header
    }

    /**
     * helper method to enter zipcode
     *
     * @param ZIPCODE_TO_ADD
     */
    private void enterZipcode(String ZIPCODE_TO_ADD) {
        EditText inputField = (EditText) solo.getView(R.id.dialog_input_text);
        solo.enterText(inputField, ZIPCODE_TO_ADD);
        solo.clickOnButton("OK");
    }

    /**
     * helper method to open add floating action button
     */
    private void clickOnAddZipcodeFloatingActionButton() {
        solo.clickOnView(solo.getView(R.id.fab));
        solo.waitForDialogToOpen(TIMEOUT_IN_MS);
    }


}