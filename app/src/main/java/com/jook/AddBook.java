package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.Name;
import com.backend.entities.Supplier_Book;
import com.backend.enums.*;

import static com.backend.entities.SystemFunc.GetCategory;
import static com.backend.entities.SystemFunc.tryParseFloat;
import static com.backend.entities.SystemFunc.tryParseInt;


public class AddBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.Supplier)) {

            LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.book_amount);
            LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.book_price);
            linearLayout1.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
        }


    }

    public void CreateBook(View view) {
        String Name=(((EditText)findViewById(R.id.get_book_name)).getText()).toString();
        String WriterPname= (((EditText)findViewById(R.id.get_writer_private_name)).getText()).toString();
        String WriterFname= (((EditText)findViewById(R.id.get_writer_family_name)).getText()).toString();
        String Publisher= (((EditText)findViewById(R.id.get_publisher)).getText()).toString();
        String PublishYear= (((EditText)findViewById(R.id.get_publish_year)).getText()).toString();
        int pYear=0;
        if (tryParseInt(PublishYear)) {
            pYear = Integer.parseInt(PublishYear);
        }
        String Category= ((Spinner)findViewById(R.id.get_book_category)).getSelectedItem().toString();
        Category category=GetCategory(Category);
        boolean ThickCover=((Switch)findViewById(R.id.get_binding)).isChecked();
        String Url=(((EditText)findViewById(R.id.get_picture_url)).getText()).toString();
        try {
            Book newbook = new Book("1", Name, new Name(WriterPname, WriterFname),Publisher,ThickCover,pYear,category,Url );
            AndroidSuperApp.BL.AddBook(newbook);
            if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.Supplier)) {
                String Price = (((EditText) findViewById(R.id.get_book_price)).getText()).toString();
                float price=0;
                if (tryParseFloat(Price)) {
                    price = Float.parseFloat(Price);
                }
                String Amount = (((EditText) findViewById(R.id.get_book_amount)).getText()).toString();
                int amount=0;
                if (tryParseInt(Amount)) {
                    amount = Integer.parseInt(Amount);
                }
                Supplier_Book sb=new Supplier_Book(AndroidSuperApp.CurrAppUser.getID(),newbook.getID(),amount,price);
                AndroidSuperApp.BL.addBookToSupplier(sb);

            }

        }catch (Exception e){}

        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
