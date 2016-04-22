package laurenyew.weatherapp.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
    private List<String> sortedList = null;

    private static final String[] DEFAULT_ZIPCODES = {"75078", "78757", "92127"};

    private ZipcodeCache() {
        mCache = new HashSet<>();
        sortedList = new ArrayList<>();
    }

    public static ZipcodeCache getInstance() {
        if (mInstance == null) {
            mInstance = new ZipcodeCache();
        }
        return mInstance;
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

            //Setup the Shared preference file if it has not already been set up
            //Setup the Zipcode Cache with the values
            if (!weatherAppPref.contains(ZIPCODE_CACHE_KEY)) {
                setCache(defaultZipcodeSet);
                SharedPreferences.Editor editor = weatherAppPref.edit();
                editor.clear();
                editor.putStringSet(ZIPCODE_CACHE_KEY, defaultZipcodeSet);
                editor.commit();
            } else {
                setCache(weatherAppPref.getStringSet(ZIPCODE_CACHE_KEY, defaultZipcodeSet));
            }
        }
    }


    /**
     * Set the cache and update the sorted list
     *
     * @param cache
     */
    public void setCache(Set<String> cache) {
        mCache = (HashSet<String>) cache;
        updateSortedList();
    }

    /**
     * Add to the cache and update the sorted list
     *
     * @param zipcode
     */
    public void addZipcode(Context context, String zipcode) {
        mCache.add(zipcode);
        updateSortedList();

        //update the UI
        notifyListenersOfCacheUpdate();

        //update the shared intents (this should make an Asynchrounous call)
        SharedPreferences weatherAppPref = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = weatherAppPref.edit();
        editor.clear();
        editor.putStringSet(ZIPCODE_CACHE_KEY, mCache);
        editor.commit();
    }

    public int size() {
        if (sortedList == null) {
            return 0;
        } else {
            return sortedList.size();
        }
    }


    /**
     * Get in sorted list for use in array adapter
     *
     * @return
     */
    public String getItem(int position) {
        if (sortedList == null || position < 0 || position >= sortedList.size()) {
            return null;
        } else {
            return sortedList.get(position);
        }

    }

    /**
     * Helper method: update the sortedList with the cache values
     */
    private void updateSortedList() {
        sortedList = createSortedList(mCache);
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Zipcode Cache = [");
        Iterator<String> iter = mCache.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next() + "\n");
        }
        builder.append("]");
        return builder.toString();
    }

    //OBSERVER PATTERN

    public void addListener(UpdateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UpdateListener listUpdateListener) {
        listeners.remove(listUpdateListener);
    }

    private void notifyListenersOfCacheUpdate() {
        for (UpdateListener listener : listeners) {
            listener.onCacheUpdate();
        }
    }

}
