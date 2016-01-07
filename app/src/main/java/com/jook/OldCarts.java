package com.jook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.AndroidSuperApp;
import com.R;
import com.jook.Adapters.CartDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class OldCarts extends AppCompatActivity {

    public static final String KEY_AMOUNT="amount";
    public static final String KEY_PRICE="price";
    public static final String KEY_CART_ID="cid";

    ListView list;
    CartDataAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_carts);

        try{
            final ArrayList<HashMap<String, String>> cartList = new ArrayList<HashMap<String, String>>();

            ArrayList<com.backend.entities.Cart> spList = AndroidSuperApp.BL.GetAllCustomerCarts(AndroidSuperApp.CurrAppUser.getID());

            if (spList.size()>0)
            {

                for (int i = 0; i < spList.size(); i++)
                {
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    com.backend.entities.Cart c = spList.get(i);


                    // adding each child node to HashMap key => value
                    map.put(CartDataAdapter.KEY_AMOUNT, String.valueOf(c.getNumOfOrders()));
                    map.put(CartDataAdapter.KEY_PRICE, String.valueOf(c.getTotalPrice()));
                    map.put(CartDataAdapter.KEY_CART_ID, c.getID());

                    // adding HashList to ArrayList
                    cartList.add(map);
                }

                ListView listView = (ListView)this.findViewById(R.id.list_of_carts);
                CartDataAdapter adapter = new CartDataAdapter(this,cartList);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                         String CartId = cartList.get(position).get(KEY_CART_ID);
                        String flag ="old";

                        Intent intent = new Intent(getBaseContext(), CartActivity.class);
                        intent.putExtra(CartActivity.KEY_CART_ID, CartId);
                        intent.putExtra(CartActivity.KEY_FLAG, flag);
                        startActivity(intent);

                    }
                });
            }

        }catch (Exception ex){}
    }
}
