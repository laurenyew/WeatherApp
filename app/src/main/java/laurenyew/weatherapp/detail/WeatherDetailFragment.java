package laurenyew.weatherapp.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.cache.CurrentWeatherConditionsCache;
import laurenyew.weatherapp.network.ApiRequest;
import laurenyew.weatherapp.network.WeatherServiceCenter;
import laurenyew.weatherapp.network.listeners.CurrentWeatherConditionsResponseListener;
import laurenyew.weatherapp.network.listeners.FetchCurrentWeatherUpdateListener;
import laurenyew.weatherapp.network.listeners.Result;
import laurenyew.weatherapp.network.responses.CurrentWeatherConditions;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherDetailFragment extends Fragment implements FetchCurrentWeatherUpdateListener {
    //Views
    public ImageView weather_icon;
    public TextView weather_info;
    public TextView location;
    public TextView detail_info;
    public Button openGoogleMaps;
    public ImageView reference_logo;

    //Values
    String detailZipcode = null;

    //Listeners
    WeakReference<CurrentWeatherConditionsResponseListener> mFetchWeatherInfoListenerRef = null;

    /**
     * FetchCurrentWeatherUpdateListener implementation
     *
     * @param result
     */
    @Override
    public void onFetchComplete(Result result) {
        if (result == Result.SUCCESS) {
            CurrentWeatherConditions weather = CurrentWeatherConditionsCache.getInstance().getCurrentWeatherCondition(detailZipcode);
            updateDetailInfoView(weather);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        detailZipcode = getArguments().getString(WeatherDetailActivity.ZIPCODE_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);

        //populate the views for use later
        weather_icon = (ImageView) view.findViewById(R.id.weather_icon);
        weather_info = (TextView) view.findViewById(R.id.weather_info);
        location = (TextView) view.findViewById(R.id.weather_location);
        detail_info = (TextView) view.findViewById(R.id.detail_info);
        openGoogleMaps = (Button) view.findViewById(R.id.detail_location_open_google_maps);
        reference_logo = (ImageView) view.findViewById(R.id.reference_logo_icon);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (detailZipcode != null) {
            CurrentWeatherConditions weather = CurrentWeatherConditionsCache.getInstance().getCurrentWeatherCondition(detailZipcode);
            if (weather == null) {
                CurrentWeatherConditionsResponseListener listener = new CurrentWeatherConditionsResponseListener();
                listener.addListener(this);
                mFetchWeatherInfoListenerRef = new WeakReference<CurrentWeatherConditionsResponseListener>(listener);
                ApiRequest request = WeatherServiceCenter.getInstance().getCurrentConditions(getActivity(), detailZipcode);
                request.execute(listener);
            }
            updateDetailInfoView(weather);
        }
    }

    /**
     * On Stop cacnel the condition request
     */
    @Override
    public void onStop() {
        super.onStop();
        WeatherServiceCenter.getInstance().cancelCurrentConditionsRequest(getActivity(), "75078");

        if (mFetchWeatherInfoListenerRef != null && mFetchWeatherInfoListenerRef.get() != null) {
            mFetchWeatherInfoListenerRef.get().removeListener(this);
        }
    }


    /**
     * Update the detail info UI with the given weather info
     *
     * @param weather
     */
    private void updateDetailInfoView(final CurrentWeatherConditions weather) {
        System.out.println("Update Detail Info View: " + weather);
        if (weather == null) {
            //TODO: Show progress bar for whole page
        }
        //Update the UI if the view is still available (weak reference should be null otherwise
        else {
            weather_info.setText(weather.weather);
            location.setText(weather.displayLocationFull);
            detail_info.setText(weather.observationTime +
                    "\nTemp (F): " + weather.tempF +
                    "\nTemp (C): " + weather.tempC +
                    "\nHumidity: " + weather.humidity +
                    "\nWind: " + weather.windSummary +
                    "\nForecast URL:\n" + weather.openForecastUrl);
            //Setup the button to click to google maps
            openGoogleMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String googleMapsUrl = "http://maps.google.com/?q=" + weather.latitude + "," + weather.longitude;
                    Uri uri = Uri.parse(googleMapsUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            //Have Picasso load the images
            Picasso.with(getActivity()).load(weather.iconUrl).into(weather_icon);
            Picasso.with(getActivity()).load(weather.logoImageUrl).into(reference_logo);
        }
    }
}
