package com.jook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.R;
import com.AndroidSuperApp;
import com.backend.entities.Cart;
import com.backend.entities.Order;
import com.backend.entities.User;
import com.jook.Adapters.RecDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        try
        {
            Cart cart=AndroidSuperApp.CurrAppCart;

            ArrayList<Order> spList= AndroidSuperApp.BL.GetAllCartOrders(cart.getID());
            ArrayList<HashMap<String, String>> orderLlist = new ArrayList<HashMap<String, String>>();

            if (spList.size()>0)
            {

                for (int i = 0; i < spList.size(); i++)
                {
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    com.backend.entities.Order r = spList.get(i);

                    User u = AndroidSuperApp.BL.GetUserByID(r.getId());
                    // adding each child node to HashMap key => value
                    map.put(RecDataAdapter.KEY_RECOMMENDS, u.getContactName().GetFullName());
                    map.put(RecDataAdapter.KEY_RATE, String.valueOf(r.getRate()));
                    map.put(RecDataAdapter.KEY_CONTENT, r.getContent());

                    // adding HashList to ArrayList
                    recLlist.add(map);
                }

                ListView listView = (ListView)this.findViewById(R.id.list_for_rec);
                RecDataAdapter adapter = new RecDataAdapter(this,recLlist);

                listView.setAdapter(adapter);
            }


        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
