package laurenyew.weatherapp.cache;

import android.util.LruCache;

import java.util.Date;

import laurenyew.weatherapp.network.responses.models.BaseKeyEvictionModel;

/**
 * Created by laurenyew on 4/20/16.
 * <p/>
 * Base class
 * Basic Lru cache to hold the responses
 * Key: zipcode
 * Value: CurrentWeatherConsitionsResponse
 */
public class WeatherDetailsEvictionBaseCache<T extends BaseKeyEvictionModel> {

    private static final int MAX_CACHE_ITEMS = 20;
    private LruCache<String, T> mCache;

    public WeatherDetailsEvictionBaseCache() {
        mCache = new LruCache<>(getMaxNumCacheItems());
    }

    protected int getMaxNumCacheItems() {
        return MAX_CACHE_ITEMS;
    }


    /**
     * Get the requested item (if non-expired).
     * If item is expired, return null and remove from the cache
     *
     * @param key
     * @return
     */
    protected T getItem(String key) {
        T response = null;
        if (key != null) {
            response = mCache.get(key);
            if (response != null) {
                //evict if necessary, and if evicted, we should return a null response
                //to trigger an api call.
                if (shouldEvictModel(response.evictionDate)) {
                    mCache.remove(key);
                    response = null;
                }
            }
        }
        return response;
    }

    /**
     * Given a date, check if is older than the current time. If so,
     * we should evict
     *
     * @param date
     * @return true if should evict, false otherwise
     */
    private boolean shouldEvictModel(Date date) {
        Date currentDate = new Date();
        return date.before(currentDate);
    }

    public void addBaseKeyEvictionModel(T response) {
        mCache.put(response.key, response);
    }

    public int size() {
        if (mCache == null) {
            return 0;
        } else {
            return mCache.size();
        }
    }

}
