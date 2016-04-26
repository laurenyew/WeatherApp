package laurenyew.weatherapp.network;

import android.content.Context;

import com.android.volley.Request;

import laurenyew.weatherapp.network.listeners.volley_response.JsonResponseListener;
import laurenyew.weatherapp.network.requests.BaseObjectRequest;

/**
 * Created by laurenyew on 4/19/16.
 * <p/>
 * WeatherServiceApi implementation
 *
 * @see WeatherServiceApi for more info
 * <p/>
 * Uses Volley's Request Queue to send out network requests as per the WeatherServiceApi
 */
public class WeatherServiceCenter implements WeatherServiceApi {

    private static WeatherServiceApi mInstance;

    /**
     * Get Base Uri for the service we are using
     *
     * @return Base Uri for the weather service
     */
    private String getWeatherServiceBaseUri() {
        return WeatherUndergroundApiContract.API_URL;
    }

    /**
     * Each weather service appears to have an app api key. Return the contract's associated api key
     *
     * @return contract's associated api key
     */
    private String getWeatherServiceApiKey() {
        return WeatherUndergroundApiContract.API_APP_KEY;
    }

    /**
     * Helper method: create uri for a given feature
     *
     * @param feature     -- weather feature (ex: 'forecast', 'conditions')
     * @param queryParams -- parameters to query with (ex: zipcode)
     * @return request uri
     */
    private String getUri(String feature, String queryParams) {
        return getWeatherServiceBaseUri() + getWeatherServiceApiKey()
                + WeatherUndergroundApiContract.getUriJsonGETRequestFormat(feature, queryParams);
    }

    /**
     * We should use the singleton, not this constructor.
     */
    private WeatherServiceCenter() {
    }

    /**
     * Singleton pattern
     *
     * @return
     */
    public static WeatherServiceApi getInstance() {
        if (mInstance == null) {
            mInstance = new WeatherServiceCenter();
        }
        return mInstance;
    }

    /**
     * Helper method so we don't have to keep calling getRequestQueue
     *
     * @param context
     * @return
     */
    private WeatherServiceRequestQueue getRequestQueue(Context context) {
        return WeatherServiceRequestQueue.getInstance(context);
    }


    /**
     * Returns an API Request to get the current conditions for a given zipcode
     * Specifies the JsonResponseListener to send the results (error/json) to.
     * <p/>
     * Example query: "http://api.wunderground.com/api/731efe0d70901aaf/conditions/q/75078.json"
     *
     * @param context
     * @param zipcode of US city
     * @return ApiRequest to execute
     */
    @Override
    public ApiRequest getCurrentConditions(final Context context, final String zipcode) {
        return new ApiRequest() {
            @Override
            public void execute(JsonResponseListener listener) {
                BaseObjectRequest request = new BaseObjectRequest(Request.Method.GET, getUri(WeatherUndergroundApiContract.CURRENT_CONDITION_FEATURE, zipcode), null,
                        listener);
                request.setTag(WeatherServiceApiContract.ACTION_GET_CURRENT_CONDITIONS + zipcode);
                getRequestQueue(context).addToRequestQueue(request);
            }
        };
    }

    /**
     * Cancel current conditions request for given zipcode
     *
     * @param zipcode to cancel current condition request(s) for.
     */
    @Override
    public void cancelCurrentConditionsRequest(Context context, String zipcode) {
        getRequestQueue(context).cancelRequestsWithTag(WeatherServiceApiContract.ACTION_GET_CURRENT_CONDITIONS + zipcode);
    }

    /**
     * Returns an API Request to get the forecast for a given zipcode
     * Specifies the JsonResponseListener to send the results (error/json) to.
     * <p/>
     * Example query: "http://api.wunderground.com/api/731efe0d70901aaf/forecast/q/75078.json"
     *
     * @param context
     * @param zipcode of US city
     * @return ApiRequest to execute
     */
    @Override
    public ApiRequest getForecastProjection(final Context context, final String zipcode) {
        return new ApiRequest() {
            @Override
            public void execute(JsonResponseListener listener) {
                BaseObjectRequest request = new BaseObjectRequest(
                        Request.Method.GET,
                        getUri(WeatherUndergroundApiContract.FORECAST_FEATURE,
                                zipcode),
                        null,
                        listener);
                request.setTag(WeatherServiceApiContract.ACTION_GET_FORECAST + zipcode);
                getRequestQueue(context).addToRequestQueue(request);
            }
        };
    }

    /**
     * Cancel forecast request for given zipcode
     *
     * @param zipcode to cancel current condition request(s) for.
     */
    @Override
    public void cancelForecastProjectionRequest(Context context, String zipcode) {
        getRequestQueue(context).cancelRequestsWithTag(WeatherServiceApiContract.ACTION_GET_FORECAST + zipcode);
    }

}
