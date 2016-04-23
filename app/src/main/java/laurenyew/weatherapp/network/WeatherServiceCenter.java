package laurenyew.weatherapp.network;

import android.content.Context;

import com.android.volley.Request;

import laurenyew.weatherapp.network.listeners.JsonResponseListener;
import laurenyew.weatherapp.network.requests.BaseObjectRequest;

/**
 * Created by laurenyew on 4/19/16.
 * <p/>
 * Uses Volley's Request Queue to send out network requests as per the WeatherServiceApi
 */
public class WeatherServiceCenter implements WeatherServiceApi {

    private static WeatherServiceApi mInstance;

    private String getWeatherServiceBaseUri() {
        return WeatherUndergroundApiContract.API_URL;
    }

    private String getWeatherServiceApiKey() {
        return WeatherUndergroundApiContract.API_APP_KEY;
    }

    private String getUri(String feature, String queryParams) {
        return getWeatherServiceBaseUri() + getWeatherServiceApiKey()
                + WeatherUndergroundApiContract.getUriJsonGETRequestFormat(feature, queryParams);
    }

    /**
     * We should use the singleton, not this constructor.
     */
    private WeatherServiceCenter() {
    }

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
     * Example query: "http://api.wunderground.com/api/731efe0d70901aaf/conditions/q/75078.json"
     *
     * @param context
     * @param zipcode of US city
     * @return
     */
    @Override
    public ApiRequest getCurrentConditions(final Context context, final String zipcode) {
        return new ApiRequest() {
            @Override
            public void execute(JsonResponseListener listener) {
                BaseObjectRequest request = new BaseObjectRequest(Request.Method.GET, getUri("conditions", zipcode), null,
                        listener);
                request.setTag(WeatherServiceApiContract.ACTION_GET_CURRENT_CONDITIONS + zipcode);
                getRequestQueue(context).addToRequestQueue(request);
            }
        };
    }

    /**
     * Cancel current conditions request
     *
     * @param zipcode
     */
    @Override
    public void cancelCurrentConditionsRequest(Context context, String zipcode) {
        getRequestQueue(context).cancelRequestsWithTag(WeatherServiceApiContract.ACTION_GET_CURRENT_CONDITIONS + zipcode);
    }

}
