package com.backend.entities;

import com.R;

import java.util.Arrays;
import java.util.List;

import static com.AndroidSuperApp.getContex;

/**
 * Created by ���� on 12/11/2015.
 */
public class ContactInformation
{


    private String Phone;
    private String CellPhone;
    private String Email;
    private Address Address;
    public List<String> Prefix = Arrays.asList("050", "052", "054", "055", "057", "059", "02", "03", "04", "09", "072", "077");

    public ContactInformation(String phone, String cellPhone, String email, com.backend.entities.Address address) {
        Phone = phone;
        CellPhone = cellPhone;
        Email = email;
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) throws Exception
    {
        if (!SystemFunc.tryParseInt(phone))
            throw new Exception(getContex().getString(R.string.PhoneCharErr));

        int Size = phone.length();
        if (Size < 9 || Size > 10)
            throw new Exception(getContex().getString(R.string.PhoneLengthErr));

        if ((Size == 9 && !Arrays.asList(Prefix).contains(phone.substring(0,1))) || !Arrays.asList(Prefix).contains(phone.substring(0,2)))
                throw new Exception(getContex().getString(R.string.PhonePrefixErr));

        Phone = phone;
    }

    public String getCellPhone() throws Exception
    {
        return CellPhone;
    }

    public void setCellPhone(String cellPhone) throws Exception{
        if (!SystemFunc.tryParseInt(cellPhone))
            throw new Exception(getContex().getString(R.string.cPhoneNumErr));

        int Size = cellPhone.length();
        if (Size != 10)
            throw new Exception(getContex().getString(R.string.cPhoneLengthErr));

        if (!Arrays.asList(Prefix).contains(cellPhone.substring(0,2)))
            throw new Exception(getContex().getString(R.string.cPhonePrefixErr));

        CellPhone = cellPhone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) throws Exception
    {
        if (!SystemFunc.isValidEmailAddress(email))
            throw new Exception(getContex().getString(R.string.EmailRegularErr));

        Email = email;
    }

    public Address getAddress() {
        return Address;
    }

    public void setAddress(String street, String num, String city) throws Exception{
        Address = new Address(street,num,city);
    }

    public ContactInformation(String phone, String cellPhone, String email, String street, String num, String city)throws Exception{
        setPhone (phone);
        setCellPhone (cellPhone);
        setEmail (email);
        setAddress(street, num, city);
    }
}
