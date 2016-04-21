package laurenyew.weatherapp.network.listeners;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import laurenyew.weatherapp.network.responses.CurrentWeatherConditionsResponse;

/**
 * Created by laurenyew on 4/19/16.
 */
public class CurrentWeatherConditionsResponseListener extends JsonResponseListener<CurrentWeatherConditionsResponse> {

    /**
     * Convert the response into a proper CurrentWeatherCondiationsResponse
     *
     * @param response
     * @return
     */
    @Override

    public CurrentWeatherConditionsResponse deserialize(JSONObject response) throws JSONException {
        System.out.println("deserialize response: " + response);
        CurrentWeatherConditionsResponse result = null;
        if (response != null) {
            JSONObject jsonObject = response.optJSONObject("current_observation");
            if (jsonObject != null) {
                result = parseJSONObjectResponse(jsonObject);
            }
        }
        return result;
    }

    /**
     * @param response
     * @return
     * @throws JSONException
     */
    @NonNull
    private CurrentWeatherConditionsResponse parseJSONObjectResponse(JSONObject response) throws JSONException {
        CurrentWeatherConditionsResponse result = new CurrentWeatherConditionsResponse();

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


    @Override
    public void onSuccess(CurrentWeatherConditionsResponse data) {
        System.out.println("onSuccess: " + data);

    }
}
