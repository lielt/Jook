package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.jook.Adapters.BookDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowAllBooksInCategory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KEY_B_ID = "id";
    public static final String KEY_B_TITLE = "title";
    public static final String KEY_B_WRITER = "writer";
    public static final String KEY_B_YEAR = "year";
    public static final String KEY_B_THUMB_URL = "thumb_url";

    ListView list;
    BookDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_books_in_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(),AddBook.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //////////////////////////////////// My Add in To func //////////////////////////////

        try {

            Intent intent = getIntent();
            String filter = intent.getStringExtra(MainActivity.KEY_FILTER);

            ArrayList<Book> db;

            if (filter.equals("all"))
                db = AndroidSuperApp.BL.GetAllBooks();
            else
                db = AndroidSuperApp.BL.GetBooksByParameters("category", filter);

            // Convert Data from BL database to HashMapArrayList
            final ArrayList<HashMap<String, String>> BookList = new ArrayList<HashMap<String, String>>();


            // looping through all song nodes <song>
            for (int i = 0; i < db.size(); i++) {
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                Book b = db.get(i);

                // adding each child node to HashMap key => value
                map.put(KEY_B_ID, b.getID());
                map.put(KEY_B_TITLE, b.getBookName());
                map.put(KEY_B_WRITER, b.getWriterAsString());
                map.put(KEY_B_YEAR, String.valueOf(b.getYear()));
                map.put(KEY_B_THUMB_URL, b.getURL());

                // adding HashList to ArrayList
                BookList.add(map);
            }

            list=(ListView)findViewById(R.id.list_for_book);

            // Getting adapter by passing data ArrayList
            adapter = new BookDataAdapter(this,BookList);
            list.setAdapter(adapter);

            // Click event for single list row
            list.setOnItemClickListener(
                    new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String BookId = BookList.get(position).get(KEY_B_ID);

                            Intent intent = new Intent(getBaseContext(), ShowBookMain.class);
                            intent.putExtra(ShowBookMain.KEY_BOOK_ID, BookId);
                            startActivity(intent);
                        }
                    });

        }
        catch (Exception ex)
        {
            Toast toast = Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_all_books_in_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
