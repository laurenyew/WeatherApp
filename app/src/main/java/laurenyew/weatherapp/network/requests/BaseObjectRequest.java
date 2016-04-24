package laurenyew.weatherapp.network.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import laurenyew.weatherapp.network.listeners.volley_response.JsonResponseListener;

/**
 * Created by laurenyew on 4/19/16
 * <p/>
 * Helper method, used to simplify the Volley Response listeners into one class.
 * NOTE: Usually VOlley requires two different listeners, but for simplicity sake, I combined them into a single listener
 */
public class BaseObjectRequest extends JsonObjectRequest {

    //base volley requirement
    public BaseObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    /**
     * Using this method so that we can use the single JsonResponseListener and override it
     *
     * @param method      (ex: Request.Method.GET)
     * @param url         (url need to hit)
     * @param jsonRequest (parameters included)
     * @param listener    (custom listener)
     */
    public BaseObjectRequest(int method, String url, JSONObject jsonRequest, JsonResponseListener listener) {
        super(method, url, jsonRequest, listener, listener);
    }
}
