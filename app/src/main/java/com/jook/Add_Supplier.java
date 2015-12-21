package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Supplier;
import com.backend.enums.PayWay;
import com.backend.enums.Ship;

import static com.backend.entities.SystemFunc.GetPayWay;
import static com.backend.entities.SystemFunc.GetShip;

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
        }catch (Exception ex){};
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
