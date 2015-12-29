package com.jook.Adapters.MailSend;

import android.os.AsyncTask;

/**
 * Created by ליאל on 28/12/2015.
 */
public class AsyncSendMail extends AsyncTask<String,Void,String>
{

    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            GMailSender sender = new GMailSender("jookmanager1@gmail.com", "12369091");
            sender.sendMail("זהו מייל אוטומטי מהאפליקציה המטורפת jook שתחייה!!!!",
                    "הידד!!!!!!",
                    "jookAdmin@gmail.com",
                    "ybcarmeli@gmail.com");
        }
        catch (Exception ex)
        {

        }

        return null;
    }

    protected void onPostExecute() {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
