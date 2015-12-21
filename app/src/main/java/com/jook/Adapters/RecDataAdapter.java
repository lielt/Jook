package com.jook.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.R;
import com.jook.DownloadImageFromNetTools.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import static com.AndroidSuperApp.getContex;

public class RecDataAdapter extends BaseAdapter
{
    public static final String KEY_RECOMMENDS = "Recommends";
    public static final String KEY_RATE = "Rate";
    public static final String KEY_CONTENT = "Content";


    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public RecDataAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {
        this.activity = activity;
        this.data = data;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() { return data.size();  }

    public Object getItem(int position) { return position; }

    public long getItemId(int position) {return position;}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi= convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.recoomentetion_list_row, null);

        TextView recommends = (TextView)vi.findViewById(R.id.rec_view_recommended_text);
        ImageView thumb_image = (ImageView)vi.findViewById(R.id.rec_view_rate_img);
        TextView content = (TextView)vi.findViewById(R.id.rec_content_text); // duration


        HashMap<String, String> sup = new HashMap<String, String>();
        sup = data.get(position);

        // Setting all values in listview
        recommends.setText(sup.get(KEY_RECOMMENDS));
        content.setText(sup.get(KEY_CONTENT));


        switch (sup.get(KEY_RATE))
        {
            case "1":
                thumb_image.setImageDrawable(getContex().getDrawable(R.drawable.star_1));
                break;
            case "2":
                thumb_image.setImageDrawable(getContex().getDrawable(R.drawable.star_2));
                break;
            case "3":
                thumb_image.setImageDrawable(getContex().getDrawable(R.drawable.star_3));
                break;
            case "4":
                thumb_image.setImageDrawable(getContex().getDrawable(R.drawable.star_4));
                break;
            case "5":
                thumb_image.setImageDrawable(getContex().getDrawable(R.drawable.star_5));
                break;
            default:
                thumb_image.setImageDrawable(getContex().getDrawable(R.drawable.star_0));
        }

        return vi;
    }

}
