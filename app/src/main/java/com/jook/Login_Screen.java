package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;

public class Login_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__screen);
    }

    public void log_scrren_register(View view)
    {
        Intent intent = new Intent(this,AddUser.class);
        startActivity(intent);

    }

    public void log_scrren_login(View view)
    {
        TextView username = (TextView)findViewById(R.id.user_log_mail);
        TextView userpass = (TextView)findViewById(R.id.user_log_pass);

        if (AndroidSuperApp.onLogin(username.getText().toString(),userpass.getText().toString()))
        {
            username.setText("");
            userpass.setText("");
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else
        {
            username.setText("");
            userpass.setText("");

            Toast tosat = Toast.makeText(this, "שם משתמש או סיסמא אינם נכונים", Toast.LENGTH_LONG);
            tosat.show();

        }

    }

    public void log_scrren_guest(View view)
    {
        try {
            AndroidSuperApp.insertGuestMode();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(Login_Screen.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
