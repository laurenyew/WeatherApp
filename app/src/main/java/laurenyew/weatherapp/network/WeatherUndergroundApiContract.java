package laurenyew.weatherapp.network;

/**
 * Created by laurenyew on 4/22/16.
 * <p/>
 * This is a class to keep track of Weather Api Constantt
 */
public class WeatherUndergroundApiContract {
    //Service API call values
    public static final String API_APP_KEY = "731efe0d70901aaf";
    public static final String API_URL = "http://api.wunderground.com/api/";

    //API Response Error values
    public static final String ERROR_KEY = "error";
    public static final String ERROR_TYPE_KEY = "description";
    public static final String ERROR_RESPONSE_INVALID_APP_KEY = "this key does not exist";
    public static final String ERROR_RESPONSE_INVALID_CITY_KEY = "No cities match your search query";

    /**
     * Different Weather APIs may have different formats for their requests
     *
     * @param feature
     * @param queryParams
     * @return
     */
    public static String getUriJsonGETRequestFormat(String feature, String queryParams) {
        return "/" + feature + "/q/" + queryParams + ".json";
    }
}
