package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.Recommendation;
import com.backend.entities.User;
import com.jook.Adapters.RecDataAdapter;
import com.jook.DownloadImageFromNetTools.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class Show_Recomends_for_book extends AppCompatActivity {

    public static final String KEY_BOOK_ID = "bookID";
    public ImageLoader imageLoader;
    Book CurrBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__recomends_for_book);

        try
        {
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

            ///////////////////////////////////////////////////////////////


            ArrayList<HashMap<String, String>> recLlist = new ArrayList<HashMap<String, String>>();

            ArrayList<Recommendation> spList = AndroidSuperApp.BL.GetRecommendationByRecommendedID(CurrBook.getID());

            if (spList.size()>0)
            {

                for (int i = 0; i < spList.size(); i++)
                {
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    Recommendation r = spList.get(i);

                    User u = AndroidSuperApp.BL.GetUserByID(r.getIDRecomends());
                    // adding each child node to HashMap key => value
                    map.put(RecDataAdapter.KEY_RECOMMENDS, u.getContactName().GetFullName());
                    map.put(RecDataAdapter.KEY_RATE, String.valueOf(r.getRate()));
                    map.put(RecDataAdapter.KEY_CONTENT, r.getContent());

                    // adding HashList to ArrayList
                    recLlist.add(map);
                }

                ListView listView = (ListView)this.findViewById(R.id.list_for_rec);
                RecDataAdapter adapter = new RecDataAdapter(this,recLlist);

                listView.setAdapter(adapter);
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(),Toast.LENGTH_LONG).show();
        }


    }
}
