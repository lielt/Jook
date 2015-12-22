package com.jook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Customer;
import com.backend.enums.PayWay;

import static com.backend.entities.SystemFunc.GetPayWay;

public class Add_Customer extends AppCompatActivity {

    Intent MyIntent;

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
        MyIntent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__customer);
    }

    public void CreateCustomer(View view) {
        try {
            String Password = (((EditText) findViewById(R.id.get_user_password)).getText()).toString();
            String RecommendedBy = (((EditText) findViewById(R.id.get_customer_recomended)).getText()).toString();
            String payways=(((Spinner)findViewById(R.id.get_customer_payway)).getSelectedItem()).toString();
            PayWay payWay = GetPayWay(payways);
            Customer newCus = new Customer(ID, PrivateNames, FamilyNames, Phone, CellPhone, Email, Street, Building, City, Password, false,payWay,RecommendedBy , 0);
            AndroidSuperApp.BL.AddCustomer(newCus);
        }catch (Exception ex){};
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
