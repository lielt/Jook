package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.Cart;
import com.backend.entities.Order;
import com.backend.entities.Supplier;
import com.jook.Adapters.OrderDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> orderList;
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
            orderList = new ArrayList<HashMap<String, String>>();

            if (spList.size()>0)
            {

                for (int i = 0; i < spList.size(); i++)
                {
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    com.backend.entities.Order r = spList.get(i);

                    //User u = AndroidSuperApp.BL.GetUserByID(r.getId());
                    // adding each child node to HashMap key => value

                    Book b = AndroidSuperApp.BL.GetBooksByParameters("id",r.getBookId()).get(0);
                    map.put(OrderDataAdapter.KEY_BOOK_NAME,b.getBookName());
                    Supplier s = AndroidSuperApp.BL.GetSupplierByID(r.getSupplierId());
                    map.put(OrderDataAdapter.KEY_SUPPLIER_NAME, s.getBusinessName());
                    map.put(OrderDataAdapter.KEY_AMOUNT, String.valueOf(r.getAmount()));
                    map.put(OrderDataAdapter.KEY_PRICE, String.valueOf(AndroidSuperApp.BL.GetBookPrice(r.getSupplierId(), r.getBookId())));
                    map.put(OrderDataAdapter.KEY_BOOK_ID, String.valueOf(r.getBookId()));
                    map.put(OrderDataAdapter.KEY_SUP_ID, String.valueOf(r.getSupplierId()));
                    map.put(OrderDataAdapter.KEY_ORDER_ID, String.valueOf(r.getId()));


                    // adding HashList to ArrayList
                    orderList.add(map);
                }

                ListView listView = (ListView)this.findViewById(R.id.list_of_orders);
                OrderDataAdapter adapter = new OrderDataAdapter(this,orderList);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         Intent intent = new Intent(getBaseContext(), OrderPage.class);
                        intent.putExtra(OrderPage.SUP_ID, orderList.get(position).get(OrderDataAdapter.KEY_SUP_ID));
                        intent.putExtra(OrderPage.BOOK_ID, orderList.get(position).get(OrderDataAdapter.KEY_BOOK_ID));
                        intent.putExtra(OrderPage.NEW_UPDATE_FLAG,"update");
                        intent.putExtra(OrderPage.ORDER_ID,orderList.get(position).get(OrderDataAdapter.KEY_ORDER_ID));
                        startActivity(intent);
                    }
                });
            }


        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
