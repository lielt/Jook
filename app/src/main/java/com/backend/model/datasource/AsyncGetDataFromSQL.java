package com.backend.model.datasource;

import android.os.AsyncTask;

/**
 * Created by ליאל on 04/01/2016.
 */
public class AsyncGetDataFromSQL extends AsyncTask<String,Void,String>
{

    @Override
    protected String doInBackground(String... params)
    {
        try {
            String test = com.backend.model.datasource.SqlDS.GET("https://jookdb-liel7.c9users.io/sqlQuery.php");
            String t = "test";
        }
        catch (Exception ex)
        {

        }

        return null;
    }
}
