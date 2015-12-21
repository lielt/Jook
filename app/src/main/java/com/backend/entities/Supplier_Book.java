package com.backend.entities;

import com.R;

import static com.AndroidSuperApp.getContex;

/**
 * Created by Yosi Carmeli on 12/11/2015.
 */
public class Supplier_Book
{
    private String SupplierID;
    private String BookID;
    private int Amount;
    private float Price;

    public Supplier_Book(String supplierID, String bookID, int amount, float price) {
        SupplierID = supplierID;
        BookID = bookID;
        Amount = amount;
        Price = price;
    }

    public Supplier_Book(String supplierID, String bookID) {
        SupplierID = supplierID;
        BookID = bookID;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public String getBookID() {
        return BookID;
    }

    public int getAmount() {
        return Amount;
    }

    public float getPrice() {
        return Price;
    }

    public void setSupplierID(String supplierID) throws Exception{
        if (!SystemFunc.tryParseInt(supplierID) || supplierID.length() != 9)
            throw new Exception(getContex().getString(R.string.SupplierBookSupIdErr));
        SupplierID = supplierID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public void setAmount(int amount)throws Exception
    {
        if (amount<1)
            throw new Exception(getContex().getString(R.string.WorngAmuntForSupplierBookErr));
        Amount = amount;
    }

    public void setPrice(float price)throws Exception{
        if (price<0)
            throw new Exception(getContex().getString(R.string.WorngPriceForSupplierBookErr));
        Price = price;
    }
}
