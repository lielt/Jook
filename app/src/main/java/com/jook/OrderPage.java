package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.Order;
import com.backend.entities.Supplier;
import com.backend.entities.Supplier_Book;

public class OrderPage extends AppCompatActivity {

    public static String BOOK_ID = "bid";
    public static String SUP_ID = "sid";

    Book myBook;
    Supplier mySupplier;
    Supplier_Book mySupplierBook;
    Order myOrder;
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        try {
            myIntent = getIntent();

            String bookID = myIntent.getStringExtra(BOOK_ID);
            String SupplierID = myIntent.getStringExtra(SUP_ID);

            myBook = AndroidSuperApp.BL.GetBooksByParameters("id",bookID).get(0);
            mySupplier = AndroidSuperApp.BL.GetSupplierByID(SupplierID);
            myOrder = new Order();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}
