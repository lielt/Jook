package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.User;
import com.backend.enums.Privilege;
import com.jook.Adapters.RecDataAdapter;
import com.jook.DownloadImageFromNetTools.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class Show_Recommends_For_Book extends AppCompatActivity {

    public static final String KEY_BOOK_ID = "bookID";
    public ImageLoader imageLoader;
    Book CurrBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__recommends__for__book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), RecommendationActivity.class);
                String BookID=CurrBook.getID();
                myIntent.putExtra(KEY_BOOK_ID, BookID);
                startActivity(myIntent);
            }
        });

        if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.Guest))
            fab.setVisibility(View.INVISIBLE);
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

            ArrayList<com.backend.entities.Recommendation> spList = AndroidSuperApp.BL.GetRecommendationByRecommendedID(CurrBook.getID());

            if (spList.size()>0)
            {

                for (int i = 0; i < spList.size(); i++)
                {
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    com.backend.entities.Recommendation r = spList.get(i);

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
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
}


