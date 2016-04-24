package laurenyew.weatherapp.network.responses.models;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Made a simplified more generic DailyForecast (part of ForecastProjection).
 * There were way too many unneeded values in the original response from underground weather.
 * Gson should ignore the unmentioned values when deserializing.
 * <p/>
 */
public class DailyForecast {

    public String iconSummary;
    public String iconUrl;
    public String dayOfWeek;
    public String fullForecastSummary;

    public DailyForecast() {
        iconSummary = null;
        iconUrl = null;
        dayOfWeek = null;
        fullForecastSummary = null;
    }


    @Override
    public String toString() {
        return "DailyForecast [response=" +
                dayOfWeek + ":" + iconSummary + "]";
    }
}
