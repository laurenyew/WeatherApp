package laurenyew.weatherapp.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherListFragment extends Fragment{
    private RecyclerView mWeatherListRecyclerView = null;
    private ProgressBar mWeatherListLoadingProgressBar = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);

        mWeatherListLoadingProgressBar = (ProgressBar) view.findViewById(R.id.weather_list_load_progress_bar);
        mWeatherListRecyclerView = (RecyclerView) view.findViewById(R.id.weather_recyler_list_view);
        mWeatherListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWeatherListRecyclerView.setAdapter(new WeatherListAdapter());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void showProgressBar()
    {
        //show progress bar, hide recyclerView
        if(mWeatherListLoadingProgressBar != null)
        {
            mWeatherListLoadingProgressBar.setVisibility(View.VISIBLE);
        }
        if(mWeatherListRecyclerView != null)
        {
            mWeatherListRecyclerView.setVisibility(View.GONE);
        }
    }

    private void hideProgressBar()
    {

        if(mWeatherListLoadingProgressBar != null)
        {
            mWeatherListLoadingProgressBar.setVisibility(View.GONE);
        }
        if(mWeatherListRecyclerView != null)
        {
            mWeatherListRecyclerView.setVisibility(View.VISIBLE);
        }
    }

}
