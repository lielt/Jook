package com.jook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.jook.DownloadImageFromNetTools.ImageLoader;

public class ShowBookForSupplier extends AppCompatActivity {

    public static final String KEY_BOOK_ID = "bookID";

    public ImageLoader imageLoader;
    Book CurrBook;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_for_supplier);

        try
        {
            final Intent intent = getIntent();
            imageLoader = new ImageLoader(this.getApplicationContext());

            String BookId = intent.getStringExtra(KEY_BOOK_ID);
            CurrBook = AndroidSuperApp.BL.GetBooksByParameters("ID", BookId).get(0);

            TextView Title = (TextView) this.findViewById(R.id.BookTitle);
            TextView Writer = (TextView) this.findViewById(R.id.BookWirter);
            TextView Publisher = (TextView) this.findViewById(R.id.BookPublisher);
            ImageView Image = (ImageView) this.findViewById(R.id.Book_image);

            Title.setText(CurrBook.getBookName());
            Writer.setText(CurrBook.getWriterAsString());
            Publisher.setText(CurrBook.getPublisher());
            imageLoader.DisplayImage(CurrBook.getURL(), Image);

            ////////////////////////////////////////////////////////////


        }
        catch (Exception ex)
        {

        }
    }

    public void showRecForBook(View view)
    {
        Intent intent = new Intent(this,Show_Recommends_For_Book.class);
        intent.putExtra(Show_Recommends_For_Book.KEY_BOOK_ID, CurrBook.getID());
        startActivity(intent);
    }
}
