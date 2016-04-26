package laurenyew.weatherapp.cache;

import laurenyew.weatherapp.network.responses.models.CurrentWeatherConditions;

/**
 * Created by laurenyew on 4/20/16.
 * <p>
 * Basic Lru cache to hold the CurrentWeatherConditions
 * Key: zipcode
 * Value: CurrentWeatherConditions
 */
public class CurrentWeatherConditionsCache extends WeatherDetailsEvictionBaseCache<CurrentWeatherConditions> {
    private static CurrentWeatherConditionsCache mInstance = null;

    private CurrentWeatherConditionsCache() {
        super();
    }

    public static CurrentWeatherConditionsCache getInstance() {
        if (mInstance == null) {
            mInstance = new CurrentWeatherConditionsCache();
        }
        return mInstance;
    }

    public CurrentWeatherConditions getCurrentWeatherCondition(String zipcode) {
        return getItem(zipcode);
    }
}
