package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.AndroidSuperApp;
import com.R;
import com.backend.enums.Privilege;

public class AddUser extends AppCompatActivity {


    public static final String ID = "addUserID";
    public static final String PrivateName = "addUserPrivateName";
    public static final String FamilyName = "addUserFamilyName";
    public static final String City = "addUserCity";
    public static final String Street = "addUserStreet";
    public static final String Building = "addUserBuilding";
    public static final String Email = "addUserEmail";
    public static final String Phone = "addUserPhone";
    public static final String CellPhone = "addUserCellPhone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        if (AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.OnlyAdmin)) {


            LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.options);
            LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.add_admin);
            linearLayout1.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
        }
    }

    public void RegAsCusButton(View view)
    {
        Intent intent = new Intent(this,Add_Customer.class);
        addDataAsExtra(intent);
        startActivity(intent);

    }

    public  void RegAsSupButton (View view)
    {
        Intent intent =new Intent(this,Add_Supplier.class);
        addDataAsExtra(intent);
        startActivity(intent);

    }



    private void addDataAsExtra(Intent intent)
    {
        try {
            String IDs = (String) ((TextView) findViewById(R.id.get_user_id)).getText();
            intent.putExtra(ID, IDs);
            String PrivateNames = (String) ((TextView) findViewById(R.id.get_user_private_name)).getText();
            intent.putExtra(PrivateName, PrivateNames);
            String FamilyNames = (String) ((TextView) findViewById(R.id.get_user_family_name)).getText();
            intent.putExtra(FamilyName, FamilyNames);
            String Citys = (String) ((TextView) findViewById(R.id.get_user_city)).getText();
            intent.putExtra(City, Citys);
            String Streets = (String) ((TextView) findViewById(R.id.get_user_street)).getText();
            intent.putExtra(Street, Streets);
            String Buildings = (String) ((TextView) findViewById(R.id.get_user_building)).getText();
            intent.putExtra(Building, Buildings);
            String Emails = (String) ((TextView) findViewById(R.id.get_mail)).getText();
            intent.putExtra(Email, Emails);
            String Phones = (String) ((TextView) findViewById(R.id.get_user_phone_number)).getText();
            intent.putExtra(Phone, Phones);
            String CellPhones = (String) ((TextView) findViewById(R.id.get_user_cellphone_number)).getText();
            intent.putExtra(CellPhone, CellPhones);
        }
        catch(Exception ex){};

    }


    public void RegAsAdmin(View view) {
        Intent intent =new Intent(this,Add_Admin.class);
        addDataAsExtra(intent);
        startActivity(intent);
    }
}
