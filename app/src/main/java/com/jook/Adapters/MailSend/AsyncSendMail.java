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
            String Body;
            switch (params[0]) {
                case "reg":


                 Body = "שלום לך מר: " + params[1] + " " + "נרשמת בהצלחה לאפליקציה jook בתור " + params[2] +
                        " " + "שים לב ששם המשתמש שלך הוא: " + params[3] + " והסיסמה היא: " + params[4] +
                        "... דיר באלק שאתה מאבד אותם!!! כי אם אתה חושב שהיה לנו כוח לממש גם שחזר סיסמה אתה חי בסרט";
                sender.sendMail("נרשמת בהצלחה לאפליקציית jook!!",
                        Body,
                        "jookAdmin@gmail.com",
                        params[3]);
                    break;
                case "invite":
                    Body="ביצעת הזמנה באפליקציה jook";
                    sender.sendMail("הזמנה חדשה",Body,"jookAdmin@gmail.com",params[1]);
                   break;
                case "supinv":
                    Body="בוצעה הזמנה לספר: " +params[1]+" "+ "בכמות:"+params[2]+" "+"על ידי: "+params[3]+" "+
                            "אנא צור עימו קשר במייל: "+params[4];
                    sender.sendMail("הזמנה חדשה",Body,"jookAdmin@gmail.com",params[5]);
                case "delete":
                    Body= "הספר: "+params[1]+" "+ "נמחק ממאגר הספרים שלך עקב היותו יקר מדי ממדיניות האפליקציה"
                            +"/n"+"נשמח שתשנה את המחיר למחיר סביר יותר, צוות האפליקציה jook";
                    sender.sendMail("מחיקת הזמנה",Body,"jookAdmin@gmail.com",params[2]);
            }
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
