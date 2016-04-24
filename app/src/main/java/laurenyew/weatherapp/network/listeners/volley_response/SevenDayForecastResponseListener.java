package laurenyew.weatherapp.network.listeners.volley_response;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import laurenyew.weatherapp.cache.SevenDayForecastCache;
import laurenyew.weatherapp.network.listeners.ui_update.FetchRequestStatusUpdateListener;
import laurenyew.weatherapp.network.responses.models.Forecast;
import laurenyew.weatherapp.network.responses.models.SevenDayForecast;
import laurenyew.weatherapp.network.responses.status.Result;

/**
 * Created by laurenyew on 4/19/16.
 * <p/>
 * This class implements:
 * - deserialization of SevenDayForecast JSON
 * - caching of seven day forecast weather conditions,
 * - updating UI with Observer pattern using FetchSevenDayForecastUpdateListener
 */
public class SevenDayForecastResponseListener extends JsonResponseListener<SevenDayForecast> {

    private String zipcodeKey = null;
    private List<FetchRequestStatusUpdateListener> listeners = new ArrayList<>();

    public SevenDayForecastResponseListener(String zipcode) {
        zipcodeKey = zipcode;
    }

    /**
     * Add the response's data to the cache with the key being
     * the zipcode
     *
     * @param data
     */
    @Override
    public void onSuccessUpdateCache(SevenDayForecast data) {
        SevenDayForecastCache.getInstance().addResponse(data);
    }

    @Override
    public void onSuccess(SevenDayForecast data) {
        notifyListenersOfFetchStatus(Result.SUCCESS);
    }

    /**
     * Convert the response into a proper CurrentWeatherCondiationsResponse
     *
     * @param response
     * @return
     */
    @Override
    public SevenDayForecast deserialize(JSONObject response) throws JSONException {
        SevenDayForecast result = null;
        if (response != null) {
            JSONObject jsonObject = response.optJSONObject("forecast");
            if (jsonObject != null) {
                result = parseJSONObjectResponse(jsonObject);
            }
        }
        return result;
    }

    /**
     * parse the JsonObject response into a SevenDayForecast. Also set eviction date.
     *
     * @param response JSONObject
     * @return populated SevenDayForecast
     * @throws JSONException
     */
    @NonNull
    private SevenDayForecast parseJSONObjectResponse(JSONObject response) throws JSONException {
        SevenDayForecast result = new SevenDayForecast();

        //set eviction date to be 1 day after receiving this response
        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.MINUTE, 1);
        result.evictionDate = currentTime.getTime();

        //Parse the summary forecast
        JSONObject textForecast = response.optJSONObject("txt_forecast");
        JSONArray forecastSummaries = textForecast.optJSONArray("forecastday");
        JSONObject dayForecast;
        for (int index = 0; index < forecastSummaries.length(); index++) {
            dayForecast = forecastSummaries.getJSONObject(index);
            result.set(index, parseSummaryJSONObjectIntoForecast(dayForecast));
        }
        return result;
    }

    /**
     * Helper method: transforms object into a Forecast
     *
     * @param object
     * @return
     */
    private Forecast parseSummaryJSONObjectIntoForecast(JSONObject object) throws JSONException {
        Forecast forecast = new Forecast();

        forecast.iconSummary = object.getString("icon");
        forecast.iconUrl = object.getString("icon_url");
        forecast.dayOfWeek = object.getString("title");
        forecast.summaryF = object.getString("fcttext");

        return forecast;
    }

    public void addListener(FetchRequestStatusUpdateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(FetchRequestStatusUpdateListener listUpdateListener) {
        listeners.remove(listUpdateListener);
    }

    private void notifyListenersOfFetchStatus(Result result) {
        if (result == Result.SUCCESS) {
            for (FetchRequestStatusUpdateListener listener : listeners) {
                listener.onFetchComplete();
            }
        }
    }

}
