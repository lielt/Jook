package com.jook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.Supplier;
import com.backend.entities.Supplier_Book;
import com.jook.Adapters.SupDataAdapter;
import com.jook.DownloadImageFromNetTools.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowBookManager extends AppCompatActivity {

    public static final String KEY_BOOK_ID = "bookID";

    public ImageLoader imageLoader;
    Book CurrBook;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_manager);
        ctx = this;



        try {
            Intent intent = getIntent();
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


            ////////////////////////////////////////////////////////////////

            ArrayList<HashMap<String, String>> shopLlist = new ArrayList<HashMap<String, String>>();

            ArrayList<Supplier_Book> spList = AndroidSuperApp.BL.GetSupplierByBook(CurrBook.getID());
            TextView getBookText = (TextView)this.findViewById(R.id.getBookInSupText);


            if (spList.size()>0)
            {
                getBookText.setText("ניתן להשיג את הספר אצל הספקים הבאים");

                for (int i = 0; i < spList.size(); i++)
                {
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    Supplier_Book sb = spList.get(i);
                    Supplier s = AndroidSuperApp.BL.GetSupplierByID(sb.getSupplierID());

                    // adding each child node to HashMap key => value
                    map.put(SupDataAdapter.KEY_SHOP_NAME, s.getBusinessName());
                    map.put(SupDataAdapter.KEY_SHOP_PRICE, String.valueOf(sb.getPrice()));
                    map.put(SupDataAdapter.KEY_SHOP_SHIP, s.getShippingMethodAsString());
                    map.put(SupDataAdapter.KEY_SHOP_STAR, String.valueOf(s.getRate()));


                    // adding HashList to ArrayList
                    shopLlist.add(map);
                }

                ListView listView = (ListView)this.findViewById(R.id.sup_for_book);
                SupDataAdapter adapter = new SupDataAdapter(this,shopLlist);

                listView.setAdapter(adapter);


                listView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent1 = new Intent(ctx,OrderPage.class);


                    }
                });
            }
            else
            {
                getBookText.setText("לא קיימים ספקים שמוכרים ספר זה");
            }


        } catch (Exception ex) {

        }
    }

    public void showRecForBook(View view)
    {
        Intent intent = new Intent(this,Show_Recomends_for_book.class);
        intent.putExtra(Show_Recomends_for_book.KEY_BOOK_ID,CurrBook.getID());
        startActivity(intent);
    }
}
