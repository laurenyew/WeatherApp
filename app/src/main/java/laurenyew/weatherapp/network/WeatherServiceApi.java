package laurenyew.weatherapp.network;

import android.content.Context;

/**
 * Created by laurenyew on 4/19/16.
 * <p>
 * This is the main Weather Services API Interface.
 * <p>
 * Calling its methods will use the WeatherServiceCenter implementation to make
 * asynchronous network calls to the chosen weather service and return the
 * JSON object into the APIRequest's JsonResponseListener to be parsed into a usable object.
 */
public interface WeatherServiceApi {

    /**
     * Create an ApiRequest that when executed will
     * Get the current conditions of a US city by zipcode from the weather service.
     * <p>
     * NOTE: The Volley request is tagged with the request type and the zipcode so it can be
     * cancelled if necessary
     *
     * @param context
     * @param zipcode of US city
     * @return ApiRequest to be executed
     */
    public ApiRequest getCurrentConditions(final Context context, final String zipcode);

    /**
     * Cancel all current conditions requests for this zipcode
     *
     * @param context
     * @param zipcode
     */
    public void cancelCurrentConditionsRequest(Context context, String zipcode);

    /**
     * Create an ApiRequest that when executed will
     * Get the weather forecast for a US city by zipcode from the weather service.
     * <p>
     * NOTE: The Volley request is tagged with the request type and the zipcode so it can be
     * cancelled if necessary
     *
     * @param context
     * @param zipcode of US city
     * @return ApiRequest to be executed
     */
    public ApiRequest getForecastProjection(final Context context, final String zipcode);

    /**
     * Cancel all forecast projection requests for this zipcode
     *
     * @param context
     * @param zipcode
     */
    public void cancelForecastProjectionRequest(Context context, String zipcode);


}
