package laurenyew.weatherapp.network.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Made a simplified more generic SevenDayForecast.
 * There were way too many unneeded values in the original response from underground weather.
 * Gson should ignore the unmentioned values when deserializing.
 */
public class SevenDayForecast {

    //Used for eviction strategy (evict if 1 day off original received time)
    public Date evictionDate;

    public String zipcodeKey;

    public List<Forecast> mSevenDayForecast;

    public SevenDayForecast() {
        evictionDate = null;
        zipcodeKey = null;
        mSevenDayForecast = new ArrayList<>(7);
    }

    public SevenDayForecast(String zipcode) {
        super();
        zipcodeKey = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        SevenDayForecast c = (SevenDayForecast) o;
        boolean isEqual = false;
        if (c != null) {
            isEqual = zipcodeKey.equals(c.zipcodeKey) && evictionDate.equals(c.evictionDate);
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return "SevenDayForecast [response=" +
                zipcodeKey + "," + mSevenDayForecast + "]";
    }
}
