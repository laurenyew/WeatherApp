package laurenyew.weatherapp.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.detail.WeatherDetailActivity;

/**
 * Created by laurenyew on 4/18/16.
 */
public class ZipcodeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public String zipCode;
    public CardView mCard;
    public TextView mZipcode;

    public ZipcodeItemViewHolder(View itemView) {
        super(itemView);
        mCard = (CardView) itemView.findViewById(R.id.zipcode_preview_card_view);
        mZipcode = (TextView) itemView.findViewById(R.id.zipcode_preview_text);
        mCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent openZipcodeWeatherDetail = new Intent(context, WeatherDetailActivity.class);
        Bundle bundle = new Bundle();
        //TODO: Get zipcode from cache
        bundle.putString(WeatherDetailActivity.ZIPCODE_KEY, zipCode);
        openZipcodeWeatherDetail.putExtras(bundle);
        context.startActivity(openZipcodeWeatherDetail);
    }
}