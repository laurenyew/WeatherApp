package laurenyew.weatherapp.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.cache.ZipcodeCache;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<ZipcodeViewHolder> {


    @Override
    public ZipcodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zipcode_weather_preview_card, parent, false);
        return new ZipcodeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ZipcodeViewHolder holder, int position) {

        //Get info from the Zipcode cache
        String zipcode = ZipcodeCache.getInstance().getItem(position);
        holder.zipCode = zipcode;
        holder.mZipcode.setText(zipcode);
    }

    @Override
    public int getItemCount() {
        return ZipcodeCache.getInstance().size();
    }


}
