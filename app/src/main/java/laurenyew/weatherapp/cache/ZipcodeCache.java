package laurenyew.weatherapp.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by laurenyew on 4/21/16.
 * ZipcodeCache
 * <p>
 * Zipcodes are stored in SharedPreferences so they can be available across app sessions.
 * This is a cache wrapper used to update SharedPreferences and keep around
 * and in-memory sorted list of the zipcodes that should be in sync.
 */
public class ZipcodeCache {
    private static ZipcodeCache mInstance = null;

    /**
     * Use observer pattern to notify listeners when the cache is updated
     * (ex: clear list, add item)
     */
    public interface UpdateListener {
        void onCacheUpdate();
    }

    private List<UpdateListener> listeners = new ArrayList<UpdateListener>();

    private static final String APP_NAME = "laurenyew.weatherapp";
    private static final String ZIPCODE_CACHE_KEY = "zipcode_cache";

    //We use a HashSet so that we can ensure that no duplicate zipcodes are added
    private HashSet<String> mCache = null;

    //We keep a sorted list of zipcode items
    //(calculated dynamically when a change is made to the cache).
    //This list is kept in sync with Shared Preferences changes
    private List<String> mSortedList = null;

    //3 default zipcodes that should be populated on installation of app
    private static final String[] DEFAULT_ZIPCODES = {"75078", "78757", "92127"};

    private ZipcodeCache() {
        mCache = new HashSet<>();
        mSortedList = new ArrayList<>();
    }

    public static ZipcodeCache getInstance() {
        if (mInstance == null) {
            mInstance = new ZipcodeCache();
        }
        return mInstance;
    }

    /**
     * Get zipcode for a given position from the sorted list
     *
     * @param position
     * @return
     */
    public String getItem(int position) {
        if (mSortedList == null || position < 0 || position >= mSortedList.size()) {
            return null;
        } else {
            return mSortedList.get(position);
        }

    }

    public int size() {
        if (mSortedList == null) {
            return 0;
        } else {
            return mSortedList.size();
        }
    }

    /**
     * We store the list items in the sharedPreferences
     * (only keeping zipcode strings for now so SqliteDatabase is unnecessary overhead)
     * <p>
     * Update in memory list cache with the current sharedPreferences
     * and the shared preferences with the appropriate default data as needed
     */
    public void initListCacheAndSharedPreferences(Context context) {

        if (mCache == null || mCache.isEmpty()) {
            //Load up the ZipCode Cache with the Shared preferences values
            SharedPreferences weatherAppPref = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);

            HashSet<String> defaultZipcodeSet = new HashSet<>(Arrays.asList(DEFAULT_ZIPCODES));

            //Shared preferences has not yet been add up
            if (!weatherAppPref.contains(ZIPCODE_CACHE_KEY)) {
                //update the cache and then update the shared preferences
                mCache = defaultZipcodeSet;
                updateSortedList();
                updateCacheInDB(context);
            } else {
                //otherwise, just get the cache from the Shared preferences
                mCache = (HashSet<String>) weatherAppPref.getStringSet(ZIPCODE_CACHE_KEY, defaultZipcodeSet);
                updateSortedList();
            }
        }
    }


    /**
     * Add to the in memory cache set, update the sorted list,
     * notify listeners (updating UI), and then update the Shared Preferences
     *
     * @param zipcode
     */
    public void addZipcode(Context context, String zipcode) {
        mCache.add(zipcode);
        updateSortedList();
        notifyListenersOfCacheUpdate();
        updateCacheInDB(context);
    }

    /**
     * Clear the cache, list, and Shared Preferences
     *
     * @param context
     */
    public void clear(Context context) {
        mCache.clear();
        mSortedList.clear();
        notifyListenersOfCacheUpdate();

        updateCacheInDB(context);
    }


    /**
     * Helper method to update the shared preferences with the current mCache
     * Chose to do a synchronized commit to prevent issues where the user closes
     * the app before an apply() can occur, and thus the user loses their change.
     *
     * @param context
     */
    private void updateCacheInDB(Context context) {

        //update the shared intents (this should make an Asynchrounous call)
        SharedPreferences weatherAppPref = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = weatherAppPref.edit();
        editor.clear();
        editor.putStringSet(ZIPCODE_CACHE_KEY, mCache);
        editor.commit();
    }


    /**
     * Helper method: update the mSortedList with the cache values
     */
    private void updateSortedList() {
        mSortedList = createSortedList(mCache);
    }

    /**
     * Helper method to sort the cache set into a list
     *
     * @param c
     * @param <T>
     * @return
     */
    private static <T extends Comparable<? super T>> List<T> createSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    }

    /**
     * @return String representation of the cache
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Zipcode Cache = [");
        for (String zipcode : mSortedList) {
            builder.append(zipcode + "\n");
        }
        builder.append("]");
        return builder.toString();
    }

    //OBSERVER PATTERN to update UI on Zipcode Cache change

    public void addListener(UpdateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UpdateListener listUpdateListener) {
        listeners.remove(listUpdateListener);
    }

    /**
     * Notify UI to update
     */
    private void notifyListenersOfCacheUpdate() {
        for (UpdateListener listener : listeners) {
            listener.onCacheUpdate();
        }
    }


    /**
     * For test use only: Clear the shared preference file for test reset
     *
     * @param context
     */
    public void _clearSharedPreferences(Context context) {
        //Clear shared preferences
        SharedPreferences weatherAppPref = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = weatherAppPref.edit();
        editor.clear().commit();

        //Also clear the cache
        mCache.clear();
        mSortedList.clear();
    }

}
