package com.backend.entities;

import com.R;
import com.backend.enums.Privilege;

import static com.AndroidSuperApp.getContex;

/**
 * Created by ���� on 12/11/2015.
 */
public class User
{
    private String ID;
    private Name ContactName;
    private ContactInformation ContactInfo;
    private String ApplicationPassword;
    private boolean Block;
    private Privilege Privilege;

    public User(String ID, String firstName, String lastName, String phone, String cellPhone, String email, String street, String num, String city, String applicationPassword, boolean block) throws Exception
    {
        setID(ID);
        ContactName = new Name(firstName, lastName);
        ContactInfo = new ContactInformation(phone,cellPhone,email,new Address(street,num,city));
        ApplicationPassword = applicationPassword;
        Block = block;
    }

    public String getID() {
        return ID;
    }

    public Name getContactName() {
        return ContactName;
    }

    public ContactInformation getContactInfo() {
        return ContactInfo;
    }

    public String getApplicationPassword() {
        return ApplicationPassword;
    }

    public boolean isBlock() {
        return Block;
    }

    public void setID(String ID)throws Exception{
        if (!SystemFunc.tryParseInt(ID) || ID.length() != 9)
            throw new Exception(getContex().getString(R.string.WorngUserIDErr));
        this.ID = ID;
    }

    public Privilege getPrivilege() {
        return Privilege;
    }

    public void setPrivilege(Privilege privilege) {
        Privilege = privilege;
    }

    public void setContactName(String FName, String LName) throws  Exception{
        ContactName =new Name(FName,LName);
    }

    public void setContactInfo(String phone, String cellPhone, String email, String street, String num, String city)throws Exception{
        ContactInfo=new ContactInformation (phone, cellPhone, email, street, num, city);
    }

    public void setApplicationPassword(String applicationPassword) throws  Exception
    {
        if (applicationPassword.length()!=8)
            throw new Exception(getContex().getString(R.string.UserPasswordErr));
        ApplicationPassword = applicationPassword;
    }

    public void setBlock(boolean block) {
        Block = block;
    }


    public Boolean isCustomer()
    {
        return (getPrivilege().equals(com.backend.enums.Privilege.Customer)||getPrivilege().equals(com.backend.enums.Privilege.Super));
    }

    public Boolean isSupplier()
    {
        return (getPrivilege().equals(com.backend.enums.Privilege.Supplier)||getPrivilege().equals(com.backend.enums.Privilege.Super));
    }

    public Boolean isAdmin()
    {
        return (getPrivilege().equals(com.backend.enums.Privilege.OnlyAdmin)||getPrivilege().equals(com.backend.enums.Privilege.Super));
    }


}
