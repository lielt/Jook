package com.backend.entities;

import com.R;

import static com.AndroidSuperApp.getContex;
import static com.backend.entities.SystemFunc.StringContainNonChar;

/**
 * Created by ���� on 11/11/2015.
 */
public class Name  {


    private String FirstName;
    private String LastName;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) throws  Exception
    {
        if (!StringContainNonChar(firstName))
            FirstName = firstName;
        else throw new Exception(getContex().getString(R.string.fNameNumErr));
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName)throws Exception
    {
        if (!StringContainNonChar(lastName))
            LastName = lastName;
        else throw new Exception(getContex().getString(R.string.lNameNumErr));

    }

    public Name(String firstName, String lastName) throws Exception{
        setFirstName ( firstName);
        setLastName ( lastName);
    }

    public String GetFullName()
    {
        return getFirstName() + " " + getLastName();
    }
}
