package com.jook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Book;
import com.backend.entities.User;
import com.backend.enums.Privilege;
import com.jook.Adapters.BookDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Stock extends AppCompatActivity {

    public static final String KEY_B_ID = "id";
    public static final String KEY_B_TITLE = "title";
    public static final String KEY_B_WRITER = "writer";
    public static final String KEY_B_YEAR = "year";
    public static final String KEY_B_THUMB_URL = "thumb_url";
    private static ListView list;
    private static BookDataAdapter adapter;
    private static Context mCtx;
    private static Activity mAct;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCtx = this;
        mAct = this;

        setContentView(R.layout.activity_stock);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddBook.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "הכול";
                case 1:
                    return "לפני סיום";
                case 2:
                    return "אזלו";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_stock, container, false);
            User mUser = AndroidSuperApp.CurrAppUser;

            if (mUser!=null && mUser.getPrivilege().equals(Privilege.Supplier))
            {
                try {
                    ArrayList<Book> db = new ArrayList<Book>();

                    int sectionFilter = getArguments().getInt(ARG_SECTION_NUMBER);
                    switch (sectionFilter)
                    {
                        case 1:
                            db = AndroidSuperApp.BL.GetBooksByParameters("supplier",mUser.getID());
                            break;
                        case 2:
                            db = AndroidSuperApp.BL.GetBooksByParameters("supplier",mUser.getID(),"maxamount","4","minamount","1");
                            break;
                        case 3:
                            db = AndroidSuperApp.BL.GetBooksByParameters("supplier",mUser.getID(),"maxamount","0");
                            break;
                    }

                    if (db == null)
                        return rootView;

                    final ArrayList<HashMap<String, String>> BookList = new ArrayList<HashMap<String, String>>();

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

                    list=(ListView)rootView.findViewById(R.id.list_for_book);

                    // Getting adapter by passing data ArrayList
                    adapter = new BookDataAdapter(mAct,BookList);
                    list.setAdapter(adapter);

                    // Click event for single list row
                    list.setOnItemClickListener(
                            new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    String BookId = BookList.get(position).get(KEY_B_ID);

                                    Intent intent = new Intent(mCtx, ShowBookForSupplier.class);
                                    intent.putExtra(ShowBookMain.KEY_BOOK_ID, BookId);
                                    mAct.finish();
                                    startActivity(intent);
                                }
                            });
                }
                catch (Exception ex)
                {
                    Toast.makeText(mCtx, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            return rootView;
        }
    }
}
