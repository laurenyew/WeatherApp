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
    private String appName = "laurenyew.weatherapp";
    private String zipcodeCacheKey = "zipcode_cache";

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

        //if the Shared Preferences default values have not already been set, set them

        if (appName != null) {
            //Load up the ZipCode Cache with the Shared preferences values
            SharedPreferences weatherAppPref = context.getSharedPreferences(appName, Context.MODE_PRIVATE);

            if (zipcodeCacheKey != null) {

                HashSet<String> defaultZipcodeSet = new HashSet<>(Arrays.asList(DEFAULT_ZIPCODES));

                //Setup the Shared preference file if it has not already been set up
                //Setup the Zipcode Cache with the values
                if (!weatherAppPref.contains(zipcodeCacheKey)) {
                    SharedPreferences.Editor editor = weatherAppPref.edit();
                    editor.putStringSet(zipcodeCacheKey, defaultZipcodeSet);
                    editor.apply();
                    setCache(defaultZipcodeSet);
                } else {
                    setCache(weatherAppPref.getStringSet(zipcodeCacheKey, defaultZipcodeSet));
                }
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

        //update the shared intents (this should make an Asynchrounous call)
        SharedPreferences weatherAppPref = context.getSharedPreferences(appName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = weatherAppPref.edit();
        editor.putStringSet(zipcodeCacheKey, mCache);
        editor.apply();
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

}
