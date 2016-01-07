package com.backend.model.datasource;

import android.os.AsyncTask;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ליאל on 04/01/2016.
 */
public class AsyncGetDataFromSQL extends AsyncTask<String,Void,String>
{

    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            // params[0] = get/set
            // params[1] = query

            if (params[0].equals("set"))
            {
                Map<String, Object> q = new LinkedHashMap<>();
                q.put("query",params[1]);
                String result = SqlDS.POST(SqlDS.web_url + "setQuery.php",q);
 
            }

        }
        catch (Exception ex)
        {

        }

        return null;
    }
}
