package laurenyew.weatherapp.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.cache.ZipcodeCache;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherListFragment extends Fragment implements ZipcodeCache.UpdateListener {
    private RecyclerView mWeatherListRecyclerView = null;
    private TextView mEmptyListTextView = null;
    private WeatherListAdapter mAdapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);

        mEmptyListTextView = (TextView) view.findViewById(R.id.empty_zipcode_list_view);
        mWeatherListRecyclerView = (RecyclerView) view.findViewById(R.id.weather_recyler_list_view);
        mWeatherListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WeatherListAdapter();
        mWeatherListRecyclerView.setAdapter(mAdapter);
        updateZipcodeListViewIfEmpty();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ZipcodeCache.getInstance().addListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ZipcodeCache.getInstance().removeListener(this);
    }

    /**
     * Implementing the ZipcodeCache.UpdateListener interface
     * <p/>
     * notify the adapter if the cache has been updated
     */
    @Override
    public void onCacheUpdate() {
        if (mAdapter != null) {
            updateZipcodeListViewIfEmpty();
            mAdapter.notifyDataSetChanged();
        }
    }

    private void updateZipcodeListViewIfEmpty() {
        if (ZipcodeCache.getInstance().size() == 0) {
            mWeatherListRecyclerView.setVisibility(View.GONE);
            mEmptyListTextView.setVisibility(View.VISIBLE);
        } else {
            mWeatherListRecyclerView.setVisibility(View.VISIBLE);
            mEmptyListTextView.setVisibility(View.GONE);
        }
    }


}
