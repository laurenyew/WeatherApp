package laurenyew.weatherapp.network.responses.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Made a simplified more generic SevenDayForecast.
 * There were way too many unneeded values in the original response from underground weather.
 * Gson should ignore the unmentioned values when deserializing.
 */
public class SevenDayForecast extends BaseKeyEvictionModel {

    private List<Forecast> mSevenDayForecast;

    public SevenDayForecast() {
        mSevenDayForecast = new ArrayList<>(7);
    }

    @Override
    public String toString() {
        return "SevenDayForecast [response=" +
                key + "," + mSevenDayForecast + "]";
    }

    public void set(int index, Forecast forecast) {
        if (index >= 0 && index < mSevenDayForecast.size()) {
            mSevenDayForecast.set(index, forecast);
        }
    }

    public Forecast get(int index) {
        if (index >= 0 && index < mSevenDayForecast.size()) {
            return mSevenDayForecast.get(index);
        } else {
            return null;
        }
    }
}
