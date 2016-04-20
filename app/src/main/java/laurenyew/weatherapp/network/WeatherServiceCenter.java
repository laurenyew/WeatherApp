package laurenyew.weatherapp.network;

import android.content.Context;

import com.android.volley.Request;

import laurenyew.weatherapp.network.listeners.JsonResponseListener;
import laurenyew.weatherapp.network.requests.BaseObjectRequest;

/**
 * Created by laurenyew on 4/19/16.
 */
public class WeatherServiceCenter implements WeatherServiceApi {

    /**
     * Helper method so we don't have to keep calling getRequestQueue
     *
     * @param context
     * @return
     */
    private WeatherServiceRequestQueue getRequestQueue(Context context) {
        return WeatherServiceRequestQueue.getInstance(context);
    }


    @Override
    public ApiRequest getCurrentConditions(final Context context, String zipcode) {
        return new ApiRequest() {
            @Override
            public void execute(JsonResponseListener listener) {
                BaseObjectRequest request = new BaseObjectRequest(Request.Method.GET, "", null,
                        listener);
                getRequestQueue(context).addToRequestQueue(request);
            }
        };
    }

}
