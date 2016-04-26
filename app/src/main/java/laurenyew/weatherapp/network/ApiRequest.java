package laurenyew.weatherapp.network;

import laurenyew.weatherapp.network.listeners.volley_response.JsonResponseListener;

/**
 * Created by laurenyew on 4/19/16.
 * <p/>
 * This interface was created to allow the developer to have the flexibility to
 * decide when to execute the Volley service request after they set up the request.
 * <p/>
 * The JsonResponseListener subclass passed into the execute method should
 * deserialize the JSONObject returned by Volley into the appropriate object
 * or the JsonResponseListener will deal with the error response and propogate it up.
 */
public interface ApiRequest {

    /**
     * Helper method to give back to the caller method to tell when to execute
     * background thread.
     *
     * @param listener
     */
    public void execute(JsonResponseListener listener);
}
