package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.R;

public class Add_Supplier extends AppCompatActivity {

    Intent MyIntent=getIntent();

    String ID = MyIntent.getStringExtra(ConstValue.ID);
    String PrivateNames= MyIntent.getStringExtra(ConstValue.PrivateName);
    String FamilyNames= MyIntent.getStringExtra(ConstValue.FamilyName);
    String City= MyIntent.getStringExtra(ConstValue.City);
    String Street= MyIntent.getStringExtra(ConstValue.Street);
    String Building= MyIntent.getStringExtra(ConstValue.Building);
    String Email= MyIntent.getStringExtra(ConstValue.Email);
    String Phone= MyIntent.getStringExtra(ConstValue.Phone);
    String CellPhone= MyIntent.getStringExtra(ConstValue.CellPhone);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__supplier);
    }
}
