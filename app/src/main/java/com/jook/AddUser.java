package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.enums.Privilege;

import static com.backend.entities.SystemFunc.CheckIfStringAreEmpty;

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

        if (AndroidSuperApp.CurrAppUser!=null&& AndroidSuperApp.CurrAppUser.getPrivilege().equals(Privilege.OnlyAdmin))
        {
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
            String IDs = ((EditText) findViewById(R.id.get_user_id)).getText().toString();
            intent.putExtra(ID, IDs);
            String PrivateNames =  ((EditText) findViewById(R.id.get_user_private_name)).getText().toString();
            intent.putExtra(PrivateName, PrivateNames);
            String FamilyNames =  ((EditText) findViewById(R.id.get_user_family_name)).getText().toString();
            intent.putExtra(FamilyName, FamilyNames);
            String Citys =  ((EditText) findViewById(R.id.get_user_city)).getText().toString();
            intent.putExtra(City, Citys);
            String Streets =  ((EditText) findViewById(R.id.get_user_street)).getText().toString();
            intent.putExtra(Street, Streets);
            String Buildings =  ((EditText) findViewById(R.id.get_user_building)).getText().toString();
            intent.putExtra(Building, Buildings);
            String Emails =  ((EditText) findViewById(R.id.get_mail)).getText().toString();
            intent.putExtra(Email, Emails);
            String Phones = ((EditText) findViewById(R.id.get_user_phone_number)).getText().toString();
            intent.putExtra(Phone, Phones);
            String CellPhones =  ((EditText) findViewById(R.id.get_user_cellphone_number)).getText().toString();
            intent.putExtra(CellPhone, CellPhones);

            CheckIfStringAreEmpty(IDs,PrivateNames,FamilyNames,Citys,Streets,Buildings,Emails,Phones,CellPhones);
        }
        catch(Exception ex)
        {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        };

    }




    public void RegAsAdmin(View view) {
        Intent intent =new Intent(this,Add_Admin.class);
        addDataAsExtra(intent);
        startActivity(intent);
    }
}
