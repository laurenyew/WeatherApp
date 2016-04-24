package laurenyew.weatherapp.network;

import android.content.Context;

/**
 * Created by laurenyew on 4/19/16.
 */
public interface WeatherServiceApi {

    /**
     * Get the current weather conditions for the given zipcode of a US city
     * <p/>
     * This method should query the weather API,
     *
     * @param context
     * @param zipcode of US city
     * @return
     */
    public ApiRequest getCurrentConditions(final Context context, final String zipcode);

    public void cancelCurrentConditionsRequest(Context context, String zipcode);

    public ApiRequest getForecastProjection(final Context context, final String zipcode);

    public void cancel7DayForecastRequest(Context context, String zipcode);


}
