package laurenyew.weatherapp.network.responses.status;

/**
 * Created by laurenyew on 4/20/16.
 * <p>
 * Enum for translating error responses on making Weather Service Api calls.
 * Error types include:
 * - connection / network errors
 * - errors parsing json
 * - errors from the weather api
 * - etc.
 */
public enum ErrorResponse {
    UNKNOWN, CONNECTION_NOT_AVAILABLE, INVALID_REQUEST, EMPTY_RESPONSE, INVALID_APP_API_KEY
}

