package laurenyew.weatherapp.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * This adapter holds the fragment information as well as the bundle that needs to be passed down
 * <p/>
 * ViewPager takes care of both tab control and swipe control.
 */
public class WeatherDetailViewPagerAdapter extends FragmentPagerAdapter {
    private final Bundle fragmentBundle;
    private final List<Fragment> mWeatherDetailFragments = new ArrayList<>();
    private final List<String> mWeatherDetailFragmentTitles = new ArrayList<>();

    public WeatherDetailViewPagerAdapter(FragmentManager manager, Bundle args) {
        super(manager);
        fragmentBundle = args;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mWeatherDetailFragments.get(position);
        fragment.setArguments(fragmentBundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mWeatherDetailFragments.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mWeatherDetailFragments.add(fragment);
        mWeatherDetailFragmentTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mWeatherDetailFragmentTitles.get(position);
    }
}
