package com.jook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Admin;
import com.backend.enums.Level;
import com.backend.enums.Privilege;
import com.jook.Adapters.MailSend.AsyncSendMail;

public class Add_Admin extends AppCompatActivity {

    Intent MyIntent;

    String ID;
    String PrivateNames;
    String FamilyNames;
    String City;
    String Street;
    String Building;
    String Email;
    String Phone;
    String CellPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__admin);
        MyIntent = getIntent();
        ID = MyIntent.getStringExtra(ConstValue.ID);
        PrivateNames = MyIntent.getStringExtra(ConstValue.PrivateName);
        FamilyNames = MyIntent.getStringExtra(ConstValue.FamilyName);
        City = MyIntent.getStringExtra(ConstValue.City);
        Street = MyIntent.getStringExtra(ConstValue.Street);
        Building = MyIntent.getStringExtra(ConstValue.Building);
        Email = MyIntent.getStringExtra(ConstValue.Email);
        Phone = MyIntent.getStringExtra(ConstValue.Phone);
        CellPhone = MyIntent.getStringExtra(ConstValue.CellPhone);
    }

    public void CreateAdmin(View view) {
        try {
            String Password = (((EditText) findViewById(R.id.get_user_password)).getText()).toString();
            Admin newSup;
            if (((Switch) findViewById(R.id.get_permission)).isChecked()) {
                newSup = new Admin(ID, PrivateNames, FamilyNames, Phone, CellPhone, Email, Street, Building, City, Password, false, Level.Administrator);
            } else {
                newSup = new Admin(ID, PrivateNames, FamilyNames, Phone, CellPhone, Email, Street, Building, City, Password, false, Level.Editor);
            }
            AndroidSuperApp.BL.AddAdmin(newSup);

            newSup.setPrivilege(Privilege.OnlyAdmin);
            AndroidSuperApp.CurrAppUser = newSup;

            Toast.makeText(this, "מנהל נוסף בהצלחה", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            AddUser.AddUserActivity.finish();

            new AsyncSendMail().execute(newSup.getContactName().GetFullName(), "מנהל", newSup.getContactInfo().getEmail(), newSup.getApplicationPassword());

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
        ;
    }

}
