package laurenyew.weatherapp.cache;

import laurenyew.weatherapp.network.responses.models.ForecastProjection;

/**
 * Created by laurenyew on 4/20/16.
 * <p/>
 * Basic Lru cache to hold the ForecastProjection responses
 * Key: zipcode
 * Value: ForecastProjection
 */
public class ForecastProjectionCache extends WeatherDetailsEvictionBaseCache<ForecastProjection> {
    private static ForecastProjectionCache mInstance = null;

    private ForecastProjectionCache() {
        super();
    }

    public static ForecastProjectionCache getInstance() {
        if (mInstance == null) {
            mInstance = new ForecastProjectionCache();
        }
        return mInstance;
    }

    public ForecastProjection getForecastList(String zipcode) {
        return getItem(zipcode);
    }

}
