package laurenyew.weatherapp.network.listeners.ui_update;

import laurenyew.weatherapp.network.responses.status.ErrorResponse;

/**
 * Created by laurenyew on 4/20/16.
 * <p>
 * Observer pattern listener interface for request results with errors
 */
public interface RequestErrorListener {
    void onError(ErrorResponse error);
}
