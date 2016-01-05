package com.backend.model.datasource;

import com.backend.entities.Admin;
import com.backend.entities.Book;
import com.backend.entities.Cart;
import com.backend.entities.Customer;
import com.backend.entities.Order;
import com.backend.entities.Recommendation;
import com.backend.entities.Supplier;
import com.backend.entities.Supplier_Book;
import com.backend.entities.User;
import com.backend.model.backend.Backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

public class SqlDS implements Backend
{

    public static final String web_url = "https://jookdb-liel7.c9users.io/";

    public static String GET(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            return "";
        }
    }

    private static String POST(String url, Map<String,Object> params) throws IOException {

        //Convert Map<String,Object> into key=value&key=value pairs.
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(postData.toString().getBytes("UTF-8"));
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        else return "";
    }

    @Override
    public void AddBook(Book book) throws Exception
    {
        String ThickCover = book.isThickCover()? "0" : "1";
        String commend = ("INSERT INTO  `jookdb`.`Books` (\n" +
                "`id` ,\n" +
                "`name` ,\n" +
                "`writer_f_name` ,\n" +
                "`writer_l_name` ,\n" +
                "`publisher` ,\n" +
                "`thick_cover` ,\n" +
                "`year` ,\n" +
                "`category` ,\n" +
                "`url`\n" +
                ")\n" +
                "VALUES (\n" +
                "NULL,"+
                book.getBookName() +
                ","+
                book.getWriter().getFirstName()+
                ","+
                book.getWriter().getLastName()+
                ","+
                book.getPublisher()+
                ","+
                ThickCover+
                ","+
                book.getCategory().toString()+
                ","+
                book.getURL()+
                ",\n"+");");

        new AsyncGetDataFromSQL().execute("set",commend);

    }

    @Override
    public void UpdateBook(Book book) throws Exception {

    }

    @Override
    public void DeleteBook(Book book) throws Exception {

    }

    @Override
    public User GetUserByMail(String mail) {
        return null;
    }

    @Override
    public User GetUserByID(String id) {
        return null;
    }

    @Override
    public void AddCustomer(Customer customer) throws Exception {

    }

    @Override
    public void UpdateCustomer(Customer customer) throws Exception {

    }

    @Override
    public void DeleteCustomer(Customer customer) throws Exception {

    }

    @Override
    public void AddSupplier(Supplier supplier) throws Exception {

    }

    @Override
    public void UpdateSupplier(Supplier supplier) throws Exception {

    }

    @Override
    public void DeleteSupplier(Supplier supplier) throws Exception {

    }

    @Override
    public void addBookToSupplier(Supplier_Book sbook) throws Exception {

    }

    @Override
    public void removeBookFromSupplier(Supplier_Book sbook) throws Exception {

    }

    @Override
    public void updateBookOnSupplier(Supplier_Book sbook) throws Exception {

    }

    @Override
    public Supplier_Book GetSupplierBook(String book, String supplier) {
        return null;
    }

    @Override
    public void AddAdmin(Admin admin) throws Exception {

    }

    @Override
    public void UpdateAdmin(Admin admin) throws Exception {

    }

    @Override
    public void DeleteAdmin(Admin admin) throws Exception {

    }

    @Override
    public void AddRecommendation(Recommendation recommendation) throws Exception {

    }

    @Override
    public void UpdateRecommendation(Recommendation recommendation) throws Exception {

    }

    @Override
    public void DeleteRecommendation(Recommendation recommendation) throws Exception {

    }

    @Override
    public ArrayList<Recommendation> GetRecommendationByRecommendedID(String string) {
        return null;
    }

    @Override
    public void AddCart(Cart cart) throws Exception {

    }

    @Override
    public void UpdateCart(Cart cart) throws Exception {

    }

    @Override
    public ArrayList<Order> GetAllCartOrders(String CartID) {
        return null;
    }

    @Override
    public void AddOrder(Order order) throws Exception {

    }

    @Override
    public void UpdateOrder(Order order) throws Exception {

    }

    @Override
    public void DeleteOrder(Order order) throws Exception {

    }

    @Override
    public Order GetOrderByID(String OrderID) {
        return null;
    }

    @Override
    public ArrayList<Book> GetAllBooks() throws Exception {
        return null;
    }

    @Override
    public ArrayList<Book> GetBooksByParameters(String... List) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Supplier_Book> GetSupplierByBook(String BookID) throws Exception {
        return null;
    }

    @Override
    public Supplier GetSupplierByID(String supplierID) throws Exception {
        return null;
    }

    @Override
    public String GenerateRunCartID() {
        return null;
    }

    @Override
    public void CartIDNext() {

    }

    @Override
    public String GenerateRunOrderID() {
        return null;
    }

    @Override
    public void OrderIDNext() {

    }

    @Override
    public Cart DiscountPolicy(Cart cart) throws Exception {
        return null;
    }
}
