package laurenyew.weatherapp.network.listeners;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import laurenyew.weatherapp.network.responses.ErrorResponse;

/**
 * Created by laurenyew on 4/19/16.
 */
public abstract class JsonResponseListener<T> implements Response.Listener<JSONObject>, Response.ErrorListener {

    private List<RequestErrorListener> listeners = new ArrayList<RequestErrorListener>();

    @Override
    public void onResponse(JSONObject response) {
        try {
            if (response != null) {
                T responseModel = deserialize(response);
                onSuccessUpdateCache(responseModel);
                onSuccess(responseModel);
            } else {
                onError(ErrorResponse.EMPTY_RESPONSE);
            }

        } catch (Exception e) {
            onError(ErrorResponse.INVALID_REQUEST);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Error: " + error);
        onError(ErrorResponse.UNKNOWN);
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
    public void onError(ErrorResponse error) {
        System.out.println("On Error: " + error);
        notifyListenersOfError(error);
    }

    public void addErrorListener(RequestErrorListener listener) {
        listeners.add(listener);
    }

    public void removeErrorListener(RequestErrorListener listUpdateListener) {
        listeners.remove(listUpdateListener);
    }

    private void notifyListenersOfError(ErrorResponse result) {
        for (RequestErrorListener listener : listeners) {
            listener.onError(result);
        }
    }


}
