package laurenyew.weatherapp.network.listeners;

import laurenyew.weatherapp.network.responses.ErrorResponse;

/**
 * Created by laurenyew on 4/20/16.
 */
public interface RequestErrorListener {
    void onError(ErrorResponse error);
}
