package com.jook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.Cart;
import com.backend.entities.Order;
import com.backend.entities.Supplier;
import com.backend.entities.Supplier_Book;
import com.jook.Adapters.OrderDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    public static Activity cartAct;

    ArrayList<HashMap<String, String>> orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartAct = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent=new Intent(view.getContext(),MainActivity.class);
                startActivity(myIntent);
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
                        intent.putExtra(OrderPage.Sender,"cart");
                        startActivity(intent);
                    }
                });
            }
            TextView pay=(TextView) findViewById(R.id.payment);
            Cart cart2=AndroidSuperApp.BL.DiscountPolicy(cart);
            pay.setText(String.valueOf(cart2.getTotalPrice()));


        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void invite(View view) {
        int j=AndroidSuperApp.CurrAppCart.getNumOfOrders();
        ArrayList<Order> spList= AndroidSuperApp.BL.GetAllCartOrders(AndroidSuperApp.CurrAppCart.getID());
        for (int i=0;i<j;i++)
        {
            Supplier_Book sb=AndroidSuperApp.BL.GetSupplierBook(spList.get(i).getBookId(), spList.get(i).getSupplierId());
            try {
                sb.setAmount(sb.getAmount()-spList.get(i).getAmount());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //send mail
        Toast.makeText(CartActivity.this, "ההזמנה בוצעה בהצלחה", Toast.LENGTH_SHORT).show();

        AndroidSuperApp.CurrAppCart = new Cart();
        try {
            AndroidSuperApp.CurrAppCart.setCustomerID(AndroidSuperApp.CurrAppUser.getID());
            AndroidSuperApp.BL.AddCart(AndroidSuperApp.CurrAppCart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}
