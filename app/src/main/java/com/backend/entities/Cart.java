package com.backend.entities;

import com.R;

import static com.AndroidSuperApp.getContex;

public class Cart
{


    private String ID;
    private String CustomerID;
    private float TotalPrice;
    private int DiscountPrecent;
    private int NumOfOrders;
    private float OriginalPrice;

    public Cart() {
    }

    public Cart(String ID, String customerID, float totalPrice, int discountPrecent, int numOfOrders, float originalPrice) {
        this.ID = ID;
        CustomerID = customerID;
        TotalPrice = totalPrice;
        DiscountPrecent = discountPrecent;
        NumOfOrders = numOfOrders;
        OriginalPrice = originalPrice;
    }

    public String getID() {
        return ID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public float getTotalPrice() {
        return TotalPrice;
    }

    public int getDiscountPrecent() {
        return DiscountPrecent;
    }

    public int getNumOfOrders() {
        return NumOfOrders;
    }

    public float getOriginalPrice() {
        return OriginalPrice;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setCustomerID(String customerID) throws Exception
    {
        if (!SystemFunc.tryParseInt(customerID) || customerID.length() != 9)
            throw new Exception(getContex().getString(R.string.CustomerIdDigitErr));
        CustomerID = customerID;
    }

    public void setTotalPrice(float totalPrice) {
        TotalPrice = totalPrice;
    }

    public void setDiscountPrecent(int discountPrecent) throws Exception
    {
        if (discountPrecent<=0 || discountPrecent>=100)
            throw new Exception(getContex().getString(R.string.DiscountPrecentRangeErr));
        DiscountPrecent = discountPrecent;
    }

    public void setNumOfOrders(int numOfOrders) {
        NumOfOrders = numOfOrders;
    }

    public void setOriginalPrice(float originalPrice) {
        OriginalPrice = originalPrice;
    }
}
