package laurenyew.weatherapp.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/18/16.
 */
public class ZipcodeHeaderViewHolder extends RecyclerView.ViewHolder {

    public CardView mCard;
    public TextView mTitle;

    public ZipcodeHeaderViewHolder(View itemView) {
        super(itemView);
        mCard = (CardView) itemView.findViewById(R.id.zipcode_preview_header_card_view);
        mTitle = (TextView) itemView.findViewById(R.id.zipcode_preview_header_text);
    }


}
