package com.jook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.R;

public class AddBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        //if user is supplier


    }

    public void CreateBook(View view) {
        String Name=(((EditText)findViewById(R.id.get_book_name)).getText()).toString();
        String Writer= (((EditText)findViewById(R.id.get_writer)).getText()).toString();
        String Publisher= (((EditText)findViewById(R.id.get_publisher)).getText()).toString();
        String PublishYear= (((EditText)findViewById(R.id.get_publish_year)).getText()).toString();
        String Category= ((Spinner)findViewById(R.id.get_book_category)).getSelectedItem().toString();


    }
}
