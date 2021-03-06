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
import com.backend.entities.Customer;
import com.backend.enums.PayWay;
import com.backend.enums.Privilege;
import com.jook.Adapters.MailSend.AsyncSendMail;

import static com.backend.entities.SystemFunc.GetPayWay;

public class Add_Customer extends AppCompatActivity {

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
        setContentView(R.layout.activity_add__customer);
        MyIntent = getIntent();
        ID = MyIntent.getStringExtra(ConstValue.ID);
        PrivateNames= MyIntent.getStringExtra(ConstValue.PrivateName);
        FamilyNames= MyIntent.getStringExtra(ConstValue.FamilyName);
        City= MyIntent.getStringExtra(ConstValue.City);
        Street= MyIntent.getStringExtra(ConstValue.Street);
        Building= MyIntent.getStringExtra(ConstValue.Building);
        Email= MyIntent.getStringExtra(ConstValue.Email);
        Phone= MyIntent.getStringExtra(ConstValue.Phone);
        CellPhone= MyIntent.getStringExtra(ConstValue.CellPhone);
    }

    public void CreateCustomer(View view) {
        try {
            String Password = (((EditText) findViewById(R.id.get_user_password)).getText()).toString();
            String RecommendedBy = (((EditText) findViewById(R.id.get_customer_recomended)).getText()).toString();
            String payways=(((Spinner)findViewById(R.id.get_customer_payway)).getSelectedItem()).toString();
            PayWay payWay = GetPayWay(payways);
            Customer newCus = new Customer(ID, PrivateNames, FamilyNames, Phone, CellPhone, Email, Street, Building, City, Password, false,payWay,RecommendedBy , 0);

            AndroidSuperApp.BL.AddCustomer(newCus);

            newCus.setPrivilege(Privilege.Customer);
            AndroidSuperApp.CurrAppUser = newCus;

            Toast.makeText(this,"לקוח נוסף בהצלחה", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            AddUser.AddUserActivity.finish();

            new AsyncSendMail().execute("reg",newCus.getContactName().GetFullName(), "לקוח", newCus.getContactInfo().getEmail(), newCus.getApplicationPassword());

        }
        catch (Exception ex)
        {
            Toast.makeText(Add_Customer.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        };

    }
}
