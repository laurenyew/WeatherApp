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
 */
public class ZipcodeCache {
    private static ZipcodeCache mInstance = null;

    public interface UpdateListener {
        void onCacheUpdate();
    }

    private List<UpdateListener> listeners = new ArrayList<UpdateListener>();

    private static final String APP_NAME = "laurenyew.weatherapp";
    private static final String ZIPCODE_CACHE_KEY = "zipcode_cache";

    //We use a set so that we can ensure that no duplicate zipcodes are added
    //This also helps with sorting the list
    private HashSet<String> mCache = null;
    //Sorted List is used by the array adapter (try to prevent re-calculation
    //of sorted list order unless adding/setting the cache)
    //This should be kept up to date with mCache
    private List<String> mSortedList = null;

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
     * Get in sorted list for use in array adapter
     *
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
     * <p/>
     * Update list cache with the current sharedPreferences
     */
    public void initListCacheAndSharedPreferences(Context context) {

        if (mCache == null || mCache.isEmpty()) {
            //Load up the ZipCode Cache with the Shared preferences values
            SharedPreferences weatherAppPref = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);

            HashSet<String> defaultZipcodeSet = new HashSet<>(Arrays.asList(DEFAULT_ZIPCODES));

            //Shared preferences has not yet been set up
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
     * Add to the cache and update the sorted list
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
     * Helper method to sort the cache into a list
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
