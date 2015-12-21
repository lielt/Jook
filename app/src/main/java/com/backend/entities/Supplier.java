package com.backend.entities;

import com.backend.enums.PayWay;
import com.backend.enums.Ship;
import com.R;

import static com.AndroidSuperApp.getContex;

/**
 * Created by Yosi Carmeli on 12/11/2015.
 */
public class Supplier extends User
{
    private String BusinessName;
    private Ship ShippingMethod;
    private PayWay PaymentMethod;
    private int Rate;


    public Supplier(String ID, String firstName, String lastName, String phone, String cellPhone, String email, String street, String num, String city, String applicationPassword, boolean block,String businessName, Ship shippingMethod, PayWay paymentMethod, int rate) throws Exception{
        super(ID, firstName,lastName, phone, cellPhone, email, street, num, city, applicationPassword, block);
        BusinessName = businessName;
        ShippingMethod = shippingMethod;
        PaymentMethod = paymentMethod;
        Rate = rate;
    }

    public Supplier(Supplier source) throws Exception
    {
        this(source.getID(),source.getContactName().getFirstName(),source.getContactName().getLastName(),source.getContactInfo().getPhone(),source.getContactInfo().getCellPhone(),source.getContactInfo().getEmail(),source.getContactInfo().getAddress().getStreet(),source.getContactInfo().getAddress().getNum(),source.getContactInfo().getAddress().getCity(),source.getApplicationPassword(),source.isBlock(),source.getBusinessName(),source.getShippingMethod(), source.getPaymentMethod(),source.getRate());
    }


    public String getBusinessName() {
        return BusinessName;
    }

    public Ship getShippingMethod() {
        return ShippingMethod;
    }

    public PayWay getPaymentMethod() {
        return PaymentMethod;
    }

    public int getRate() {
        return Rate;
    }

    public String getShippingMethodAsString()
    {
        switch (this.getShippingMethod())
        {
            case RegisteredMail:
                return getContex().getString(R.string.RegisterdMail);
            case CourierService:
                return getContex().getString(R.string.RunerMail);
            case UPS:
                return getContex().getString(R.string.UPS);
            case Pickup:
                return getContex().getString(R.string.SelfPickUP);
        }

        return "";
    }



    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public void setShippingMethod(Ship shippingMethod) {
        ShippingMethod = shippingMethod;
    }

    public void setPaymentMethod(PayWay paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public void setRate(int rate) {
        Rate = rate;
    }
}
