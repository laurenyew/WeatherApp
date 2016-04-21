package laurenyew.weatherapp.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.util.CommonlyUsedIntents;

/**
 * Created by laurenyew on 4/18/16.
 */
public class ZipcodeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public String zipcode;
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
        //open weather detail for this zipcode
        CommonlyUsedIntents.openWeatherDetailActivity(v.getContext(), zipcode);
    }
}
