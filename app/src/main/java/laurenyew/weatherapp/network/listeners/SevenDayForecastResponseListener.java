package laurenyew.weatherapp.network.listeners;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import laurenyew.weatherapp.cache.CurrentWeatherConditionsCache;
import laurenyew.weatherapp.network.responses.CurrentWeatherConditions;
import laurenyew.weatherapp.network.responses.Result;

/**
 * Created by laurenyew on 4/19/16.
 * <p/>
 * This class implements:
 * - deserialization of SevenDayForecast JSON
 * - caching of seven day forecast weather conditions,
 * - updating UI with Observer pattern using FetchSevenDayForecastUpdateListener
 */
public class SevenDayForecastResponseListener extends JsonResponseListener<CurrentWeatherConditions> {

    private List<FetchCurrentWeatherUpdateListener> listeners = new ArrayList<>();

    /**
     * Add the response's data to the cache with the key being
     * the zipcode
     *
     * @param data
     */
    @Override
    public void onSuccessUpdateCache(CurrentWeatherConditions data) {
        CurrentWeatherConditionsCache.getInstance().addResponse(data);
    }

    @Override
    public void onSuccess(CurrentWeatherConditions data) {
        notifyListenersOfFetchStatus(Result.SUCCESS);
    }

    /**
     * Convert the response into a proper CurrentWeatherCondiationsResponse
     *
     * @param response
     * @return
     */
    @Override
    public CurrentWeatherConditions deserialize(JSONObject response) throws JSONException {
        CurrentWeatherConditions result = null;
        if (response != null) {
            JSONObject jsonObject = response.optJSONObject("current_observation");
            if (jsonObject != null) {
                result = parseJSONObjectResponse(jsonObject);
            }
        }
        return result;
    }

    /**
     * parse the JsonObject response into a CalendarWeatherConditionsResponse. Also set eviction date.
     *
     * @param response JSONObject
     * @return populated CalendarWeatherConditionsResponse
     * @throws JSONException
     */
    @NonNull
    private CurrentWeatherConditions parseJSONObjectResponse(JSONObject response) throws JSONException {
        CurrentWeatherConditions result = new CurrentWeatherConditions();

        //set eviction date to be 1 day after receiving this response
        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.MINUTE, 1);
        result.evictionDate = currentTime.getTime();

        //Parse the rest of the Json object
        JSONObject image_info = response.optJSONObject("image");
        result.logoImageUrl = image_info.getString("url");

        JSONObject display_location = response.getJSONObject("display_location");

        result.displayLocationFull = display_location.getString("full");
        result.zipcode = display_location.getString("zip");
        result.latitude = display_location.getDouble("latitude");
        result.longitude = display_location.getDouble("longitude");

        result.observationTime = response.getString("observation_time");

        result.weather = response.getString("weather");
        result.tempF = response.getInt("temp_f");
        result.tempC = response.getInt("temp_c");
        result.humidity = response.getString("relative_humidity");
        result.windSummary = response.getString("wind_string");

        result.weatherIconName = response.getString("icon");
        result.iconUrl = response.getString("icon_url");
        result.openForecastUrl = response.getString("forecast_url");

        return result;
    }

    public void addListener(FetchCurrentWeatherUpdateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(FetchCurrentWeatherUpdateListener listUpdateListener) {
        listeners.remove(listUpdateListener);
    }

    private void notifyListenersOfFetchStatus(Result result) {
        if (result == Result.SUCCESS) {
            for (FetchCurrentWeatherUpdateListener listener : listeners) {
                listener.onFetchComplete();
            }
        }
    }

}
