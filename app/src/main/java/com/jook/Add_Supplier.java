package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Supplier;
import com.backend.enums.PayWay;
import com.backend.enums.Ship;

import static com.backend.entities.SystemFunc.GetPayWay;
import static com.backend.entities.SystemFunc.GetShip;

public class Add_Supplier extends AppCompatActivity {

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
        setContentView(R.layout.activity_add__supplier);

        MyIntent=getIntent();
        ID =MyIntent.getStringExtra(ConstValue.ID);
        PrivateNames=MyIntent.getStringExtra(ConstValue.PrivateName);
        FamilyNames=MyIntent.getStringExtra(ConstValue.FamilyName);
        City=MyIntent.getStringExtra(ConstValue.City);
        Street=MyIntent.getStringExtra(ConstValue.Street);
        Building=MyIntent.getStringExtra(ConstValue.Building);
        Email=MyIntent.getStringExtra(ConstValue.Email);
        Phone=MyIntent.getStringExtra(ConstValue.Phone);
        CellPhone=MyIntent.getStringExtra(ConstValue.CellPhone);
    }

    public void CreateSupplier(View view) {
        try {
            String Password = (((EditText) findViewById(R.id.get_user_password)).getText()).toString();
            String BuisnessName = (((EditText) findViewById(R.id.get_buisness_name)).getText()).toString();
            String payways= ((Spinner)findViewById(R.id.get_supplier_payway)).getSelectedItem().toString();
            String ships= ((Spinner)findViewById(R.id.get_supplier_delivery_way)).getSelectedItem().toString();

            Ship ShippingWay = GetShip(ships);
            PayWay payWay = GetPayWay(payways);
            Supplier newSup = new Supplier(ID, PrivateNames, FamilyNames, Phone, CellPhone, Email, Street, Building, City, Password, false, BuisnessName, ShippingWay, payWay, 0);
            AndroidSuperApp.BL.AddSupplier(newSup);

            Toast.makeText(Add_Supplier.this, "ספק נוסף בהצלחה", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            AddUser.AddUserActivity.finish();

        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        };
    }
}
