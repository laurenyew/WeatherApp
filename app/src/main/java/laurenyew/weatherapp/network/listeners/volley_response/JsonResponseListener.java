package laurenyew.weatherapp.network.listeners.volley_response;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import laurenyew.weatherapp.network.WeatherUndergroundApiContract;
import laurenyew.weatherapp.network.listeners.ui_update.RequestErrorListener;
import laurenyew.weatherapp.network.responses.status.ErrorResponse;

/**
 * Created by laurenyew on 4/19/16.
 * <p>
 * JsonResponseListener<T>
 * where T is a passed object from the subclass that the JSONObject will be parsed into
 * <p>
 * This Response listener also handles Volley errors
 * <p>
 * Uses the Observer pattern to notify subscribed listeners on request error
 */
public abstract class JsonResponseListener<T> implements Response.Listener<JSONObject>, Response.ErrorListener {

    private List<RequestErrorListener> listeners = new ArrayList<RequestErrorListener>();

    /**
     * Implements Volley's Response.Listener with the JSONObject.
     * This is automatically called by Volley on request completion if there were
     * no Volley errors.
     * <p>
     * deserialize the response into the T model
     * update the cache
     * call on success (subclasses will implement this method)
     *
     * @param response
     */
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

        }
        //If have an error parsing the response, check for a weather
        //api response
        //This catches JSON error responses from the Weather API
        catch (Exception e) {
            checkForWeatherApiErrorResponse(response);
        }
    }


    /**
     * Helper method: look through the response from the Weather API for clues
     * why it did not return the expected response
     * <p>
     * otherwise, return unknown error.
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

    /**
     * Response.ErrorListener implementation
     * <p>
     * Default error response handler for Volley errors
     *
     * @param error from Volley
     */
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
     * The response must be de-serialized into the appropriate object
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
     * <p>
     * This method should be used to update the UI
     *
     * @param data
     */
    public abstract void onSuccess(T data);

    /**
     * Optional override: Developers can choose to go with the default error response
     * or decide to create their own error response
     * <p>
     * This method should be used to update the UI
     *
     * @param error
     */
    public void onError(ErrorResponse error) {
        System.out.println("onError: " + error);
        notifyListenersOfError(error);
    }

    /**
     * Listeners can subscribe to this JsonResponseListener for updates
     *
     * @param listener
     */
    public void addErrorListener(RequestErrorListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribe listener from this class's updates
     *
     * @param listUpdateListener
     */
    public void removeErrorListener(RequestErrorListener listUpdateListener) {
        listeners.remove(listUpdateListener);
    }

    /**
     * Notify listeners of error result
     *
     * @param result
     */
    private void notifyListenersOfError(ErrorResponse result) {
        for (RequestErrorListener listener : listeners) {
            listener.onError(result);
        }
    }


}
