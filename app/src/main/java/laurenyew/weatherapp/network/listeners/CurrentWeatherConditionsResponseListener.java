package laurenyew.weatherapp.network.listeners;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
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
    public CurrentWeatherConditionsResponse deserialize(JSONObject response) {
        if (response != null) {
            int resultCount = response.optInt("conditions");
            if (resultCount > 0) {
                Gson gson = new Gson();
                JSONArray jsonArray = response.optJSONArray("current_observation");
                if (jsonArray != null) {
                    CurrentWeatherConditionsResponse[] currentWeatherConditions = gson.fromJson(jsonArray.toString(), CurrentWeatherConditionsResponse[].class);
                    if (currentWeatherConditions != null && currentWeatherConditions.length > 0) {
                        for (CurrentWeatherConditionsResponse currentWeather : currentWeatherConditions) {
                            Log.i("LOG", currentWeather.toString());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onSuccess(CurrentWeatherConditionsResponse data) {
        System.out.println("onSuccess: " + data);
    }
}
