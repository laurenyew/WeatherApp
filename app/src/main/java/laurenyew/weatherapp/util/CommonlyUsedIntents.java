package laurenyew.weatherapp.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import laurenyew.weatherapp.detail.WeatherDetailPagerActivity;

/**
 * Created by laurenyew on 4/21/16.
 * <p/>
 * Helper class with commonly used intents
 */
public class CommonlyUsedIntents {

    /**
     * Since this intent is used in several places (list item click, add detail)
     * pulling this intent out for reuse.
     *
     * @param context
     * @param zipcode
     */
    public static void openWeatherDetailActivity(Context context, String zipcode) {
        Intent openZipcodeWeatherDetail = new Intent(context, WeatherDetailPagerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(WeatherDetailPagerActivity.ZIPCODE_KEY, zipcode);
        openZipcodeWeatherDetail.putExtras(bundle);
        context.startActivity(openZipcodeWeatherDetail);
    }
}
