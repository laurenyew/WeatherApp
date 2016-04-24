package laurenyew.weatherapp.detail.forecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.cache.ForecastProjectionCache;
import laurenyew.weatherapp.detail.WeatherDetailPagerActivity;
import laurenyew.weatherapp.network.ApiRequest;
import laurenyew.weatherapp.network.WeatherServiceCenter;
import laurenyew.weatherapp.network.listeners.ui_update.FetchRequestStatusUpdateListener;
import laurenyew.weatherapp.network.listeners.ui_update.RequestErrorListener;
import laurenyew.weatherapp.network.listeners.volley_response.SevenDayForecastResponseListener;
import laurenyew.weatherapp.network.responses.models.ForecastProjection;
import laurenyew.weatherapp.network.responses.status.ErrorResponse;
import laurenyew.weatherapp.util.AlertDialogUtil;

/**
 * Created by laurenyew on 4/18/16.
 */
public class SevenDayForecastWeatherDetailFragment extends Fragment implements FetchRequestStatusUpdateListener, RequestErrorListener {
    //Views
    private RecyclerView mForecastListRecyclerView = null;
    private TextView mEmptyListTextView = null;
    private ForecastListAdapter mAdapter = null;

    //Values
    private String detailZipcode = null;
    private ForecastProjection currentForecast = null;

    //Listeners
    private WeakReference<SevenDayForecastResponseListener> mSevenDayForecastResponseListenerRef = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        detailZipcode = getArguments().getString(WeatherDetailPagerActivity.ZIPCODE_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seven_day_forecast_weather_detail, container, false);

        //populate the views for use later
        mEmptyListTextView = (TextView) view.findViewById(R.id.empty_seven_day_forecast_list_view);
        mForecastListRecyclerView = (RecyclerView) view.findViewById(R.id.seven_day_forecast_recyler_list_view);
        mForecastListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ForecastListAdapter(inflater);
        mForecastListRecyclerView.setAdapter(mAdapter);
        updateForecastListViewIfEmpty(currentForecast);

        return view;
    }


    /**
     * Only show the fragment and perform a download if it is visible
     */
    @Override
    public void onResume() {
        super.onResume();
        populateForecastWeatherDetailsIfVisible();


    }

    /**
     * Only show the fragment and perform a download if it is visible
     *
     * @param visible
     */
    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        populateForecastWeatherDetailsIfVisible();

    }


    /**
     * On Stop cancel the condition request
     */
    @Override
    public void onPause() {
        super.onPause();

        currentForecast = null;

        WeatherServiceCenter.getInstance().cancel7DayForecastRequest(getActivity(), detailZipcode);

        if (mSevenDayForecastResponseListenerRef != null && mSevenDayForecastResponseListenerRef.get() != null) {
            SevenDayForecastResponseListener listener = mSevenDayForecastResponseListenerRef.get();
            listener.removeListener(this);
            listener.removeErrorListener(this);
        }

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
    private ForecastProjection fetchSevenDayForecast() {

        ForecastProjection forecast = currentForecast;

        //we have not yet add up the forecast projection for this detail
        if (forecast == null) {
            //Attempt to get info from the cache
            forecast = ForecastProjectionCache.getInstance().getForecastList(detailZipcode);

            //Cache does not have the details we need. Start an api call and listen for its result.
            if (forecast == null) {
                //Create weak reference to a listener for result of api call
                SevenDayForecastResponseListener listener = new SevenDayForecastResponseListener(detailZipcode);
                listener.addListener(this);
                listener.addErrorListener(this);
                mSevenDayForecastResponseListenerRef = new WeakReference<>(listener);

                //Making the api call with the service center
                ApiRequest request = WeatherServiceCenter.getInstance().get7DayForecast(getActivity(), detailZipcode);
                request.execute(listener);
            }
        }

        return forecast;
    }


    /**
     * FetchRequestStatusUpdateListener implementation
     * <p/>
     * Let the UI know that the fetch has been completed. Get the data.
     */
    @Override
    public void onFetchComplete() {
        //Update the fragment UI
        populateForecastWeatherDetailsIfVisible();
    }

    /**
     * Helper method, update the share intent for the activity
     */
    private void updateShareIntent() {
        //Update the activity's share intent
        if (isMenuVisible() && getActivity() instanceof WeatherDetailPagerActivity) {
            String forecastData = (currentForecast != null) ? currentForecast.getForecastInSharingFormat() : null;
            ((WeatherDetailPagerActivity) getActivity()).setWeatherDetailShareIntent(forecastData);
        }
    }

    private void populateForecastWeatherDetailsIfVisible() {
        if (detailZipcode != null && isMenuVisible()) {
            //Start the fetch command onStart (after the view has been add up
            //and on the opposite side of the lifecycle as the listeners that are add up.
            ForecastProjection sevenDayForecast = fetchSevenDayForecast();
            updateDetailInfoView(sevenDayForecast);

            updateShareIntent();
        }
    }

    /**
     * Update the detail info UI with the given weather info
     *
     * @param forecast
     */
    private void updateDetailInfoView(final ForecastProjection forecast) {

        updateForecastListViewIfEmpty(forecast);
        if (forecast != null && hasForecastChanged(forecast)) {
            mAdapter.setForecasts(forecast);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Show/Hide Empty list view as appropriate
     */
    private void updateForecastListViewIfEmpty(ForecastProjection forecast) {
        if (forecast == null || forecast.size() == 0) {
            mForecastListRecyclerView.setVisibility(View.GONE);
            mEmptyListTextView.setVisibility(View.VISIBLE);
        } else {
            mForecastListRecyclerView.setVisibility(View.VISIBLE);
            mEmptyListTextView.setVisibility(View.GONE);
        }
    }

    /**
     * Helper method: compare new forecast vs stored forecast
     *
     * @param newForecast
     * @return
     */
    private boolean hasForecastChanged(ForecastProjection newForecast) {
        boolean forecastChanged = false;
        if (currentForecast == null) {
            currentForecast = newForecast;
            forecastChanged = true;
        } else {
            forecastChanged = !currentForecast.equals(newForecast);
        }
        return forecastChanged;
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
