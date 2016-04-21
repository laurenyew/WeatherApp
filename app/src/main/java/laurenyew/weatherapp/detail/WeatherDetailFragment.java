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
import laurenyew.weatherapp.network.listeners.RequestErrorListener;
import laurenyew.weatherapp.network.responses.CurrentWeatherConditions;
import laurenyew.weatherapp.network.responses.ErrorResponse;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherDetailFragment extends Fragment implements FetchCurrentWeatherUpdateListener, RequestErrorListener {
    //Views
    public ImageView weather_icon;
    public TextView weather_info;
    public TextView location;
    public TextView detail_info;
    public Button openGoogleMaps;
    public ImageView reference_logo;

    //Values
    String detailZipcode = null;
    CurrentWeatherConditions currentWeather = null;

    //Listeners
    WeakReference<CurrentWeatherConditionsResponseListener> mFetchWeatherInfoListenerRef = null;
    WeakReference<RequestErrorListener> mRequestErrorListener = null;


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

        populateWeatherDetail();
    }

    /**
     * helper method: populate the weather detail
     */
    private void populateWeatherDetail() {
        if (detailZipcode != null) {
            CurrentWeatherConditions weather = getCurrentWeatherConditions();

            //Only update the weather UI if details have changed
            if (hasWeatherChanged(weather)) {
                updateDetailInfoView(weather);
            }
        }
    }

    /**
     * Helper method: compare new weather vs stored weather
     *
     * @param newWeather
     * @return
     */
    private boolean hasWeatherChanged(CurrentWeatherConditions newWeather) {
        boolean weatherChanged = false;
        if (currentWeather == null) {
            currentWeather = newWeather;
            weatherChanged = true;
        } else {
            weatherChanged = !currentWeather.equals(newWeather);
        }
        return weatherChanged;
    }

    /**
     * Helper method: get the current weather conditions for the given zipcode
     * <p/>
     * 1) Attempt to get the conditions from the cache.
     * 2) If cache detail not available, start an API call
     * 3) Listen for the result, updating the UI accordingly
     *
     * @return
     */
    @Nullable
    private CurrentWeatherConditions getCurrentWeatherConditions() {
        CurrentWeatherConditions weather = CurrentWeatherConditionsCache.getInstance().getCurrentWeatherCondition(detailZipcode);

        //Cache does not have the details we need. Start an api call and listen for its result.
        if (weather == null) {

            //Create weak reference to a listener for result of api call
            CurrentWeatherConditionsResponseListener listener = new CurrentWeatherConditionsResponseListener();
            listener.addListener(this);
            listener.addErrorListener(this);
            mFetchWeatherInfoListenerRef = new WeakReference<>(listener);

            //Making the api call with the service center
            ApiRequest request = WeatherServiceCenter.getInstance().getCurrentConditions(getActivity(), detailZipcode);
            request.execute(listener);
        }
        return weather;
    }

    /**
     * On Stop cancel the condition request
     */
    @Override
    public void onStop() {
        super.onStop();
        WeatherServiceCenter.getInstance().cancelCurrentConditionsRequest(getActivity(), detailZipcode);

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

    /**
     * FetchCurrentWeatherUpdateListener implementation
     * <p/>
     * Let the UI know that the fetch has been completed. Get the data.
     */
    @Override
    public void onFetchComplete() {
        CurrentWeatherConditions weather = CurrentWeatherConditionsCache.getInstance().getCurrentWeatherCondition(detailZipcode);
        updateDetailInfoView(weather);
    }

    /**
     * RequestErrorListener implementaiton
     * <p/>
     * update the UI that the fetch failed with the error associated
     *
     * @param error
     */
    @Override
    public void onError(ErrorResponse error) {

    }


}
