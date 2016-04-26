package laurenyew.weatherapp.network;

/**
 * Created by laurenyew on 4/20/16.
 * <p>
 * Tags for Volley Requests based on request type.
 * These tags are attached to Volley Requests added to the queue and
 * can be used to cancel said requests.
 */
public final class WeatherServiceApiContract {
    public static final String ACTION_GET_CURRENT_CONDITIONS = "laurenyew.weatherapp.ACTION_GET_CURRENT_CONDITIONS.";
    public static final String ACTION_GET_FORECAST = "laurenyew.weatherapp.ACTION_GET_FORECAST.";
}
