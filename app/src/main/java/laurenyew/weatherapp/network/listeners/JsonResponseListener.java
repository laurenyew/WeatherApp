package laurenyew.weatherapp.network.listeners;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by laurenyew on 4/19/16.
 */
public class JsonResponseListener implements Response.Listener<JSONObject>, Response.ErrorListener {
    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
