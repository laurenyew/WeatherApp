package laurenyew.weatherapp.network.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import laurenyew.weatherapp.network.listeners.JsonResponseListener;

/**
 * Created by laurenyew on 4/19/16
 * <p/>
 * Helper method, used to simplify the Volley Response listeners into one class.
 */
public class BaseObjectRequest extends JsonObjectRequest {
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
