package laurenyew.weatherapp.network.responses;

import java.util.Date;

/**
 * Created by laurenyew on 4/19/16.
 * <p/>
 * Made a simplified more generic CurrentWeatherConditions.
 * There were way too many unneeded values in the original response from underground weather.
 * Gson should ignore the unmentioned values when deserializing.
 */
public class CurrentWeatherConditions {

    //Used for eviction strategy (evict if 1 day off original received time)
    public Date evictionDate;

    public String logoImageUrl; //"image.url":"http://icons-ak.wxug.com/graphics/wu2/logo_130x80.png"

    public String displayLocationFull; //display_location.full":"San Francisco, CA"
    public String zipcode;//display_location.zip:"94101"
    public Double latitude;//display_location.latitude:"37.77500916"
    public Double longitude;//display_location.longitude:"-122.41825867"

    public String observationTime;//observation_time:"Last Updated on June 27, 5:27 PM PDT"
    public String weather;//weather:"Partly Cloudy"
    public Integer tempF;//temp_f:66.3
    public Integer tempC;//temp_c:19.1
    public String humidity;//relative_humidity:"65%"
    public String windSummary;//wind_string:"From the NNW at 22.0 MPH Gusting to 28.0 MPH"

    public String weatherIconName;//icon:"partlycloudy"
    public String iconUrl;//icon_url:"http://icons-ak.wxug.com/i/c/k/partlycloudy.gif"
    public String openForecastUrl;//forecast_url:"http://www.wunderground.com/US/CA/San_Francisco.html"

    public CurrentWeatherConditions() {
        logoImageUrl = null;
        displayLocationFull = null;
        zipcode = null;
        latitude = null;
        longitude = null;
        observationTime = null;
        weather = null;
        tempF = null;
        tempC = null;
        humidity = null;
        windSummary = null;
        weatherIconName = null;
        iconUrl = null;
        openForecastUrl = null;
    }

    public CurrentWeatherConditions(String zipcode) {
        super();
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        CurrentWeatherConditions c = (CurrentWeatherConditions) o;
        boolean isEqual = false;
        if (c != null) {
            isEqual = zipcode.equals(c.zipcode) && evictionDate.equals(c.evictionDate);
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return "CurrentWeatherConditions [response=" +
                zipcode + "," + displayLocationFull + "]";
    }
}
