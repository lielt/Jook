package com.jook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.R;
import com.jook.Adapters.AsyncLoadDataOnStartUp;

public class Loading extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new AsyncLoadDataOnStartUp().execute(this);
    }

}
