package com.jook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.Supplier;
import com.backend.entities.Supplier_Book;
import com.backend.enums.Privilege;
import com.jook.Adapters.SupDataAdapter;
import com.jook.DownloadImageFromNetTools.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowBookMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KEY_BOOK_ID = "bookID";
    public static Activity SBMact;

    public ImageLoader imageLoader;
    Book CurrBook;
    Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SBMact=this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.Supplier))
        {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

        }
        else
        {
            fab.setVisibility(View.INVISIBLE);
            if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.OnlyAdmin)) {
                (findViewById(R.id.deleteB)).setVisibility(View.VISIBLE);
                (findViewById(R.id.rec_for_book_button)).setVisibility(View.GONE);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ctx = this;



        try {
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


            ////////////////////////////////////////////////////////////////

            TextView getBookText = (TextView)this.findViewById(R.id.getBookInSupText);

            if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.Guest))
            {
                getBookText.setText("לא ניתן לרכוש ספר כאורח, לרכישה אנא התחבר...");
            }
            else
            {
                final ArrayList<HashMap<String, String>> shopLlist = new ArrayList<HashMap<String, String>>();

                ArrayList<Supplier_Book> spList = AndroidSuperApp.BL.GetSupplierByBook(CurrBook.getID());


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
                        map.put(SupDataAdapter.KEY_SHOP_ID, s.getID());
                        map.put(SupDataAdapter.KEY_SHOP_NAME, s.getBusinessName());
                        map.put(SupDataAdapter.KEY_SHOP_PRICE, String.valueOf(sb.getPrice()));
                        map.put(SupDataAdapter.KEY_SHOP_SHIP, s.getShippingMethodAsString());
                        map.put(SupDataAdapter.KEY_SHOP_STAR, String.valueOf(s.getRate()));


                        // adding HashList to ArrayList
                        shopLlist.add(map);
                    }

                    final ListView listView = (ListView)this.findViewById(R.id.sup_for_book);
                    SupDataAdapter adapter = new SupDataAdapter(this,shopLlist);

                    listView.setAdapter(adapter);


                    listView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick (AdapterView <?> parent, View view,int position,long id)
                                {
                                    if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.Guest))
                                        Toast.makeText(ShowBookMain.this,getString(R.string.guestError), Toast.LENGTH_SHORT).show();
                                    else
                                    {
                                        Intent intent = new Intent(getBaseContext(), OrderPage.class);
                                        intent.putExtra(OrderPage.SUP_ID, shopLlist.get(position).get(SupDataAdapter.KEY_SHOP_ID));
                                        intent.putExtra(OrderPage.BOOK_ID, CurrBook.getID());
                                        intent.putExtra(OrderPage.NEW_UPDATE_FLAG, "new");
                                        startActivity(intent);
                                    }
                                }

                            });
                }
                else
                {
                    getBookText.setText("לא קיימים ספקים שמוכרים ספר זה");
                }
            }

        } catch (Exception ex) {

        }
    }

    public void showRecForBook(View view)
    {
        Intent intent = new Intent(this,Show_Recommends_For_Book.class);
        intent.putExtra(Show_Recommends_For_Book.KEY_BOOK_ID,CurrBook.getID());
        startActivity(intent);
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
        getMenuInflater().inflate(R.menu.show_book_main, menu);
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

    public void DeleteBook(View view) {
        try {
            AndroidSuperApp.BL.DeleteBook(CurrBook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(ShowBookMain.this,"הספר נמחק בהצלחה", Toast.LENGTH_SHORT).show();

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
