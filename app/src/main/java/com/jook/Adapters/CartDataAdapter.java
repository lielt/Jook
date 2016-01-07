package com.jook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by יוסי on 06/01/2016.
 */
public class CartDataAdapter extends BaseAdapter {


    public static final String KEY_AMOUNT="amount";
    public static final String KEY_PRICE="price";
    public static final String KEY_CART_ID="cid";



    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public CartDataAdapter(Activity activity, ArrayList<HashMap<String, String>> data){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi= convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.cart_of_customer_list_row, null);

        TextView cID=(TextView)vi.findViewById(R.id.cartNumber);
        TextView cAmount=(TextView)vi.findViewById(R.id.invitesNumber);

        TextView cPrice=(TextView)vi.findViewById(R.id.total_price);

        HashMap<String, String> cart = new HashMap<String, String>();
        cart=data.get(position);

        cID.setText(cart.get(KEY_CART_ID));
        cAmount.setText(cart.get(KEY_AMOUNT));
        cPrice.setText(cart.get(KEY_PRICE));






        return vi;
    }
}
