package laurenyew.weatherapp.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by laurenyew on 4/19/16.
 * <p>
 * Singleton Volley Request Queue (Suggested best practice by Volley)
 */
public class WeatherServiceRequestQueue {
    private static WeatherServiceRequestQueue mInstance;
    private RequestQueue mRequestQueue;

    private static Context mContext;

    private WeatherServiceRequestQueue(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized WeatherServiceRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WeatherServiceRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelRequestsWithTag(Object tag) {
        getRequestQueue().cancelAll(tag);
    }
}
