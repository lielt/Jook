package com.backend.entities;

import com.R;

import static com.AndroidSuperApp.getContex;
import static com.backend.entities.SystemFunc.StringContainNonChar;


public class Address
{
   private String Street;
   private String Num;
   private String City;
    private String no;



    public Address(String street, String num, String city) throws Exception {

        setStreet(street);
        setNum(num);
        setCity(city);
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) throws Exception     {
        if (!StringContainNonChar(street))
            Street = street;
        else throw new Exception (getContex().getString(R.string.StreetCharErr));
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) throws Exception     {
        if (Integer.parseInt(num) > 0)
            Num = num;
        else throw new Exception (getContex().getString(R.string.StreetNumErr));
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city)throws Exception {
        if (!StringContainNonChar(city))
            City = city;
        else throw new Exception(getContex().getString(R.string.CityCharErr));
    }

}
