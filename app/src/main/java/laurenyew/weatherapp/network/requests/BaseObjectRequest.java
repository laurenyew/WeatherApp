package laurenyew.weatherapp.network.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import laurenyew.weatherapp.network.listeners.JsonResponseListener;

/**
 * Created by laurenyew on 4/19/16.
 */
public class BaseObjectRequest extends JsonObjectRequest {
    public BaseObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public BaseObjectRequest(int method, String url, JSONObject jsonRequest, JsonResponseListener listener) {
        super(method, url, jsonRequest, listener, listener);
    }
}
