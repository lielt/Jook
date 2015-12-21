package com.backend.entities;
import com.R;

import static com.AndroidSuperApp.getContex;


public class Recommendation
{
    private String IDRecomends;
    private String IDRecomended;
    private int Rate;
    private String Content;

    public Recommendation(String IDRecomends, String IDRecomended, int rate, String content) {
        this.IDRecomends = IDRecomends;
        this.IDRecomended = IDRecomended;
        Rate = rate;
        Content = content;
    }

    public String getIDRecomends() {
        return IDRecomends;
    }

    public String getIDRecomended() {
        return IDRecomended;
    }

    public int getRate() {
        return Rate;
    }

    public String getContent() {
        return Content;
    }

    public void setIDRecomends(String IDRecomends) throws Exception
    {
        this.IDRecomends = IDRecomends;
    }


    public void setIDRecomended(String IDRecomended) throws Exception
    {
        if (!SystemFunc.tryParseInt(IDRecomended) || IDRecomended.length() != 9)
            throw new Exception(getContex().getString(R.string.RecoomnedetionWorngID));
        this.IDRecomended = IDRecomended;
    }

    public void setRate(int rate) throws Exception
    {
        if (rate >= 1 && rate <= 5)
            Rate = rate;
        else
            throw new Exception("Rate rang is between 1-5");
    }

    public void setContent(String content) {
        Content = content;
    }
}
