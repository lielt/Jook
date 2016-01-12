package com.backend.model.datasource;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ליאל on 04/01/2016.
 */
public class AsyncGetDataFromSQL extends AsyncTask<String,Void,String>
{
    public SqlDS mySqlDS;

    public AsyncGetDataFromSQL() {
        this.mySqlDS = null;
    }
    public AsyncGetDataFromSQL(SqlDS mySqlDS) {
        this.mySqlDS = mySqlDS;
    }
    public AsyncGetDataFromSQL(Activity ac) {
        try {
        }
        catch (Exception ex)
        {

        }
    }

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
            else if (params[0].equals("get"))
            {



























            }
            else if (params[0].equals("orders"))
            {

            }

        }
        catch (Exception ex)
        {

        }

        return null;
    }
}
