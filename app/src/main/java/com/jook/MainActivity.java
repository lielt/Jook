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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.Supplier_Book;
import com.backend.enums.Category;
import com.backend.enums.Privilege;
import com.jook.DownloadImageFromNetTools.ImageLoaderForBorrunView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KEY_FILTER = "BookToShowFilter";
    ArrayList<Integer> ImageButtonIndex = new ArrayList<Integer>();
    ArrayList<String> BookIDIndex = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddBook.class);
                startActivity(intent);
            }
        });

        ////////////////////////////////////////////////////////////////


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //////////////////////////////////////////////////////////////////////////////

        try {

            View headerLayout = navigationView.getHeaderView(0);
            TextView name = (TextView)headerLayout.findViewById(R.id.mainNevName);
            TextView mail = (TextView)headerLayout.findViewById(R.id.mainNevMail);

            name.setText(AndroidSuperApp.CurrAppUser.getContactName().GetFullName());
            mail.setText(AndroidSuperApp.CurrAppUser.getContactInfo().getEmail());

        if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.Guest))
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(true);
        }
        else
        {
            navigationView.getMenu().findItem(R.id.logout).setVisible(true);

            switch (AndroidSuperApp.CurrAppUser.getPrivilege())
            {
                case Customer:
                    navigationView.getMenu().findItem(R.id.Cus).setVisible(true);
                    break;
                case Supplier:
                    navigationView.getMenu().findItem(R.id.Sup).setVisible(true);
                    break;
                case OnlyAdmin:
                    navigationView.getMenu().findItem(R.id.Manager).setVisible(true);
                    break;
            }
        }





        /////////////////////////////////////// My Code Add ////////////////////////////////////////


        ImageButton rec1img = (ImageButton)findViewById(R.id.rec1img);
        TextView rec1name = (TextView)findViewById(R.id.rec1name);
        TextView rec1writer = (TextView)findViewById(R.id.rec1writer);
        TextView rec1id = (TextView)findViewById(R.id.rec1id);

        ImageButton rec2img = (ImageButton)findViewById(R.id.rec2img);
        TextView rec2name = (TextView)findViewById(R.id.rec2name);
        TextView rec2writer = (TextView)findViewById(R.id.rec2writer);
        TextView rec2id = (TextView)findViewById(R.id.rec2id);

        ImageButton rec3img = (ImageButton)findViewById(R.id.rec3img);
        TextView rec3name = (TextView)findViewById(R.id.rec3name);
        TextView rec3writer = (TextView)findViewById(R.id.rec3writer);
        TextView rec3id = (TextView)findViewById(R.id.rec3id);

        LinearLayout rec1 = (LinearLayout)findViewById(R.id.rec1);
        rec1.setVisibility(View.GONE);
        LinearLayout rec2 = (LinearLayout)findViewById(R.id.rec2);
        rec2.setVisibility(View.GONE);
        LinearLayout rec3 = (LinearLayout)findViewById(R.id.rec3);
        rec3.setVisibility(View.GONE);

        ArrayList<Book> allBooks = AndroidSuperApp.BL.GetAllBooks();

        Random random = new Random();

        ImageLoaderForBorrunView imageLoader = new ImageLoaderForBorrunView(this);

        if (allBooks.size()>=1)
        {
            Book b = allBooks.get(random.nextInt(allBooks.size()));
            rec1name.setText(b.getBookName());
            rec1writer.setText(b.getWriterAsString());
            imageLoader.DisplayImage(b.getURL(), rec1img);
            rec1.setVisibility(View.VISIBLE);

            ImageButtonIndex.add(rec1img.getId());
            BookIDIndex.add(b.getID());
            if (allBooks.size()>=2)
            {
                b = allBooks.get(random.nextInt(allBooks.size()));
//                rec2img.ExtraData = b.getID();
                rec2name.setText(b.getBookName());
                rec2writer.setText(b.getWriterAsString());
                imageLoader.DisplayImage(b.getURL(), rec2img);
                rec2.setVisibility(View.VISIBLE);

                ImageButtonIndex.add(rec2img.getId());
                BookIDIndex.add(b.getID());
                if (allBooks.size()>=3)
                {
                    b = allBooks.get(random.nextInt(allBooks.size()));
                    rec3name.setText(b.getBookName());
                    rec3writer.setText(b.getWriterAsString());
                    imageLoader.DisplayImage(b.getURL(), rec3img);
                    rec3.setVisibility(View.VISIBLE);

                    ImageButtonIndex.add(rec3img.getId());
                    BookIDIndex.add(b.getID());
                }
            }
        }

        /////////////////////////////////////////////////////////////////
        ImageButton holy1img = (ImageButton)findViewById(R.id.holy1img);
        TextView holy1name = (TextView)findViewById(R.id.holy1name);
        TextView holy1writer = (TextView)findViewById(R.id.holy1writer);
        TextView holy1id = (TextView)findViewById(R.id.holy1id);


        ImageButton holy2img = (ImageButton)findViewById(R.id.holy2img);
        TextView holy2name = (TextView)findViewById(R.id.holy2name);
        TextView holy2writer = (TextView)findViewById(R.id.holy2writer);
        TextView holy2id = (TextView)findViewById(R.id.holy2id);


        ImageButton holy3img = (ImageButton)findViewById(R.id.holy3img);
        TextView holy3name = (TextView)findViewById(R.id.holy3name);
        TextView holy3writer = (TextView)findViewById(R.id.holy3writer);
        TextView holy3id = (TextView)findViewById(R.id.holy3id);

        allBooks = AndroidSuperApp.BL.GetBooksByParameters("Category",Category.Holy.toString());

        LinearLayout holy1 = (LinearLayout)findViewById(R.id.holy1);
        holy1.setVisibility(View.GONE);
        LinearLayout holy2 = (LinearLayout)findViewById(R.id.holy2);
        holy2.setVisibility(View.GONE);
        LinearLayout holy3 = (LinearLayout)findViewById(R.id.holy3);
        holy3.setVisibility(View.GONE);

        if (allBooks.size()>=1) {
            Book b = allBooks.get(0);
            holy1id.setText(b.getID());
            holy1name.setText(b.getBookName());
            holy1writer.setText(b.getWriterAsString());
            imageLoader.DisplayImage(b.getURL(), holy1img);
            holy1.setVisibility(View.VISIBLE);

            ImageButtonIndex.add(holy1img.getId());
            BookIDIndex.add(b.getID());
            if (allBooks.size() >= 2) {
                b = allBooks.get(1);
                holy2id.setText(b.getID());
                holy2name.setText(b.getBookName());
                holy2writer.setText(b.getWriterAsString());
                imageLoader.DisplayImage(b.getURL(), holy2img);
                holy2.setVisibility(View.VISIBLE);

                ImageButtonIndex.add(holy2img.getId());
                BookIDIndex.add(b.getID());
                if (allBooks.size() >= 3) {
                    b = allBooks.get(2);
                    holy3id.setText(b.getID());
                    holy3name.setText(b.getBookName());
                    holy3writer.setText(b.getWriterAsString());
                    imageLoader.DisplayImage(b.getURL(), holy3img);
                    holy3.setVisibility(View.VISIBLE);

                    ImageButtonIndex.add(holy3img.getId());
                    BookIDIndex.add(b.getID());
                }
            }
        }

            ////////////////////////////////////////////////////////////////////


            ImageButton kids1img = (ImageButton) findViewById(R.id.kids1img);
            TextView kids1name = (TextView) findViewById(R.id.kids1name);
            TextView kids1writer = (TextView) findViewById(R.id.kids1writer);
            TextView kids1id = (TextView) findViewById(R.id.kids1id);

            ImageButton kids2img = (ImageButton) findViewById(R.id.kids2img);
            TextView kids2name = (TextView) findViewById(R.id.kids2name);
            TextView kids2writer = (TextView) findViewById(R.id.kids2writer);
            TextView kids2id = (TextView) findViewById(R.id.kids2id);

            ImageButton kids3img = (ImageButton) findViewById(R.id.kids3img);
            TextView kids3name = (TextView) findViewById(R.id.kids3name);
            TextView kids3writer = (TextView) findViewById(R.id.kids3writer);
            TextView kids3id = (TextView) findViewById(R.id.kids3id);


            try {
                allBooks = AndroidSuperApp.BL.GetBooksByParameters("Category", Category.Children.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LinearLayout kids1 = (LinearLayout) findViewById(R.id.kids1);
            kids1.setVisibility(View.GONE);
            LinearLayout kids2 = (LinearLayout) findViewById(R.id.kids2);
            kids2.setVisibility(View.GONE);
            LinearLayout kids3 = (LinearLayout) findViewById(R.id.kids3);
            kids3.setVisibility(View.GONE);

            if (allBooks.size() >= 1) {
                Book b = allBooks.get(0);
                kids1id.setText(b.getID());
                kids1name.setText(b.getBookName());
                kids1writer.setText(b.getWriterAsString());
                imageLoader.DisplayImage(b.getURL(), kids1img);
                kids1.setVisibility(View.VISIBLE);

                ImageButtonIndex.add(kids1img.getId());
                BookIDIndex.add(b.getID());
                if (allBooks.size() >= 2) {
                    b = allBooks.get(1);
                    kids2id.setText(b.getID());
                    kids2name.setText(b.getBookName());
                    kids2writer.setText(b.getWriterAsString());
                    imageLoader.DisplayImage(b.getURL(), kids2img);
                    kids2.setVisibility(View.VISIBLE);

                    ImageButtonIndex.add(kids2img.getId());
                    BookIDIndex.add(b.getID());
                    if (allBooks.size() >= 3) {
                        b = allBooks.get(2);
//                    kids3img.setTag(0,b.getID());
                        kids3name.setText(b.getBookName());
                        kids3writer.setText(b.getWriterAsString());
                        imageLoader.DisplayImage(b.getURL(), kids3img);
                        kids3.setVisibility(View.VISIBLE);

                        ImageButtonIndex.add(kids3img.getId());
                        BookIDIndex.add(b.getID());
                    }
                }
            }


            ///////////////////////////////////////////////////////////////////////


            ImageButton history1img = (ImageButton) findViewById(R.id.history1img);
            TextView history1name = (TextView) findViewById(R.id.history1name);
            TextView history1writer = (TextView) findViewById(R.id.history1writer);
            TextView history1id = (TextView) findViewById(R.id.history1id);

            ImageButton history2img = (ImageButton) findViewById(R.id.history2img);
            TextView history2name = (TextView) findViewById(R.id.history2name);
            TextView history2writer = (TextView) findViewById(R.id.history2writer);
            TextView history2id = (TextView) findViewById(R.id.history2id);

            ImageButton history3img = (ImageButton) findViewById(R.id.history3img);
            TextView history3name = (TextView) findViewById(R.id.history3name);
            TextView history3writer = (TextView) findViewById(R.id.history3writer);
            TextView history3id = (TextView) findViewById(R.id.history3id);


            try {
                allBooks = AndroidSuperApp.BL.GetBooksByParameters("Category", Category.History.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LinearLayout history1 = (LinearLayout) findViewById(R.id.history1);
            history1.setVisibility(View.GONE);
            LinearLayout history2 = (LinearLayout) findViewById(R.id.history2);
            history2.setVisibility(View.GONE);
            LinearLayout history3 = (LinearLayout) findViewById(R.id.history3);
            history3.setVisibility(View.GONE);

            if (allBooks.size() >= 1) {
                Book b = allBooks.get(0);
                history1id.setText(b.getID());
                history1name.setText(b.getBookName());
                history1writer.setText(b.getWriterAsString());
                imageLoader.DisplayImage(b.getURL(), history1img);
                history1.setVisibility(View.VISIBLE);

                ImageButtonIndex.add(history1img.getId());
                BookIDIndex.add(b.getID());

                if (allBooks.size() >= 2) {
                    b = allBooks.get(1);
                    history2id.setText(b.getID());
                    history2name.setText(b.getBookName());
                    history2writer.setText(b.getWriterAsString());
                    imageLoader.DisplayImage(b.getURL(), history2img);
                    history2.setVisibility(View.VISIBLE);

                    ImageButtonIndex.add(history2img.getId());
                    BookIDIndex.add(b.getID());
                    if (allBooks.size() >= 3) {
                        b = allBooks.get(2);
                        history3id.setText(b.getID());
                        history3name.setText(b.getBookName());
                        history3writer.setText(b.getWriterAsString());
                        imageLoader.DisplayImage(b.getURL(), history3img);
                        history3.setVisibility(View.VISIBLE);

                        ImageButtonIndex.add(history3img.getId());
                        BookIDIndex.add(b.getID());
                    }
                }
            }


            //////////////////////////////////////////////////////////////////////

            ImageButton pro1img = (ImageButton) findViewById(R.id.pro1img);
            TextView pro1name = (TextView) findViewById(R.id.pro1name);
            TextView pro1writer = (TextView) findViewById(R.id.pro1writer);
            TextView pro1id = (TextView) findViewById(R.id.pro1id);


            ImageButton pro2img = (ImageButton) findViewById(R.id.pro2img);
            TextView pro2name = (TextView) findViewById(R.id.pro2name);
            TextView pro2writer = (TextView) findViewById(R.id.pro2writer);
            TextView pro2id = (TextView) findViewById(R.id.pro2id);


            ImageButton pro3img = (ImageButton) findViewById(R.id.pro3img);
            TextView pro3name = (TextView) findViewById(R.id.pro3name);
            TextView pro3writer = (TextView) findViewById(R.id.pro3writer);
            TextView pro3id = (TextView) findViewById(R.id.pro3id);


            try {
                allBooks = AndroidSuperApp.BL.GetBooksByParameters("Category", Category.Professional.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LinearLayout pro1 = (LinearLayout) findViewById(R.id.pro1);
            history1.setVisibility(View.GONE);
            LinearLayout pro2 = (LinearLayout) findViewById(R.id.pro2);
            pro2.setVisibility(View.GONE);
            LinearLayout pro3 = (LinearLayout) findViewById(R.id.pro3);
            pro3.setVisibility(View.GONE);

            if (allBooks.size() >= 1) {
                Book b = allBooks.get(0);
                pro1id.setText(b.getID());
                pro1name.setText(b.getBookName());
                pro1writer.setText(b.getWriterAsString());
                imageLoader.DisplayImage(b.getURL(), pro1img);
                pro1.setVisibility(View.VISIBLE);

                ImageButtonIndex.add(pro1img.getId());
                BookIDIndex.add(b.getID());

                if (allBooks.size() >= 2) {
                    b = allBooks.get(1);
                    pro2id.setText(b.getID());
                    pro2name.setText(b.getBookName());
                    pro2writer.setText(b.getWriterAsString());
                    imageLoader.DisplayImage(b.getURL(), pro2img);
                    pro2.setVisibility(View.VISIBLE);

                    ImageButtonIndex.add(pro2img.getId());
                    BookIDIndex.add(b.getID());

                    if (allBooks.size() >= 3) {
                        b = allBooks.get(2);
                        pro3id.setText(b.getID());
                        pro3name.setText(b.getBookName());
                        pro3writer.setText(b.getWriterAsString());
                        imageLoader.DisplayImage(b.getURL(), pro3img);
                        pro3.setVisibility(View.VISIBLE);

                        ImageButtonIndex.add(pro3img.getId());
                        BookIDIndex.add(b.getID());
                    }
                }
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.main, menu);
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


        if (id == R.id.RecCategoryMenu) {
            Intent intent = new Intent(this,ShowAllBooksInCategory.class);
            intent.putExtra(KEY_FILTER,"all");
            startActivity(intent);
        } else if (id == R.id.HolyCategoryMenu) {
            Intent intent = new Intent(this,ShowAllBooksInCategory.class);
            intent.putExtra(KEY_FILTER,Category.Holy.toString());
            startActivity(intent);
        } else if (id == R.id.KidsCategoryMenu) {
            Intent intent = new Intent(this,ShowAllBooksInCategory.class);
            intent.putExtra(KEY_FILTER, Category.Children.toString());
            startActivity(intent);
        } else if (id == R.id.historyCategoryMenu) {
            Intent intent = new Intent(this,ShowAllBooksInCategory.class);
            intent.putExtra(KEY_FILTER,Category.History.toString());
            startActivity(intent);
        } else if (id == R.id.ProCategoryMenu) {
            Intent intent = new Intent(this,ShowAllBooksInCategory.class);
            intent.putExtra(KEY_FILTER,Category.Professional.toString());
            startActivity(intent);
        } else if (id == R.id.AddBookMenu) {
            startActivity(new Intent(this,AddBook.class));
        } else if (id == R.id.SupCartMenu) {
            Intent CIntent= new Intent(this,CartActivity.class);
            String flag="supplier";
            CIntent.putExtra(CartActivity.KEY_FLAG, flag);
            startActivity(CIntent);
        } else if (id == R.id.StockMenu) {
            startActivity(new Intent(this,Stock.class));
        } else if (id == R.id.PreOrdersMenu) {
            startActivity(new Intent(this, OldCarts.class));
        } else if (id == R.id.CartMenu) {
            Intent CIntent= new Intent(this,CartActivity.class);
            String flag="new";
            CIntent.putExtra(CartActivity.KEY_FLAG, flag);
            startActivity(CIntent);
        } else if (id == R.id.UpdateUserMenu) {
        }else if (id == R.id.AddAdminMenu) {
            startActivity(new Intent(this,AddUser.class));
        }else if (id == R.id.AccountMenu) {

        }else if (id == R.id.login) {
            startActivity(new Intent(this, com.jook.Login_Screen.class));
        }
        else if (id == R.id.logout)
        {
            AndroidSuperApp.onLogOut();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.getMenu().findItem(R.id.Cus).setVisible(false);
            navigationView.getMenu().findItem(R.id.Sup).setVisible(false);
            navigationView.getMenu().findItem(R.id.Manager).setVisible(false);
            Toast.makeText(MainActivity.this, "משתמש התנתק בהצלחה", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if (id == R.id.exit)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void RecBookOpenCategory(View view)
    {
        Intent intent = new Intent(this,ShowAllBooksInCategory.class);
        intent.putExtra(KEY_FILTER,"all");
        startActivity(intent);

    }
    public void holyBookOpenCategory(View view)
    {
        Intent intent = new Intent(this,ShowAllBooksInCategory.class);
        intent.putExtra(KEY_FILTER,Category.Holy.toString());
        startActivity(intent);
    }
    public void kidsBookOpenCategory(View view)
    {
        Intent intent = new Intent(this,ShowAllBooksInCategory.class);
        intent.putExtra(KEY_FILTER, Category.Children.toString());
        startActivity(intent);

    }
    public void historyBookOpenCategory(View view)
    {
        Intent intent = new Intent(this,ShowAllBooksInCategory.class);
        intent.putExtra(KEY_FILTER,Category.History.toString());
        startActivity(intent);

    }
    public void proBookOpenCategory(View view)
    {
        Intent intent = new Intent(this,ShowAllBooksInCategory.class);
        intent.putExtra(KEY_FILTER,Category.Professional.toString());
        startActivity(intent);
    }

    public void ClickOnBook(View view)
    {
        String BookID = "";
        ImageButton imageButton = (ImageButton)view;

        for (int i=0;i<ImageButtonIndex.size();i++)
        {
            if (ImageButtonIndex.get(i).equals(imageButton.getId()))
            {
                BookID = BookIDIndex.get(i);
                break;
            }
        }


        if(AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.Supplier))
        {

            try {

                Supplier_Book sb;
                sb = AndroidSuperApp.BL.GetSupplierBook(BookID,AndroidSuperApp.CurrAppUser.getID());
                if (sb == null)
                {
                    sb = new Supplier_Book(AndroidSuperApp.CurrAppUser.getID(), BookID, 0, 0);
                    AndroidSuperApp.BL.addBookToSupplier(sb);
                }
                Intent intent = new Intent(this, ShowBookForSupplier.class);
                intent.putExtra(ShowBookMain.KEY_BOOK_ID, BookID);
                startActivity(intent);
            }
            catch (Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            };
        }
        else
        {
            Intent intent = new Intent(getBaseContext(), ShowBookMain.class);
            intent.putExtra(ShowBookMain.KEY_BOOK_ID, BookID);
            startActivity(intent);
        }



    }

}
