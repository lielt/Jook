package com.backend.entities;

import com.backend.enums.Category;
import com.R;

import java.util.Calendar;

import static com.AndroidSuperApp.getContex;


public class Book
{


    private String ID;
    private String BookName;
    private Name Writer;
    private String Publisher;
    private boolean ThickCover;
    private int Year;
    private Category Category;
    private String URL;

    public Book(String ID, String bookName, Name writer, String publisher, boolean thickCover, int year, com.backend.enums.Category category, String URL)
    {
        this.ID = ID;
        BookName = bookName;
        Writer = writer;
        Publisher = publisher;
        ThickCover = thickCover;
        Year = year;
        Category = category;
        this.URL = URL;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public Name getWriter() {
        return Writer;
    }

    public String getWriterAsString(){return Writer.GetFullName();}

    public void setWriter(String FName, String LName) throws Exception{
        Writer= new Name(FName,LName);
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public boolean isThickCover() {
        return ThickCover;
    }

    public void setThickCover(boolean thickCover) {
        ThickCover = thickCover;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) throws Exception {
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year <= currYear && year >= (currYear - 100))
            Year = year;
        else
            throw new Exception(getContex().getString(R.string.BookYearRangeErr));
    }

    public Category getCategory() {
        return Category;
    }

    public void setCategory(Category category) {
        Category = category;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

}
