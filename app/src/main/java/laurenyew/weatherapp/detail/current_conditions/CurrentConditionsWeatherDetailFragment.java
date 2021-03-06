package laurenyew.weatherapp.detail.current_conditions;

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
import laurenyew.weatherapp.detail.WeatherDetailPagerActivity;
import laurenyew.weatherapp.network.ApiRequest;
import laurenyew.weatherapp.network.WeatherServiceCenter;
import laurenyew.weatherapp.network.listeners.ui_update.FetchRequestStatusUpdateListener;
import laurenyew.weatherapp.network.listeners.ui_update.RequestErrorListener;
import laurenyew.weatherapp.network.listeners.volley_response.CurrentWeatherConditionsResponseListener;
import laurenyew.weatherapp.network.responses.models.CurrentWeatherConditions;
import laurenyew.weatherapp.network.responses.status.ErrorResponse;
import laurenyew.weatherapp.util.AlertDialogUtil;

/**
 * Created by laurenyew on 4/18/16.
 * CurrentConditionsWeatherDetailFragment
 * <p>
 * When being viewed: Fetches Current Weather Conditions for given zipcode and displays.
 * First looks in the cache for the Current Conditions.
 * If cannot find, makes a WeatherServiceApi call to get the information and
 * will be updated (if still subscribed) when the data is populated.
 */
public class CurrentConditionsWeatherDetailFragment extends Fragment implements FetchRequestStatusUpdateListener, RequestErrorListener {
    //Views
    private ImageView weather_icon;
    private TextView weather_info;
    private TextView location;
    private TextView detail_info;
    private Button openGoogleMaps;
    private ImageView reference_logo;

    //Values
    private String detailZipcode = null;
    private CurrentWeatherConditions currentWeather = null;

    //Listeners
    private WeakReference<CurrentWeatherConditionsResponseListener> mCurrentWeatherConditionsResponseListenerRef = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        detailZipcode = getArguments().getString(WeatherDetailPagerActivity.ZIPCODE_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_condition_weather_detail, container, false);

        //populate the views for use later
        weather_icon = (ImageView) view.findViewById(R.id.weather_icon);
        weather_info = (TextView) view.findViewById(R.id.weather_info);
        location = (TextView) view.findViewById(R.id.weather_location);
        detail_info = (TextView) view.findViewById(R.id.detail_info);
        openGoogleMaps = (Button) view.findViewById(R.id.detail_location_open_google_maps);
        reference_logo = (ImageView) view.findViewById(R.id.reference_logo_icon);

        return view;
    }


    /**
     * Only show the fragment and perform a download if it is visible
     */
    @Override
    public void onResume() {
        super.onResume();
        populateWeatherDetailsIfVisible();
    }

    /**
     * Only show the fragment and perform a download if it is visible
     *
     * @param visible
     */
    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        populateWeatherDetailsIfVisible();
    }

    /**
     * Helper method, update the share intent for the activity
     */
    private void updateShareIntent() {
        //Update the activity's share intent
        if (isMenuVisible() && getActivity() instanceof WeatherDetailPagerActivity) {
            String forecastData = (currentWeather != null) ? currentWeather.getCurrentConditionsInSharingFormat() : null;
            ((WeatherDetailPagerActivity) getActivity()).setWeatherDetailShareIntent(forecastData);
        }
    }

    /**
     * Helper method populates the page's view details and updates the share icon
     */
    private void populateWeatherDetailsIfVisible() {
        if (detailZipcode != null && isMenuVisible()) {

            //Start the fetch command onStart (after the view has been add up
            //and on the opposite side of the lifecycle as the listeners that are add up.
            CurrentWeatherConditions weather = fetchCurrentWeatherConditions();
            updateDetailInfoView(weather);

            updateShareIntent();
        }
    }

    /**
     * Helper method: get the current weather conditions for the given zipcode
     * <p>
     * 1) Attempt to get the conditions from the cache.
     * 2) If cache detail not available, start an API call
     * 3) Listen for the result, updating the UI accordingly
     *
     * @return
     */
    @Nullable
    private CurrentWeatherConditions fetchCurrentWeatherConditions() {

        CurrentWeatherConditions weather = currentWeather;

        //we have not yet add up the current weather for this detail
        if (weather == null) {
            //Attempt to get info from the cache
            weather = CurrentWeatherConditionsCache.getInstance().getCurrentWeatherCondition(detailZipcode);

            //Cache does not have the details we need. Start an api call and listen for its result.
            if (weather == null) {
                //Create weak reference to a listener for result of api call
                CurrentWeatherConditionsResponseListener listener = new CurrentWeatherConditionsResponseListener(detailZipcode);
                listener.addListener(this);
                listener.addErrorListener(this);
                mCurrentWeatherConditionsResponseListenerRef = new WeakReference<>(listener);

                //Making the api call with the service center
                ApiRequest request = WeatherServiceCenter.getInstance().getCurrentConditions(getActivity(), detailZipcode);
                request.execute(listener);
            }
        }

        return weather;
    }

    /**
     * On Stop cancel the condition request
     */
    @Override
    public void onPause() {
        super.onPause();

        currentWeather = null;

        WeatherServiceCenter.getInstance().cancelCurrentConditionsRequest(getActivity(), detailZipcode);

        if (mCurrentWeatherConditionsResponseListenerRef != null && mCurrentWeatherConditionsResponseListenerRef.get() != null) {
            CurrentWeatherConditionsResponseListener listener = mCurrentWeatherConditionsResponseListenerRef.get();
            listener.removeListener(this);
            listener.removeErrorListener(this);
        }

    }


    /**
     * FetchRequestStatusUpdateListener implementation
     * <p>
     * Let the UI know that the fetch has been completed. Get the data.
     */
    @Override
    public void onFetchComplete() {
        populateWeatherDetailsIfVisible();
    }

    /**
     * Update the detail info UI with the given weather info
     *
     * @param weather
     */
    private void updateDetailInfoView(final CurrentWeatherConditions weather) {
        if (weather != null && hasWeatherChanged(weather)) {
            weather_info.setText(weather.weather);
            location.setText(weather.displayLocationFull);
            detail_info.setText(weather.observationTime +
                    "\nTemp (F): " + weather.tempF +
                    "\nTemp (C): " + weather.tempC +
                    "\nHumidity: " + weather.humidity +
                    "\nWind: " + weather.windSummary +
                    "\nDailyForecast URL:\n" + weather.openForecastUrl);
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
     * RequestErrorListener implementaiton
     * <p>
     * update the UI that the fetch failed with the error associated
     *
     * @param error
     */
    @Override
    public void onError(ErrorResponse error) {
        if (error != null) {
            updateShareIntent();
            AlertDialogUtil.showErrorAlertDialog(
                    getContext(),
                    getString(R.string.error_title),
                    getErrorMessage(error));
        }
    }

    private String getErrorMessage(ErrorResponse errorResponse) {
        String errorMessage;
        switch (errorResponse) {
            case CONNECTION_NOT_AVAILABLE: {
                errorMessage = getString(R.string.data_connection_error_message);
                break;
            }
            case INVALID_REQUEST: {
                errorMessage = getString(R.string.invalid_request);
                break;
            }
            case INVALID_APP_API_KEY: {
                errorMessage = getString(R.string.invalid_app_api_key);
                break;
            }
            case EMPTY_RESPONSE: {
                errorMessage = getString(R.string.default_response_error);
                break;
            }
            default: {
                errorMessage = getString(R.string.invalid_request);
            }
        }
        return errorMessage;
    }

}
