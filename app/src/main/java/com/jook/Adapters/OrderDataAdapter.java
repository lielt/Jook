package com.jook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import static com.backend.entities.SystemFunc.*;
import com.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by יוסי on 27/12/2015.
 */
public class OrderDataAdapter extends BaseAdapter {
    public static final String KEY_BOOK_NAME="bookName";
    public static final String KEY_SUPPLIER_NAME="supplierName";
    public static final String KEY_AMOUNT="amount";
    public static final String KEY_PRICE="price";

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public OrderDataAdapter(Activity activity, ArrayList<HashMap<String, String>> data){
        this.activity = activity;
        this.data = data;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View vi= convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.order_for_cart_view_list_row, null);

        TextView bName=(TextView)vi.findViewById(R.id.book_name);
        TextView sName=(TextView)vi.findViewById(R.id.supplier_name);
        TextView bAmount=(TextView)vi.findViewById(R.id.book_amount);
        TextView bPrice=(TextView)vi.findViewById(R.id.book_price);
        TextView tPrice=(TextView)vi.findViewById(R.id.total_price);

        HashMap<String, String> order = new HashMap<String, String>();
        order=data.get(position);

        bName.setText(order.get(KEY_BOOK_NAME));
        sName.setText(order.get(KEY_SUPPLIER_NAME));
        bAmount.setText(order.get(KEY_AMOUNT));
        bPrice.setText(order.get(KEY_PRICE));
        String tempP=order.get(KEY_PRICE);
        String tempA=order.get(KEY_AMOUNT);
        if (tryParseInt(tempA)&&tryParseFloat(tempP)) {
            Float total = (Integer.parseInt(tempA)) * (Float.parseFloat(tempP));
            tPrice.setText(total.toString());
        }




        return vi;

    }
}
