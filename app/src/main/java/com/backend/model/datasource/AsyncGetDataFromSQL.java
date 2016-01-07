package com.backend.model.datasource;

import android.os.AsyncTask;

import com.backend.entities.Admin;
import com.backend.entities.Book;
import com.backend.entities.Cart;
import com.backend.entities.Customer;
import com.backend.entities.Name;
import com.backend.enums.Category;
import com.backend.enums.Level;
import com.backend.enums.PayWay;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ליאל on 04/01/2016.
 */
public class AsyncGetDataFromSQL extends AsyncTask<String,Void,String>
{
    public SqlDS mySqlDS;

    public AsyncGetDataFromSQL() {
        this.mySqlDS = null;
    }
    public AsyncGetDataFromSQL(SqlDS mySqlDS) {
        this.mySqlDS = mySqlDS;
    }

    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            // params[0] = get/set
            // params[1] = query

            if (params[0].equals("set"))
            {
                Map<String, Object> q = new LinkedHashMap<>();
                q.put("query",params[1]);
                String result = SqlDS.POST(SqlDS.web_url + "setQuery.php",q);
 
            }
            else if (params[0].equals("get"))
            {

                //GetAdmin
                SqlDS.DataLoadFromWeb<Admin> Admin_Result = mySqlDS.dataList.get(0);
                try {
                    Admin_Result.Result = SqlDS.GET(SqlDS.web_url + "sqlQuery.php?table=Admin");
                    if (!Admin_Result.Result.equals("0 results")) {
                        JSONArray adminArray = new JSONObject(Admin_Result.Result).getJSONArray("products");
                        for (int i = 0; i < adminArray.length(); i++) {
                            // com.backend.enums.Level level
                            Admin tempAdmin = new Admin(
                                    adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_ID),
                                    adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_firstName),
                                    adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_lastName),
                                    adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_phone),
                                    adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_cellPhone),
                                    adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_email),
                                    adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_street),
                                    adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_num),
                                    adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_city),
                                    adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_applicationPassword),
                                    (adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_block).equals("0") ? true : false),
                                    Level.valueOf(adminArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_level)));
                            Admin_Result.arrayList.add(tempAdmin);
                        }
                    }

                }
                catch (Exception ex)
                {
                    Admin_Result.ex = ex;
                }
                Admin_Result.isInLoad = false;


                // GetBooks
                SqlDS.DataLoadFromWeb<Book> Book_Result = mySqlDS.dataList.get(1);
                try {
                    Book_Result.Result = SqlDS.GET(SqlDS.web_url + "sqlQuery.php?table=Books");
                    if (!Book_Result.Result.equals("0 results")) {
                        JSONArray bookArray = new JSONObject(Book_Result.Result).getJSONArray("products");
                        for (int i = 0; i < bookArray.length(); i++) {
                            //(String ID, String bookName, Name writer, String publisher, boolean thickCover, int year, com.backend.enums.Category category, String URL)
                            Book tempBook = new Book(
                                    bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_ID),
                                    bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_BookName),
                                    new Name(bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_Writerf),
                                            bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_Writerl)),
                                    bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_Publisher),
                                    (bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_ThickCover).equals("0") ? true : false),
                                    bookArray.getJSONObject(i).getInt(Sql_Schema_Const_values.BOOK_Year),
                                    Category.valueOf(bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_Category)),
                                    bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_URL));
                            Book_Result.arrayList.add(tempBook);
                        }
                        Book_Result.isInLoad = false;
                    }

                }
                catch (Exception ex)
                {
                    Book_Result.ex = ex;
                }

                //GetCarts
                SqlDS.DataLoadFromWeb<Cart> CartResult = mySqlDS.dataList.get(2);
                try {
                    CartResult.Result = SqlDS.GET(SqlDS.web_url + "sqlQuery.php?table=Carts");
                    if (!CartResult.Result.equals("0 results")) {
                        JSONArray cartArray = new JSONObject(Book_Result.Result).getJSONArray("products");
                        for (int i = 0; i < cartArray.length(); i++) {
                            Cart tempCart = new Cart(
                                    cartArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_ID),
                                    cartArray.getJSONObject(i).getString(Sql_Schema_Const_values.CART_CustomerID),
                                    (float) cartArray.getJSONObject(i).getDouble(Sql_Schema_Const_values.CART_TotalPrice),
                                    cartArray.getJSONObject(i).getInt(Sql_Schema_Const_values.CART_DiscountPrecent),
                                    cartArray.getJSONObject(i).getInt(Sql_Schema_Const_values.CART_NumOfOrders),
                                    (float) cartArray.getJSONObject(i).getDouble(Sql_Schema_Const_values.CART_OriginalPrice)
                            );
                            CartResult.arrayList.add(tempCart);
                        }
                        CartResult.isInLoad = false;
                    }

                }
                catch (Exception ex)
                {
                    CartResult.ex = ex;
                }

                //GetCarts
                SqlDS.DataLoadFromWeb<Customer> CustomerResult = mySqlDS.dataList.get(2);
                try {
                    CustomerResult.Result = SqlDS.GET(SqlDS.web_url + "sqlQuery.php?table=Carts");
                    if (!CustomerResult.Result.equals("0 results"))
                    {
                        JSONArray CustomerArray = new JSONObject(CustomerResult.Result).getJSONArray("products");
                        for (int i = 0; i < CustomerArray.length(); i++)
                        {
                            //PayWay paymentMethod, String recommendedBy, int numOfRecommends
                            Customer tempCustomer = new Customer(
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_ID),
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_firstName),
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_lastName),
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_phone),
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_cellPhone),
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_email),
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_street),
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_num),
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_city),
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_applicationPassword),
                                    (CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_block).equals("0")?true:false),
                                    PayWay.valueOf(CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.Customer_PaymentMethod)),
                                    CustomerArray.getJSONObject(i).getString(Sql_Schema_Const_values.Customer_RecommendedBy),
                                    CustomerArray.getJSONObject(i).getInt(Sql_Schema_Const_values.Customer_NumOfRecommends));
                            CustomerResult.arrayList.add(tempCustomer);
                        }
                        CustomerResult.isInLoad = false;
                    }

                }
                catch (Exception ex)
                {
                    CustomerResult.ex = ex;
                }

                //GetRecommendation

//                switch (params[1])
//                {
//                    case "Admin":
//                        break;
//                    case "Books":
//                        for (int i = 0; i < bookArray.length(); i++) {
//                            //(String ID, String bookName, Name writer, String publisher, boolean thickCover, int year, com.backend.enums.Category category, String URL)
//                            Book tempBook = new Book(
//                                    bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_ID),
//                                    bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_BookName),
//                            new Name(bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_Writerf),
//                                    bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_Writerl)),
//                                    bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_Publisher),
//                                   (bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_ThickCover).equals("0")?true:false),
//                                    bookArray.getJSONObject(i).getInt(Sql_Schema_Const_values.BOOK_Year),
//                                    Category.valueOf(bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_ID)),
//                                    bookArray.getJSONObject(i).getString(Sql_Schema_Const_values.BOOK_URL));
//                            booksList.add(tempBook1);
//                        }
//                            break;
//                    case "Carts":
//                        break;
//                    case "Customers":
//                        break;
//                    case "Orders":
//                        break;
//                    case "Recommends":
//                        break;
//                    case "Suppliers":
//                        break;
//                    case "Supplier_Book":
//                        break;
//                }
            }
            else if (params[0].equals("cartid"))
            {
                String result = SqlDS.GET(SqlDS.web_url + "GetCartLastIndex.php");
            }

        }
        catch (Exception ex)
        {

        }

        return null;
    }
}
