package laurenyew.weatherapp.detail.forecast;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.network.responses.models.DailyForecast;
import laurenyew.weatherapp.network.responses.models.ForecastProjection;

/**
 * Created by laurenyew on 4/18/16.
 */
public class ForecastListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private ForecastProjection forecasts;

    public ForecastListAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setForecasts(ForecastProjection newForecasts) {
        forecasts = newForecasts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.forecast_detail_card, parent, false);
        return new ForecastItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ForecastItemViewHolder) {
            //Get info from the DailyForecast list
            DailyForecast forecast = forecasts.get(position);

            ForecastItemViewHolder itemHolder = (ForecastItemViewHolder) holder;
            itemHolder.dayOfWeek.setText(forecast.dayOfWeek);
            itemHolder.forecastIconSummary.setText(forecast.iconSummary);
            itemHolder.fullForecastSummary.setText(forecast.fullForecastSummary);

            //Have Picasso load the weather image
            ImageView forecastIcon = itemHolder.forecastIcon;
            Picasso.with(forecastIcon.getContext()).load(forecast.iconUrl).into(forecastIcon);
        }

    }

    @Override
    public int getItemCount() {
        return (forecasts != null) ? forecasts.size() : 0;
    }


}
