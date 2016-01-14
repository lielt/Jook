package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.Customer;
import com.backend.entities.Order;
import com.backend.entities.Supplier;
import com.backend.entities.Supplier_Book;
import com.backend.enums.Privilege;
import com.jook.Adapters.MailSend.AsyncSendMail;
import com.jook.DownloadImageFromNetTools.ImageLoader;

public class OrderPage extends AppCompatActivity {

    public static String BOOK_ID = "bid";
    public static String SUP_ID = "sid";
    public static String ORDER_ID = "oid";
    public static String NEW_UPDATE_FLAG = "flag";
    public static String Sender = "sender";


    public ImageLoader imageLoader;

    Book myBook;
    Supplier mySupplier;
    Supplier_Book mySupplierBook;
    Order myOrder;
    Intent myIntent;


    TextView Amount;
    TextView Price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        try {
            myIntent = getIntent();

            String bookID = myIntent.getStringExtra(BOOK_ID);
            String SupplierID = myIntent.getStringExtra(SUP_ID);
            imageLoader = new ImageLoader(this.getApplicationContext());
            if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.OnlyAdmin))
                (findViewById(R.id.deleteSb)).setVisibility(View.VISIBLE);
            myBook = AndroidSuperApp.BL.GetBooksByParameters("id",bookID).get(0);
            mySupplier = AndroidSuperApp.BL.GetSupplierByID(SupplierID);
            mySupplierBook = AndroidSuperApp.BL.GetSupplierBook(bookID, SupplierID);

            Intent mIntent = getIntent();
            String flag = mIntent.getStringExtra(NEW_UPDATE_FLAG);
            if (flag.equals("new"))
                myOrder = new Order(AndroidSuperApp.CurrAppCart.getID(),AndroidSuperApp.CurrAppCart.getID(),SupplierID,bookID,1);
            else
            {
                myOrder = AndroidSuperApp.BL.GetOrderByID(mIntent.getStringExtra(ORDER_ID));
            }



            ///////////////////////////////////////////////////

            TextView SupName = (TextView)findViewById(R.id.SupName);
            TextView SupID = (TextView)findViewById(R.id.SupID);
            TextView SupShip = (TextView)findViewById(R.id.SupShip);
            TextView SupPay = (TextView)findViewById(R.id.SupPay);
            TextView SupAdrr = (TextView)findViewById(R.id.SupAdrr);

            TextView BookTitle = (TextView)findViewById(R.id.BookTitle);
            TextView BookWirter = (TextView)findViewById(R.id.BookWirter);
            TextView BookPublisher = (TextView)findViewById(R.id.BookPublisher);
            ImageView Image = (ImageView) this.findViewById(R.id.Book_image);


            Amount = (TextView)findViewById(R.id.Amount);
            Price = (TextView)findViewById(R.id.Price);

            SupName.setText(mySupplier.getBusinessName());
            SupID.setText(mySupplier.getID());
            SupShip.setText(mySupplier.getShippingMethodAsString());
            if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.Customer))
                SupPay.setText(((Customer)AndroidSuperApp.CurrAppUser).getPayMethodAsString());
            else
                SupPay.setText("זמין רק עבור לקוח");
            SupAdrr.setText(mySupplier.getContactInfo().getAddress().getFullAddressAsString());

            BookTitle.setText(myBook.getBookName());
            BookWirter.setText(myBook.getWriterAsString());
            BookPublisher.setText(myBook.getPublisher());
            imageLoader.DisplayImage(myBook.getURL(), Image);

            Amount.setText(String.valueOf(myOrder.getAmount()));
            Price.setText(String.valueOf(mySupplierBook.getPrice()));

        }
        catch (Exception ex)
        {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public void addAmount(View view)
    {

        if(myOrder.getAmount() < mySupplierBook.getAmount())
        {
            myOrder.setAmount(myOrder.getAmount()+1);
            Amount.setText(String.valueOf(myOrder.getAmount()));
            Price.setText(String.valueOf(myOrder.getAmount() * mySupplierBook.getPrice()));
        }
        else
            Toast.makeText(OrderPage.this, "הגעת לכמות המקסימאלית עבור ספק זה", Toast.LENGTH_SHORT).show();


    }
    public void MinousAmount(View view)
    {
        if (myOrder.getAmount() > 1)
        {
            myOrder.setAmount(myOrder.getAmount()-1);
            Amount.setText(String.valueOf(myOrder.getAmount()));
            Price.setText(String.valueOf(myOrder.getAmount()*mySupplierBook.getPrice()));
        }
        else
        {
            Toast.makeText(OrderPage.this, "לא ניתן להוריד כמות מתחת ל1", Toast.LENGTH_SHORT).show();
        }

    }

    public void MakeOrder(View view)
    {
        try
        {
            Intent mIntent = getIntent();
            String flag = mIntent.getStringExtra(NEW_UPDATE_FLAG);
            if (flag.equals("new"))
                AndroidSuperApp.BL.AddOrder(myOrder);
            else
            {
                AndroidSuperApp.BL.UpdateOrder(myOrder);
            }

            AndroidSuperApp.BL.UpdateCart(AndroidSuperApp.CurrAppCart);

            String flag1="new";
            Intent intent=new Intent(this, CartActivity.class);
            intent.putExtra(CartActivity.KEY_FLAG, flag1);

            Toast.makeText(OrderPage.this, "הזמנה נוספה בהצלחה", Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();

            if (getIntent().getStringExtra(Sender).equals("cart"))
                CartActivity.cartAct.finish();
            else
                ShowBookMain.SBMact.finish();

        }
        catch (Exception ex)
        {
            Toast.makeText(OrderPage.this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    public void Delete(View view) {

        try {
            AndroidSuperApp.BL.removeBookFromSupplier(mySupplierBook);
            Toast.makeText(OrderPage.this, "הספר נמחק מהספק בהצלחה", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new AsyncSendMail().execute("delete",myBook.getBookName(),mySupplier.getContactInfo().getEmail());

    }
}
