package ice_pbru.pramrudeevajasen.ice_database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ICE on 5/12/2016.
 */
public class ProductAdapter extends BaseAdapter {

    private Context context;
    private String[] iconString, titleStrings;

    public ProductAdapter(Context context,
                          String[] iconStrings,
                          String[] titleStrings) {

        this.context = context;
        this.iconString = iconStrings;
        this.titleStrings = titleStrings;
    }

    @Override
    public int getCount() {
        return iconString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.product_listview, viewGroup, false);

        TextView titleTextView = (TextView) view1.findViewById(R.id.textView7);
        titleTextView.setText(titleStrings[i]);

        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imageView);
        Picasso.with(context).load(iconString[i]).resize(100, 140).into(iconImageView);

        return view1;
    }
}

