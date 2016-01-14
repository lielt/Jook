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

    public Book(String ID, String bookName, Name writer, String publisher, boolean thickCover, int year, com.backend.enums.Category category, String URL) throws Exception
    {
        setID(ID);
        setBookName(bookName);
        setWriter(writer.getFirstName(),writer.getLastName());
        setPublisher(publisher);
        ThickCover = thickCover;
        setYear(year);
        Category = category;
        this.URL = URL;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) throws Exception{
        if (ID.equals("") || ID == null)
            throw new Exception("Book id cennot by empty!!");

        this.ID = ID;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName)throws Exception
    {
        if (bookName.equals("") || bookName == null)
            throw new Exception("Book name cennot by empty!!");
        BookName = bookName;
    }

    public Name getWriter() {
        return Writer;
    }

    public String getWriterAsString(){return Writer.GetFullName();}

    public void setWriter(String FName, String LName) throws Exception{
        Writer= new Name(FName,LName);
        if (Writer.GetFullName().equals("") || Writer == null)
            throw new Exception("Book Writer cennot by empty!!");
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher)throws Exception
    {
        if (publisher.equals("") || publisher == null)
            throw new Exception("Book publisher cennot by empty!!");
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
        if (year <= currYear && year >= (currYear - 200))
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
