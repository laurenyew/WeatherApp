package laurenyew.weatherapp.network.responses.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurenyew on 4/23/16.
 * <p>
 * Made a simplified more generic ForecastProjection model.
 * There were way too many unneeded values in the original response from underground weather.
 * Gson should ignore the unmentioned values when deserializing.
 */
public class ForecastProjection extends BaseKeyEvictionModel {

    private List<DailyForecast> mForecasts;

    public ForecastProjection() {
        mForecasts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ForecastProjection [response=" +
                key + "," + mForecasts + "]";
    }

    /**
     * Puts the forecast in sharing format
     *
     * @return String to be parsed into sharing format
     */
    public String getForecastInSharingFormat() {
        StringBuilder builder = new StringBuilder();
        builder.append("Forecast for " + key + ": \n\n");

        for (DailyForecast dailyForecast : mForecasts) {
            builder.append("\t\t" + dailyForecast.dayOfWeek + " : " + dailyForecast.fullForecastSummary + "\n\n");
        }

        return builder.toString();
    }

    public void add(DailyForecast forecast) {
        mForecasts.add(forecast);
    }

    public DailyForecast get(int index) {
        if (index >= 0 && index < mForecasts.size()) {
            return mForecasts.get(index);
        } else {
            return null;
        }
    }

    public int size() {
        if (mForecasts == null) {
            return 0;
        } else {
            return mForecasts.size();
        }
    }
}
