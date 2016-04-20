package laurenyew.weatherapp.network.listeners;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by laurenyew on 4/19/16.
 */
public abstract class JsonResponseListener<T> implements Response.Listener<JSONObject>, Response.ErrorListener {

    private T clazz;

    public JsonResponseListener(T clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onResponse(JSONObject response) {
        System.out.println("Response: " + response.toString());
        deserialize(response, clazz.getClass());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Error: " + error);
    }

    /**
     * Helper method, use Gson to serialize the JsonObject's string to
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T deserialize(JSONObject response, Class<T> clazz) {
        System.out.println("deserialize response");
        Gson gson = new Gson();
        return gson.fromJson(response.toString(), clazz);
    }

    /**
     * Developer using JsonResponseListener children MUST implement this class
     * (Allows developers to specify different responses for the same data depending on the UI
     * situation)
     *
     * @param data
     */
    public abstract void onSuccess(T data);

    /**
     * Optional override: Developers can choose to go with the default error response
     * or decide to create their own error response
     *
     * @param error
     */
    public void onError(String error) {

    }


}
