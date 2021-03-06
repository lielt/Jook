package com.backend.entities;

import com.backend.enums.Category;
import com.backend.enums.PayWay;
import com.backend.enums.Ship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ���� on 12/11/2015.
 */
public final class SystemFunc {
    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean tryParseFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean StringContainNonChar(String s) {
        if (s == null || s.trim().isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-zא-ת]");
        Matcher m = p.matcher(s);

        return m.find();
    }


    public static boolean isValidEmailAddress(String email) {
        boolean stricterFilter = true;
        String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
        String emailRegex = stricterFilter ? stricterFilterString : laxString;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailRegex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static  Ship GetShip(String s)
    {
        switch(s)
        {
            case "דואר שליחים":
                return Ship.CourierService;
            case "איסוף עצמי":
                return Ship.Pickup;
            case "דואר רשום":
                return Ship.RegisteredMail;
            case "UPS":
                return Ship.UPS;
            default:
                return null;

        }
    }

    public static PayWay GetPayWay(String s)
    {
        switch(s)
        {
            case "העברה בנקאית":
                return PayWay.BankTransfer;
            case "מזומן":
                return PayWay.Cash;
            case "צק":
                return PayWay.Check;
            case "אשראי":
                return PayWay.Credit;
            default:
                return null;

        }
    }

    public static Category GetCategory(String s)
    {
        switch(s)
        {
            case "ילדים":
                return Category.Children;
            case "היסטוריה":
                return Category.History;
            case "קודש":
                return Category.Holy;
            case "ספרות מקצועית":
                return Category.Professional;
            default:
                return null;

        }
    }

    public static void CheckIfStringAreEmpty(String ... list) throws Exception
    {
        for(String s: list )
        {
            if (s.equals(""))
            {
                throw new Exception("יש למלא את כל השדות כדי להמשיך");
            }
        }

    }

}