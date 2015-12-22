package com.jook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.AndroidSuperApp;
import com.R;
import com.backend.enums.Privilege;

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
        String Writer= (((EditText)findViewById(R.id.get_writer)).getText()).toString();
        String Publisher= (((EditText)findViewById(R.id.get_publisher)).getText()).toString();
        String PublishYear= (((EditText)findViewById(R.id.get_publish_year)).getText()).toString();
        String Category= ((Spinner)findViewById(R.id.get_book_category)).getSelectedItem().toString();
        boolean ThickCover=((Switch)findViewById(R.id.get_binding)).isChecked();
        if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.Supplier))
        {
            String Price= (((EditText)findViewById(R.id.get_book_price)).getText()).toString();
            String Amount= (((EditText)findViewById(R.id.get_book_amount)).getText()).toString();
        }


    }
}
