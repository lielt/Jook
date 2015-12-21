package com.jook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.R;
import com.jook.DownloadImageFromNetTools.ImageLoader;
import com.jook.ShowAllBooksInCategory;

import java.util.ArrayList;
import java.util.HashMap;

public class BookDataAdapter extends BaseAdapter
{
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public BookDataAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {
        this.activity = activity;
        this.data = data;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) { return position; }

    public long getItemId(int position) {return position;}

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi= convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.book_view_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView writer = (TextView)vi.findViewById(R.id.writer); // artist name
        TextView year = (TextView)vi.findViewById(R.id.year); // duration
        ImageView thumb_image =(ImageView)vi.findViewById(R.id.book_list_image); // thumb image

        HashMap<String, String> book = new HashMap<String, String>();
        book = data.get(position);

        // Setting all values in listview
        title.setText(book.get(ShowAllBooksInCategory.KEY_B_TITLE));
        writer.setText(book.get(ShowAllBooksInCategory.KEY_B_WRITER));
        year.setText(book.get(ShowAllBooksInCategory.KEY_B_YEAR));
        imageLoader.DisplayImage(book.get(ShowAllBooksInCategory.KEY_B_THUMB_URL), thumb_image);

        return vi;
    }
}
