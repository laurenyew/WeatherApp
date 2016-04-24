package laurenyew.weatherapp.network.responses;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Made a simplified more generic Forecast (part of SevenDayForecast).
 * There were way too many unneeded values in the original response from underground weather.
 * Gson should ignore the unmentioned values when deserializing.
 * <p/>
 */
public class Forecast {

    String iconSummary;
    String iconUrl;
    String dayOfWeek;
    String summaryF;
    Integer tempF;

    public Forecast() {
        iconSummary = null;
        iconUrl = null;
        dayOfWeek = null;
        summaryF = null;
        tempF = null;
    }


    @Override
    public String toString() {
        return "CurrentWeatherConditions [response=" +
                dayOfWeek + ":" + iconSummary + "," + tempF + "]";
    }
}
