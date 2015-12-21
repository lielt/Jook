package com.jook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Admin;
import com.backend.enums.Level;

public class Add_Admin extends AppCompatActivity {

    Intent MyIntent=getIntent();

    String ID =MyIntent.getStringExtra(ConstValue.ID);
    String PrivateNames=MyIntent.getStringExtra(ConstValue.PrivateName);
    String FamilyNames=MyIntent.getStringExtra(ConstValue.FamilyName);
    String City=MyIntent.getStringExtra(ConstValue.City);
    String Street=MyIntent.getStringExtra(ConstValue.Street);
    String Building=MyIntent.getStringExtra(ConstValue.Building);
    String Email=MyIntent.getStringExtra(ConstValue.Email);
    String Phone=MyIntent.getStringExtra(ConstValue.Phone);
    String CellPhone=MyIntent.getStringExtra(ConstValue.CellPhone);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__admin);
    }

    public void CreateAdmin(View view) {
        try {
            String Password = (((EditText) findViewById(R.id.get_user_password)).getText()).toString();
            Admin newSup;
            if (((Switch) findViewById(R.id.get_permission)).isChecked()) {
                newSup = new Admin(ID, PrivateNames, FamilyNames, Phone, CellPhone, Email, Street, Building, City, Password, false, Level.Administrator);
            }
            else
            {
                newSup = new Admin(ID, PrivateNames, FamilyNames, Phone, CellPhone, Email, Street, Building, City, Password, false, Level.Editor);
            }
            AndroidSuperApp.BL.AddAdmin(newSup);

        }catch (Exception ex){};
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
