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
import laurenyew.weatherapp.network.ApiRequest;
import laurenyew.weatherapp.network.WeatherServiceCenter;
import laurenyew.weatherapp.network.listeners.CurrentWeatherConditionsResponseListener;
import laurenyew.weatherapp.network.responses.CurrentWeatherConditionsResponse;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherDetailFragment extends Fragment {

    WeakReference<TextView> mInfoViewWeakReference = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);

        mInfoViewWeakReference = new WeakReference<TextView>((TextView) view.findViewById(R.id.detail_info));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ApiRequest request = WeatherServiceCenter.getInstance().getCurrentConditions(getActivity(), "75078");
        request.execute(new FetchWeatherDetailResponseListener());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * On Stop cacnel the condition request
     */
    @Override
    public void onStop() {
        super.onStop();
        WeatherServiceCenter.getInstance().cancelCurrentConditionsRequest(getActivity(), "75078");
    }

    /**
     * Inner class b/c we should only be using this onSuccess method for this particular fragment to update UI
     */
    private class FetchWeatherDetailResponseListener extends CurrentWeatherConditionsResponseListener {

        /**
         * OnSuccess, we should update the fragment's view
         *
         * @param data
         */
        @Override
        public void onSuccess(CurrentWeatherConditionsResponse data) {
            if (mInfoViewWeakReference != null) {
                TextView mInfoView = mInfoViewWeakReference.get();
                if (mInfoView != null) {
                    mInfoView.setText(data.weather);
                }
            }
        }
    }


}
