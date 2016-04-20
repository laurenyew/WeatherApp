package laurenyew.weatherapp.network;

import android.content.Context;

import com.android.volley.Request;

import laurenyew.weatherapp.network.listeners.JsonResponseListener;
import laurenyew.weatherapp.network.requests.BaseObjectRequest;

/**
 * Created by laurenyew on 4/19/16.
 */
public class WeatherServiceCenter implements WeatherServiceApi {


    private String getWeatherServiceBaseUri() {
        return "http://api.wunderground.com/api/";
    }

    private String getWeatherServiceApiKey() {
        return "731efe0d70901aaf";
    }

    private String getUri(String feature, String queryParams) {
        return getWeatherServiceBaseUri() + getWeatherServiceApiKey() + "/" + feature + "/q/" + queryParams + ".json";
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
                getRequestQueue(context).addToRequestQueue(request);
            }
        };
    }

}
