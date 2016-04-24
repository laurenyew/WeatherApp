package laurenyew.weatherapp.detail.forecast;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/18/16.
 */
public class ForecastItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView forecastIcon;
    public TextView dayOfWeek;
    public TextView forecastIconSummary;
    public TextView fullForecastSummary;

    public ForecastItemViewHolder(View itemView) {
        super(itemView);
        forecastIcon = (ImageView) itemView.findViewById(R.id.forecast_weather_icon);
        dayOfWeek = (TextView) itemView.findViewById(R.id.day_of_week);
        forecastIconSummary = (TextView) itemView.findViewById(R.id.forecast_icon_info);
        fullForecastSummary = (TextView) itemView.findViewById(R.id.forecast_summary_info);
    }

}
