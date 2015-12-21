package com.backend.entities;

import com.backend.enums.PayWay;
import com.R;

import static com.AndroidSuperApp.getContex;

public class Customer extends User
{
    private PayWay PaymentMethod;
    private String RecommendedBy;
    private int NumOfRecommends;

    public Customer(String ID, String firstName, String lastName, String phone, String cellPhone, String email, String street, String num, String city, String applicationPassword, boolean block, PayWay paymentMethod, String recommendedBy, int numOfRecommends) throws Exception {
        super(ID, firstName, lastName, phone, cellPhone, email, street, num, city, applicationPassword, block);
        PaymentMethod = paymentMethod;
        RecommendedBy = recommendedBy;
        NumOfRecommends = numOfRecommends;
    }

    public PayWay getPaymentMethod() {
        return PaymentMethod;
    }

    public String getRecommendedBy() {
        return RecommendedBy;
    }

    public int getNumOfRecommends() {
        return NumOfRecommends;
    }

    public void setPaymentMethod(PayWay paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public void setRecommendedBy(String recommendedBy) throws Exception
    {
        if (!SystemFunc.tryParseInt(recommendedBy) || recommendedBy.length() != 9)
            throw new Exception(getContex().getString(R.string.RecoomnedetionWorngID));

        RecommendedBy = recommendedBy;
    }

    public void setNumOfRecommends(int NumOfRecommends) throws Exception
    {
        if (NumOfRecommends <= 0)
            throw new Exception(getContex().getString(R.string.RecommendetionIndexNegetivErr));
            NumOfRecommends = NumOfRecommends;
    }
}
