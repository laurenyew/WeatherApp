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
public class WeatherListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.zipcode_weather_header_preview_card, parent, false);
            return new ZipcodeHeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.zipcode_weather_preview_card, parent, false);
            return new ZipcodeItemViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ZipcodeItemViewHolder) {
            //Get info from the Zipcode cache
            ZipcodeItemViewHolder itemHolder = (ZipcodeItemViewHolder) holder;
            //ignore header in cache position
            String zipcode = ZipcodeCache.getInstance().getItem(position - 1);
            itemHolder.zipCode = zipcode;
            itemHolder.mZipcode.setText(zipcode);
        }

    }

    @Override
    public int getItemCount() {
        return ZipcodeCache.getInstance().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
