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

            String Body = "שלום לך מר: " + params[0] + " " + "נרשמת בהצלחה לאפליקציה jook בתור " + params[1] +
                            " " + "שים לב ששם המשתמש שלך הוא: " + params[2] + " והסיסמה היא: " + params[3] +
                           "... דיר באלק שאתה מאבד אותם!!! כי אם אתה חושב שהיה לנו כוח לממש גם שחזר סיסמה אתה חי בסרט";
            sender.sendMail("נרשמת בהצלחה לאפליקציית jook!!",
                    Body,
                    "jookAdmin@gmail.com",
                    params[2]);
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
