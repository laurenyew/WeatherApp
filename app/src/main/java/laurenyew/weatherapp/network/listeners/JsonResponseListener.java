package laurenyew.weatherapp.network.listeners;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import laurenyew.weatherapp.network.WeatherUndergroundApiContract;
import laurenyew.weatherapp.network.responses.ErrorResponse;

/**
 * Created by laurenyew on 4/19/16.
 */
public abstract class JsonResponseListener<T> implements Response.Listener<JSONObject>, Response.ErrorListener {

    private List<RequestErrorListener> listeners = new ArrayList<RequestErrorListener>();

    @Override
    public void onResponse(JSONObject response) {
        System.out.println("onResponse: " + response);
        try {
            if (response != null) {
                //No error response
                T responseModel = deserialize(response);
                onSuccessUpdateCache(responseModel);
                onSuccess(responseModel);
            } else {
                onError(ErrorResponse.EMPTY_RESPONSE);
            }

        } catch (Exception e) {

            checkForWeatherApiErrorResponse(response);


        }
    }


    /**
     * Helper method: look through the response from the Weather API for clues
     * why it did not return the expected response
     *
     * @param fullResponse
     */
    protected void checkForWeatherApiErrorResponse(JSONObject fullResponse) {
        try {
            JSONObject response = fullResponse.getJSONObject("response");
            JSONObject errorResponse = response.getJSONObject(WeatherUndergroundApiContract.ERROR_KEY);
            String errorType = errorResponse.getString(WeatherUndergroundApiContract.ERROR_TYPE_KEY);

            if (errorType.equals(WeatherUndergroundApiContract.ERROR_RESPONSE_INVALID_CITY_KEY)) {
                onError(ErrorResponse.INVALID_REQUEST);
            } else if (errorType.equals(WeatherUndergroundApiContract.ERROR_RESPONSE_INVALID_APP_KEY)) {
                onError(ErrorResponse.INVALID_APP_API_KEY);
            } else {
                onError(ErrorResponse.UNKNOWN);
            }

        } catch (JSONException e) {
            onError(ErrorResponse.UNKNOWN);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ErrorResponse errorResponse = ErrorResponse.UNKNOWN;
        if (error != null) {
            if (error instanceof com.android.volley.TimeoutError) {
                errorResponse = ErrorResponse.CONNECTION_NOT_AVAILABLE;
            } else {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse == null) {
                    errorResponse = ErrorResponse.CONNECTION_NOT_AVAILABLE;
                } else {
                    switch (networkResponse.statusCode) {
                        case 401:
                        case 404:
                        case 503: {
                            errorResponse = ErrorResponse.CONNECTION_NOT_AVAILABLE;
                            break;
                        }
                    }
                }
            }

        }

        onError(errorResponse);

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
        System.out.println("onError: " + error);
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
