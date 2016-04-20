package laurenyew.weatherapp.network.listeners;

import laurenyew.weatherapp.network.responses.CurrentWeatherConditionsResponse;

/**
 * Created by laurenyew on 4/19/16.
 */
public class CurrentWeatherConditionsResponseListener extends JsonResponseListener<CurrentWeatherConditionsResponse> {
    @Override
    public void onSuccess(CurrentWeatherConditionsResponse data) {
        System.out.println("onSuccess: " + data);
    }
}
