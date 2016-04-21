package laurenyew.weatherapp.cache;

import android.util.LruCache;

import java.util.Date;

import laurenyew.weatherapp.network.responses.CurrentWeatherConditions;

/**
 * Created by laurenyew on 4/20/16.
 * <p/>
 * Basic Lru cache to hold the CurrentWeatherConditionsResponses
 * Key: zipcode
 * Value: CurrentWeatherConsitionsResponse
 */
public class CurrentWeatherConditionsCache {
    private static final int MAX_CACHE_ITEMS = 20;
    private LruCache<String, CurrentWeatherConditions> mCache;
    private static CurrentWeatherConditionsCache mInstance = null;

    private CurrentWeatherConditionsCache() {
        mCache = new LruCache<>(MAX_CACHE_ITEMS);
    }

    public static CurrentWeatherConditionsCache getInstance() {
        if (mInstance == null) {
            mInstance = new CurrentWeatherConditionsCache();
        }
        return mInstance;
    }

    public CurrentWeatherConditions getCurrentWeatherCondition(String zipcode) {
        CurrentWeatherConditions response = null;
        if (zipcode != null) {
            response = mCache.get(zipcode);
            if (response != null) {
                //evict if necessary, and if evicted, we should return a null response
                //to trigger an api call.
                if (shouldEvictResponse(response.evictionDate)) {
                    mCache.remove(zipcode);
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
    private boolean shouldEvictResponse(Date date) {
        Date currentDate = new Date();
        return date.before(currentDate);
    }

    public void addResponse(CurrentWeatherConditions response) {
        mCache.put(response.zipcode, response);
    }
}
