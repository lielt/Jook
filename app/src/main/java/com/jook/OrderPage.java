package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
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
            mySupplierBook = AndroidSuperApp.BL.GetSupplierBook(bookID,SupplierID);
            myOrder = new Order("",AndroidSuperApp.CurrAppCart.getID(),SupplierID,bookID,1);

            ///////////////////////////////////////////////////

            TextView SupName = (TextView)findViewById(R.id.SupName);
            TextView SupID = (TextView)findViewById(R.id.SupID);
            TextView SupShip = (TextView)findViewById(R.id.SupShip);
            TextView SupPay = (TextView)findViewById(R.id.SupPay);
            TextView SupAdrr = (TextView)findViewById(R.id.SupAdrr);

            TextView BookTitle = (TextView)findViewById(R.id.BookTitle);
            TextView BookWirter = (TextView)findViewById(R.id.BookWirter);
            TextView BookPublisher = (TextView)findViewById(R.id.BookPublisher);

            TextView Amount = (TextView)findViewById(R.id.Amount);
            TextView Price = (TextView)findViewById(R.id.Price);

            SupName.setText(mySupplier.getBusinessName());
            SupID.setText(mySupplier.getID());
            SupShip.setText(mySupplier.getShippingMethodAsString());
            SupPay.setText(mySupplier.getPayMethodAsString());
            SupAdrr.setText(mySupplier.getContactInfo().getAddress().getFullAddressAsString());

            BookTitle.setText(myBook.getBookName());
            BookWirter.setText(myBook.getWriterAsString());
            BookPublisher.setText(myBook.getPublisher());

            Amount.setText("1");
            Price.setText(String.valueOf(mySupplierBook.getPrice()));

        }
        catch (Exception ex)
        {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}
