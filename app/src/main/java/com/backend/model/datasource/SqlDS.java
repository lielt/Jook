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

    public static String POST(String url, Map<String,Object> params) throws IOException {

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

    private static ListDS listDS;

    public static ArrayList<DataLoadFromWeb> dataList;

    public class DataLoadFromWeb<T>
    {
        public boolean isInLoad;
        public ArrayList<T> arrayList;
        public String Result;
        public Exception  ex;

        public DataLoadFromWeb()
        {
            isInLoad = true;
            arrayList = new ArrayList<T>();
            ex=null;
        }
    }

    public SqlDS()
    {
        dataList = new ArrayList<DataLoadFromWeb>();
        try {
            dataList.add(new DataLoadFromWeb<Admin>() );
            dataList.add(new DataLoadFromWeb<Book>() );
            dataList.add(new DataLoadFromWeb<Cart>() );
            dataList.add(new DataLoadFromWeb<Customer>() );
            dataList.add(new DataLoadFromWeb<Recommendation>() );
            dataList.add(new DataLoadFromWeb<Supplier>() );
            dataList.add(new DataLoadFromWeb<Supplier_Book>() );
        }
        catch (Exception ex)
        {
            String asd ="asdasd";
            String asdasd= asd;
        }


        // get all arrays
        new AsyncGetDataFromSQL(this).execute("get");

        // chackDownloadState
        while (dataList.get(0).isInLoad  || dataList.get(1).isInLoad  || dataList.get(2).isInLoad  || dataList.get(3).isInLoad  || dataList.get(4).isInLoad  || dataList.get(5).isInLoad  || dataList.get(6).isInLoad)
        {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex)
            {

            }
        }


        listDS  = new ListDS(this);
    }

    @Override
    public void AddBook(Book book) throws Exception
    {
        try
        {
            listDS.AddBook(book);
            String ThickCover = book.isThickCover()? "0" : "1";
            String commend = ("INSERT INTO `Books`(`id`,`name`,`writer_f_name`,`writer_l_name`,`publisher`,`thick_cover`,`year`,`category`,`url`)VALUES(" +
                    "\"" +
                    book.getID() + "\"" +
                    ",\"" +
                    book.getBookName() + "\"" +
                    ",\""+
                    book.getWriter().getFirstName()+ "\"" +
                    ",\""+
                    book.getWriter().getLastName()+ "\"" +
                    ",\""+
                    book.getPublisher()+ "\"" +
                    ","+
                    ThickCover +
                    ","+
                    book.getYear() +
                    ",\""+
                    book.getCategory().toString()+ "\"" +
                    ",\""+
                    book.getURL()+ "\"" + ")");

            new AsyncGetDataFromSQL().execute("set", commend);

        }
        catch (Exception ex)
        {
            throw ex;
        }


    }

    @Override
    public void UpdateBook(Book book) throws Exception
    {
        try {
            listDS.UpdateBook(book);
            String ThickCover = book.isThickCover()? "0" : "1";
            String commend = "UPDATE `Books` SET `name`=\""+book.getBookName()+"\",`writer_f_name`=\""+book.getWriter().getFirstName()+"\",`writer_l_name`=\""+book.getWriter().getLastName()+"\",`publisher`=\"" + book.getPublisher()+"\",`thick_cover`="+ThickCover+",`year`="+book.getYear()+",`category`=\""+book.getCategory().toString()+"\",`url`=\""+book.getURL()+"\" WHERE `id`=\""+book.getID()+"\"";
            new AsyncGetDataFromSQL().execute("set", commend);

        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    @Override
    public void DeleteBook(Book book) throws Exception
    {

        try {
            listDS.DeleteBook(book);
            String commend = "DELETE FROM `Books` WHERE `id` = \"" + book.getID() + "\"";
            new AsyncGetDataFromSQL().execute("set", commend);

        }
        catch (Exception ex)
        {
            throw ex;
        }


    }

    @Override
    public User GetUserByMail(String mail) {
        return listDS.GetUserByMail(mail);
    }

    @Override
    public User GetUserByID(String id) {
        return listDS.GetUserByID(id);
    }

    @Override
    public void AddCustomer(Customer customer) throws Exception
    {
        try {
            listDS.AddCustomer(customer);
            String isBlock = customer.isBlock() ? "0" : "1";
            String commend = ("INSERT INTO `Customers` (`id`, `firstName`, `lastName`, `phone`, `cellPhone`, `email`, `street`, `num`, `city`, `applicationPassword`, `block`, `PaymentMethod`, `RecommendedBy`, `NumOfRecommends`) VALUES (" +
                    "\"" +
                    customer.getID() + "\"" +
                    ",\"" +
                    customer.getContactName().getFirstName() + "\"" +
                    ",\"" +
                    customer.getContactName().getLastName() + "\"" +
                    ",\"" +
                    customer.getContactInfo().getPhone() + "\"" +
                    ",\"" +
                    customer.getContactInfo().getCellPhone() + "\"" +
                    ",\"" +
                    customer.getContactInfo().getEmail() + "\"" +
                    ",\"" +
                    customer.getContactInfo().getAddress().getStreet() + "\"" +
                    ",\"" +
                    customer.getContactInfo().getAddress().getNum() + "\"" +
                    ",\"" +
                    customer.getContactInfo().getAddress().getCity() + "\"" +
                    ",\"" +
                    customer.getApplicationPassword() + "\"" +
                    "," +
                    isBlock +
                    ",\"" +
                    customer.getPaymentMethod().toString() + "\"" +
                    ",\"" +
                    customer.getRecommendedBy().toString() + "\"" +
                    "," +
                    customer.getNumOfRecommends() + ")");

            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }


    @Override
    public void UpdateCustomer(Customer customer) throws Exception
    {
        try {
            listDS.UpdateCustomer(customer);
            String isBlock = customer.isBlock() ? "0" : "1";
            String commend ="UPDATE `Customers` SET ,`firstName`= "+addMarks(customer.getContactName().getFirstName())+" ,`lastName`="+addMarks(customer.getContactName().getLastName())+
                    ",`phone`="+addMarks(customer.getContactInfo().getPhone())+",`cellPhone`="+addMarks(customer.getContactInfo().getCellPhone())+
                    ",`email`="+addMarks(customer.getContactInfo().getEmail())+",`street`="+addMarks(customer.getContactInfo().getAddress().getStreet())+
                    ",`num`="+addMarks(customer.getContactInfo().getAddress().getNum())+",`city`="+addMarks(customer.getContactInfo().getAddress().getCity())+
                    ",`applicationPassword`="+addMarks(customer.getApplicationPassword())+",`block`="+isBlock+",`PaymentMethod`="+addMarks(customer.getPaymentMethod().toString())+
                    ",`RecommendedBy`="+addMarks(customer.getRecommendedBy())+",`NumOfRecommends`="+customer.getNumOfRecommends()+
                    " WHERE id="+addMarks(customer.getID());

            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void DeleteCustomer(Customer customer) throws Exception
    {
        try {
            listDS.DeleteCustomer(customer);
            String commend = "DELETE FROM `Customers` WHERE id="+addMarks(customer.getID());
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    @Override
    public void AddSupplier(Supplier supplier) throws Exception
    {
        try
        {
            listDS.AddSupplier(supplier);
            String isBlock = supplier.isBlock() ? "0" : "1";
            String commend  = "INSERT INTO `Suppliers` (`id`, `firstName`, `lastName`, `phone`, `cellPhone`, `email`, `street`, `num`, `city`, `applicationPassword`, `block`, `BusinessName`, `ShippingMethod`, `Rate`)  VALUES (" +
                    addMarkAndPshik(supplier.getID())+addMarkAndPshik(supplier.getContactName().getFirstName())+addMarkAndPshik(supplier.getContactName().getLastName())+
                    addMarkAndPshik(supplier.getContactInfo().getPhone())+addMarkAndPshik(supplier.getContactInfo().getCellPhone())+addMarkAndPshik(supplier.getContactInfo().getEmail())+
                    addMarkAndPshik(supplier.getContactInfo().getAddress().getStreet())+addMarkAndPshik(supplier.getContactInfo().getAddress().getNum())+addMarkAndPshik(supplier.getContactInfo().getAddress().getCity())+
                    addMarkAndPshik(supplier.getApplicationPassword())+isBlock+","+addMarkAndPshik(supplier.getBusinessName())+addMarkAndPshik(supplier.getShippingMethod().toString())+supplier.getRate()+")";

            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void UpdateSupplier(Supplier supplier) throws Exception
    {
        try {
            listDS.UpdateSupplier(supplier);
            String isBlock = supplier.isBlock() ? "0" : "1";
            String commend ="UPDATE `Suppliers` SET ,`firstName`= "+addMarks(supplier.getContactName().getFirstName())+" ,`lastName`="+addMarks(supplier.getContactName().getLastName())+
                    ",`phone`="+addMarks(supplier.getContactInfo().getPhone())+",`cellPhone`="+addMarks(supplier.getContactInfo().getCellPhone())+
                    ",`email`="+addMarks(supplier.getContactInfo().getEmail())+",`street`="+addMarks(supplier.getContactInfo().getAddress().getStreet())+
                    ",`num`="+addMarks(supplier.getContactInfo().getAddress().getNum())+",`city`="+addMarks(supplier.getContactInfo().getAddress().getCity())+
                    ",`applicationPassword`="+addMarks(supplier.getApplicationPassword())+",`block`="+isBlock+",`BusinessName`="+addMarks(supplier.getBusinessName())+
                    ",`ShippingMethod`="+addMarks(supplier.getShippingMethod().toString())+",`Rate`="+supplier.getRate()+
                    " WHERE id="+addMarks(supplier.getID());

            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void DeleteSupplier(Supplier supplier) throws Exception
    {
        try {
            listDS.DeleteSupplier(supplier);
            String commend = "DELETE FROM `Suppliers` WHERE id="+addMarks(supplier.getID());
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void addBookToSupplier(Supplier_Book sbook) throws Exception
    {
        try
        {
            listDS.addBookToSupplier(sbook);
            String commend  = "INSERT INTO `Supplier_Book` (`SupplierID`, `BookID`, `Amount`, `Price`) VALUES (" +
                    addMarkAndPshik(sbook.getSupplierID())+ addMarkAndPshik(sbook.getBookID())+ sbook.getAmount()+ sbook.getPrice()+")";
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void removeBookFromSupplier(Supplier_Book sbook) throws Exception
    {
        try {
            listDS.removeBookFromSupplier(sbook);
            String commend = "DELETE FROM `Supplier_Book` WHERE `SupplierID` =" +addMarks(sbook.getSupplierID())  +  " AND `BookID` =" + addMarks(sbook.getBookID());
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void updateBookOnSupplier(Supplier_Book sbook) throws Exception
    {
        try {
            listDS.updateBookOnSupplier(sbook);
            String commend ="UPDATE `Supplier_Book` SET `Amount`=" + sbook.getAmount() +",`Price`=" + sbook.getPrice()+
                    " WHERE `SupplierID`="+addMarks(sbook.getSupplierID())+" AND `BookID` = " +  addMarks(sbook.getBookID());
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Supplier_Book GetSupplierBook(String book, String supplier) {
        return listDS.GetSupplierBook(book, supplier);
    }

    @Override
    public void AddAdmin(Admin admin) throws Exception
    {
        try
        {
            listDS.AddAdmin(admin);
            String isBlock = admin.isBlock() ? "0" : "1";
            String commend  = "INSERT INTO `Admin` (`id`, `firstName`, `lastName`, `phone`, `cellPhone`, `email`, `street`, `num`, `city`, `applicationPassword`, `block`, `level`)  VALUES (" +
                    addMarkAndPshik(admin.getID())+addMarkAndPshik(admin.getContactName().getFirstName())+addMarkAndPshik(admin.getContactName().getLastName())+
                    addMarkAndPshik(admin.getContactInfo().getPhone())+addMarkAndPshik(admin.getContactInfo().getCellPhone())+addMarkAndPshik(admin.getContactInfo().getEmail())+
                    addMarkAndPshik(admin.getContactInfo().getAddress().getStreet())+addMarkAndPshik(admin.getContactInfo().getAddress().getNum())+addMarkAndPshik(admin.getContactInfo().getAddress().getCity())+
                    addMarkAndPshik(admin.getApplicationPassword())+isBlock+","+addMarks(admin.getLevel().toString())+")";

            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    @Override
    public void UpdateAdmin(Admin admin) throws Exception
    {
        try {
            listDS.UpdateAdmin(admin);
            String isBlock = admin.isBlock() ? "0" : "1";
            String commend ="UPDATE `Admin` SET ,`firstName`= "+addMarks(admin.getContactName().getFirstName())+" ,`lastName`="+addMarks(admin.getContactName().getLastName())+
                    ",`phone`="+addMarks(admin.getContactInfo().getPhone())+",`cellPhone`="+addMarks(admin.getContactInfo().getCellPhone())+
                    ",`email`="+addMarks(admin.getContactInfo().getEmail())+",`street`="+addMarks(admin.getContactInfo().getAddress().getStreet())+
                    ",`num`="+addMarks(admin.getContactInfo().getAddress().getNum())+",`city`="+addMarks(admin.getContactInfo().getAddress().getCity())+
                    ",`applicationPassword`="+addMarks(admin.getApplicationPassword())+",`block`="+isBlock+",`level`="+addMarks(admin.getLevel().toString())+
                    " WHERE id="+addMarks(admin.getID());

            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    @Override
    public void DeleteAdmin(Admin admin) throws Exception
    {
        try {
            listDS.DeleteAdmin(admin);
            String commend = "DELETE FROM `Admin` WHERE id="+addMarks(admin.getID());
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    @Override
    public void AddRecommendation(Recommendation recommendation) throws Exception
    {
        try
        {
            listDS.AddRecommendation(recommendation);
            String commend  = "INSERT INTO `Recommends` (`IDRecomends`, `IDRecomended`, `Rate`, `Content`) VALUES (" +
                    addMarkAndPshik(recommendation.getIDRecomends())+ addMarkAndPshik(recommendation.getIDRecomended())+ recommendation.getRate() + "," + addMarks(recommendation.getContent())+")";
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void UpdateRecommendation(Recommendation recommendation) throws Exception
    {
        try {
            listDS.UpdateRecommendation(recommendation);
            String commend ="UPDATE `Recommends` SET `Rate`=" + recommendation.getRate() +",`Content`=" + addMarks(recommendation.getContent())+
                    " WHERE `IDRecomends`="+addMarks(recommendation.getIDRecomends())+" AND `IDRecomended` = " + addMarks(recommendation.getIDRecomended());
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }


    }

    @Override
    public void DeleteRecommendation(Recommendation recommendation) throws Exception
    {
        try {
            listDS.DeleteRecommendation(recommendation);
            String commend = "DELETE FROM `Recommends` WHERE `IDRecomends` =" +addMarks(recommendation.getIDRecomends())  +  " AND `IDRecomended` =" + addMarks(recommendation.getIDRecomended());
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    @Override
    public ArrayList<Recommendation> GetRecommendationByRecommendedID(String string)
    {
        return listDS.GetRecommendationByRecommendedID(string);
    }

    @Override
    public void AddCart(Cart cart) throws Exception
    {
        try
        {
            listDS.AddCart(cart);
            String commend  = "INSERT INTO `Carts` (`id`, `CustomerID`) VALUES (" +  addMarkAndPshik(cart.getID()) + addMarks(cart.getCustomerID())+")";
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    @Override
    public void UpdateCart(Cart cart) throws Exception
    {
        try
        {
            listDS.UpdateCart(cart);
            String commend ="UPDATE `Carts` SET `TotalPrice`=" + cart.getTotalPrice() +",`DiscountPrecent`=" + cart.getDiscountPrecent()+
                            ",`NumOfOrders`=" + cart.getNumOfOrders() + ",`OriginalPrice`=" + cart.getOriginalPrice()+
                            " WHERE `id`=" + cart.getID();
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public ArrayList<Order> GetAllCartOrders(String CartID) {
        return listDS.GetAllCartOrders(CartID);
    }

    @Override
    public void AddOrder(Order order) throws Exception
    {
        try
        {
            listDS.AddOrder(order);
             String commend  = "INSERT INTO `Orders` (`ID`, `CartId`, `SupplierId`, `BookId`, `Amount`) VALUES (" +
                    addMarkAndPshik(order.getId()) + addMarkAndPshik(order.getCartId()) + addMarkAndPshik(order.getSupplierId())+
                    addMarkAndPshik(order.getBookId())+ order.getAmount() +")";
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    @Override
    public void UpdateOrder(Order order) throws Exception
    {
        try
        {
            listDS.UpdateOrder(order);
            String commend ="UPDATE `Orders` SET `Amount`=" + order.getAmount() +
                    " WHERE `ID`=" + addMarkAndPshik(order.getId()) + "AND `CartId`=" + addMarkAndPshik(order.getCartId());
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void DeleteOrder(Order order) throws Exception
    {
        try {
            listDS.DeleteOrder(order);
            String commend = "DELETE FROM `Orders` WHERE `ID`=" + addMarkAndPshik(order.getId());
            new AsyncGetDataFromSQL().execute("set", commend);
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    @Override
    public Order GetOrderByID(String OrderID) {
        return listDS.GetOrderByID(OrderID);
    }

    @Override
    public ArrayList<Book> GetAllBooks() throws Exception {
        return listDS.GetAllBooks();
    }

    @Override
    public ArrayList<Book> GetBooksByParameters(String... List) throws Exception {
        return listDS.GetBooksByParameters(List);
    }

    @Override
    public ArrayList<Supplier_Book> GetSupplierByBook(String BookID) throws Exception {
        return listDS.GetSupplierByBook(BookID);
    }

    @Override
    public Supplier GetSupplierByID(String supplierID) throws Exception {
        return listDS.GetSupplierByID(supplierID);
    }

    @Override
    public String GenerateRunCartID() {
        return listDS.GenerateRunCartID();
    }

    @Override
    public void CartIDNext()
    {
        listDS.CartIDNext();
    }

    @Override
    public String GenerateRunOrderID() {
        return listDS.GenerateRunOrderID();
    }

    @Override
    public void OrderIDNext() {
        listDS.OrderIDNext();

    }

    @Override
    public Cart DiscountPolicy(Cart cart) throws Exception {
        return listDS.DiscountPolicy(cart);
    }

    private String addMarks(String s)
    {return "\""+s+"\"";}
    private String addMarkAndPshik(String s)
    {return "\""+s+"\",";}

}
