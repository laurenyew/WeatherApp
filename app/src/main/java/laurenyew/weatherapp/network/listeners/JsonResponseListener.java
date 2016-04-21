package laurenyew.weatherapp.network.listeners;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import laurenyew.weatherapp.network.ResponseError;

/**
 * Created by laurenyew on 4/19/16.
 */
public abstract class JsonResponseListener<T> implements Response.Listener<JSONObject>, Response.ErrorListener {

    @Override
    public void onResponse(JSONObject response) {
        try {
            if (response != null) {
                T responseModel = deserialize(response);
                onSuccessUpdateCache(responseModel);
                onSuccess(responseModel);
            } else {
                onError(ResponseError.NULL_RESPONSE);
            }

        } catch (Exception e) {
            onError(ResponseError.COULD_NOT_PARSE_JSON);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Error: " + error);
        onError(ResponseError.UNKNOWN);
    }

    /**
     * The response must be deserialized into the appropriate object
     *
     * @param response
     * @return
     */
    public abstract T deserialize(JSONObject response) throws JSONException;

    /**
     * Optional override. Can use to update the cache
     *
     * @param data
     */
    public void onSuccessUpdateCache(T data) {
    }

    /**
     * Developer using JsonResponseListener children MUST implement this class
     * (Allows developers to specify different responses for the same data depending on the UI
     * situation)
     * <p/>
     * This method should be used to update the UI
     *
     * @param data
     */
    public abstract void onSuccess(T data);

    /**
     * Optional override: Developers can choose to go with the default error response
     * or decide to create their own error response
     * <p/>
     * This method should be used to update the UI
     *
     * @param error
     */
    public void onError(ResponseError error) {
        System.out.println("On Error: " + error);
    }


}
