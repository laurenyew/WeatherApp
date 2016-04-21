package laurenyew.weatherapp.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public TextView detail_info;

    //Values
    String detailZipcode = null;

    //Listeners
    WeakReference<CurrentWeatherConditionsResponseListener> mFetchWeatherInfoListenerRef = null;

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
        detail_info = ((TextView) view.findViewById(R.id.detail_info));

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


    @Override
    public void onFetchComplete(Result result) {
        if (result == Result.SUCCESS) {
            CurrentWeatherConditions weather = CurrentWeatherConditionsCache.getInstance().getCurrentWeatherCondition(detailZipcode);
            updateDetailInfoView(weather);
        }
    }

    /**
     * Update the detail info UI with the given weather info
     *
     * @param weather
     */
    private void updateDetailInfoView(CurrentWeatherConditions weather) {
        System.out.println("Update Detail Info View: " + weather);
        if (weather == null) {
            //TODO: Show progress bar for whole page
        }
        //Update the UI if the view is still available (weak reference should be null otherwise
        else {
            detail_info.setText(weather.weather);
        }
    }
}
