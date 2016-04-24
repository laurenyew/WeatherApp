package laurenyew.weatherapp.cache;

import laurenyew.weatherapp.network.responses.models.SevenDayForecast;

/**
 * Created by laurenyew on 4/20/16.
 * <p/>
 * Basic Lru cache to hold the SevenDayForecast responses
 * Key: zipcode
 * Value: SevenDayForecast
 */
public class SevenDayForecastCache extends WeatherDetailsEvictionBaseCache<SevenDayForecast> {
    private static SevenDayForecastCache mInstance = null;

    private SevenDayForecastCache() {
        super();
    }

    public static SevenDayForecastCache getInstance() {
        if (mInstance == null) {
            mInstance = new SevenDayForecastCache();
        }
        return mInstance;
    }

    public SevenDayForecast getSevenDayForecast(String zipcode) {
        return getItem(zipcode);
    }

}
