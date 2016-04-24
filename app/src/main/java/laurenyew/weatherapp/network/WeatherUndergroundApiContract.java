package laurenyew.weatherapp.network;

import laurenyew.weatherapp.network.responses.models.WeatherSummaryCodes;

/**
 * Created by laurenyew on 4/22/16.
 * <p/>
 * This is a class to keep track of Weather Api Constantt
 */
public class WeatherUndergroundApiContract {
    //WeatherUnderground values
    public static final String API_APP_KEY = "731efe0d70901aaf";
    public static final String API_URL = "http://api.wunderground.com/api/";

    //WeatherUnderground feature uris
    public static final String CURRENT_CONDITION_FEATURE = "conditions";
    public static final String FORECAST_FEATURE = "forecast";

    //API Response Error values
    public static final String ERROR_KEY = "error";
    public static final String ERROR_TYPE_KEY = "description";
    public static final String ERROR_RESPONSE_INVALID_APP_KEY = "this key does not exist";
    public static final String ERROR_RESPONSE_INVALID_CITY_KEY = "No cities match your search query";

    //Weather Summary Codes
    private static final String NIGHT = "nt_";
    private static final String PARTLY_CLOUDY = "partlycloudy";
    private static final String MOSTLY_CLOUDY = "mostlycloudy";
    private static final String CLEAR = "clear";
    private static final String CHANCE_OF_THUNDER_STORMS = "chancetstorms";
    private static final String THUNDER_STORMS = "tstorms";
    private static final String CHANCE_OF_RAIN = "chancerain";
    private static final String RAIN = "rain ";

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

    /**
     * Weather Underground gives us summary codes for its weather
     * Translate into viewable strings
     *
     * @param wUndergroundWeatherSummaryCode
     * @return
     */
    public static String translateWeatherSummary(String wUndergroundWeatherSummaryCode) {
        StringBuilder builder = new StringBuilder();
        boolean couldNotFindCode = false;

        //Check for codes
        if (wUndergroundWeatherSummaryCode.contains(PARTLY_CLOUDY)) {
            builder.append(WeatherSummaryCodes.PARTLY_CLOUDY);
        } else if (wUndergroundWeatherSummaryCode.contains(MOSTLY_CLOUDY)) {
            builder.append(WeatherSummaryCodes.MOSTLY_CLOUDY);
        } else if (wUndergroundWeatherSummaryCode.contains(CLEAR)) {
            builder.append(WeatherSummaryCodes.CLEAR);
        } else if (wUndergroundWeatherSummaryCode.contains(CHANCE_OF_THUNDER_STORMS)) {
            builder.append(WeatherSummaryCodes.CHANCE_OF_THUNDER_STORMS);
        } else if (wUndergroundWeatherSummaryCode.contains(THUNDER_STORMS)) {
            builder.append(WeatherSummaryCodes.THUNDER_STORMS);
        } else if (wUndergroundWeatherSummaryCode.contains(CHANCE_OF_RAIN)) {
            builder.append(WeatherSummaryCodes.CHANCE_OF_RAIN);
        } else if (wUndergroundWeatherSummaryCode.contains(RAIN)) {
            builder.append(WeatherSummaryCodes.RAIN);
        } else {
            couldNotFindCode = true;
            builder.append(wUndergroundWeatherSummaryCode);
        }
        //Check for at night
        if (!couldNotFindCode && wUndergroundWeatherSummaryCode.contains(NIGHT)) {
            builder.append(WeatherSummaryCodes.NIGHT);
        }

        return builder.toString();
    }
}
