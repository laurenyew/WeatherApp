package laurenyew.weatherapp.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherDetailFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);

        TextView mInfoView = (TextView) view.findViewById(R.id.detail_info);
        mInfoView.setText("HELLO");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
