package laurenyew.weatherapp.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/18/16.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<ZipcodeViewHolder>{


    @Override
    public ZipcodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zipcode_weather_preview_card, parent, false);
        ZipcodeViewHolder vh = new ZipcodeViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ZipcodeViewHolder holder, int position) {

        //TODO: Get info from the Zipcode cache
        holder.mZipcode.setText("75078");
    }

    @Override
    public int getItemCount() {
        //TODO: Get info from Zipcode cache
        return 1;
    }



}
