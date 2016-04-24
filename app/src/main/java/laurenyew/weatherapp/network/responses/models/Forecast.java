package laurenyew.weatherapp.network.responses.models;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Made a simplified more generic Forecast (part of SevenDayForecast).
 * There were way too many unneeded values in the original response from underground weather.
 * Gson should ignore the unmentioned values when deserializing.
 * <p/>
 */
public class Forecast {

    public String iconSummary;
    public String iconUrl;
    public String dayOfWeek;
    public String summaryF;

    public Forecast() {
        iconSummary = null;
        iconUrl = null;
        dayOfWeek = null;
        summaryF = null;
    }


    @Override
    public String toString() {
        return "CurrentWeatherConditions [response=" +
                dayOfWeek + ":" + iconSummary + "]";
    }
}
