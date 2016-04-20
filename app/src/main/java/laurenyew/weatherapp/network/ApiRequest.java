package laurenyew.weatherapp.network;

import laurenyew.weatherapp.network.listeners.JsonResponseListener;

/**
 * Created by laurenyew on 4/19/16.
 */
public interface ApiRequest {

    /**
     * Helper method to give back to the caller method to tell when to execute
     * background thread.
     */
    public void execute(JsonResponseListener listener);
}
