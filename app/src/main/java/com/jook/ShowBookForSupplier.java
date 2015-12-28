package com.jook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.Supplier;
import com.backend.entities.Supplier_Book;
import com.jook.DownloadImageFromNetTools.ImageLoader;

public class ShowBookForSupplier extends AppCompatActivity {

    public static final String KEY_BOOK_ID = "bookID";

    public ImageLoader imageLoader;
    Book CurrBook;
    Supplier CurrSup;
    Supplier_Book mSP;
    Context ctx;
    EditText mAmount;
    EditText mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_for_supplier);

        ctx = this;

        try
        {
            final Intent intent = getIntent();
            imageLoader = new ImageLoader(this.getApplicationContext());

            String BookId = intent.getStringExtra(KEY_BOOK_ID);
            CurrBook = AndroidSuperApp.BL.GetBooksByParameters("ID", BookId).get(0);
            CurrSup = (Supplier)AndroidSuperApp.CurrAppUser;

            TextView Title = (TextView) this.findViewById(R.id.BookTitle);
            TextView Writer = (TextView) this.findViewById(R.id.BookWirter);
            TextView Publisher = (TextView) this.findViewById(R.id.BookPublisher);
            ImageView Image = (ImageView) this.findViewById(R.id.Book_image);

            Title.setText(CurrBook.getBookName());
            Writer.setText(CurrBook.getWriterAsString());
            Publisher.setText(CurrBook.getPublisher());
            imageLoader.DisplayImage(CurrBook.getURL(), Image);

            ////////////////////////////////////////////////////////////

            mAmount = (EditText)findViewById(R.id.get_book_amount);
            mPrice = (EditText)findViewById(R.id.get_book_price);

            mSP = AndroidSuperApp.BL.GetSupplierBook(CurrBook.getID(),CurrSup.getID());

            mAmount.setText(String.valueOf(mSP.getAmount()));
            mPrice.setText(String.valueOf(mSP.getPrice()));


        }
        catch (Exception ex)
        {
            Toast.makeText(ShowBookForSupplier.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Stock.class));
    }

    public void showRecForBook(View view)
    {
        Intent intent = new Intent(this,Show_Recommends_For_Book.class);
        intent.putExtra(Show_Recommends_For_Book.KEY_BOOK_ID, CurrBook.getID());
        startActivity(intent);
    }

    public void updateBookForSupplier(View view)
    {
        try
        {
            AndroidSuperApp.BL.updateBookOnSupplier(new Supplier_Book(CurrSup.getID(),CurrBook.getID(),Integer.parseInt( mAmount.getText().toString() ),Float.parseFloat(mPrice.getText().toString())));
            Toast.makeText(ShowBookForSupplier.this,"העדכון בוצע בהצלחה", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, Stock.class));


        }
        catch (Exception ex)
        {
            Toast.makeText(ShowBookForSupplier.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteBookForSupplier(View view)
    {
        try
        {
            AndroidSuperApp.BL.removeBookFromSupplier(new Supplier_Book(CurrSup.getID(),CurrBook.getID()));
            finish();
            startActivity(new Intent(this,Stock.class));
        }
        catch (Exception ex)
        {
            Toast.makeText(ShowBookForSupplier.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
