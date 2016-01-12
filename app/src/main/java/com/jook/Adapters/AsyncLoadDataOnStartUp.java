package com.jook.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.backend.entities.Admin;
import com.backend.entities.Book;
import com.backend.entities.Cart;
import com.backend.entities.Customer;
import com.backend.entities.Name;
import com.backend.entities.Recommendation;
import com.backend.entities.Supplier;
import com.backend.entities.Supplier_Book;
import com.backend.enums.Category;
import com.backend.enums.Level;
import com.backend.enums.PayWay;
import com.backend.enums.Ship;
import com.backend.model.datasource.SqlDS;
import com.backend.model.datasource.Sql_Schema_Const_values;
import com.jook.Login_Screen;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ליאל on 12/01/2016.
 */
public class AsyncLoadDataOnStartUp extends AsyncTask<Activity,Void,Void> {
    private SqlDS mySqlDS;
    private Activity LoginScreenAct;

    private Exception main_Exeption;

    @Override
    protected Void doInBackground(Activity... params)
    {
        LoginScreenAct = params[0];
        try {
            mySqlDS = new SqlDS();

            //GetAdmin
            SqlDS.DataLoadFromWeb<Admin> Admin_Result = mySqlDS.dataList.get(0);
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
                Admin_Result.isInLoad = false;
            }

            // GetBooks
            SqlDS.DataLoadFromWeb<Book> Book_Result = mySqlDS.dataList.get(1);
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

            //GetCarts
            SqlDS.DataLoadFromWeb<Cart> CartResult = mySqlDS.dataList.get(2);
            CartResult.Result = SqlDS.GET(SqlDS.web_url + "sqlQuery.php?table=Carts");
            if (!CartResult.Result.equals("0 results")) {
                JSONArray cartArray = new JSONObject(CartResult.Result).getJSONArray("products");
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


            //GetCustomers
            SqlDS.DataLoadFromWeb<Customer> CustomerResult = mySqlDS.dataList.get(3);
            CustomerResult.Result = SqlDS.GET(SqlDS.web_url + "sqlQuery.php?table=Customers");
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

            //GetRecommendation
            SqlDS.DataLoadFromWeb<Recommendation> RecommendationResult = mySqlDS.dataList.get(4);
            RecommendationResult.Result = SqlDS.GET(SqlDS.web_url + "sqlQuery.php?table=Recommends");
            if (!RecommendationResult.Result.equals("0 results"))
            {
                JSONArray RecommendationArray = new JSONObject(RecommendationResult.Result).getJSONArray("products");
                for (int i = 0; i < RecommendationArray.length(); i++)
                {
                    Recommendation tempRecommendation = new Recommendation(
                            RecommendationArray.getJSONObject(i).getString(Sql_Schema_Const_values.REC_IDRecomends),
                            RecommendationArray.getJSONObject(i).getString(Sql_Schema_Const_values.REC_IDRecomended),
                            RecommendationArray.getJSONObject(i).getInt(Sql_Schema_Const_values.REC_Rate),
                            RecommendationArray.getJSONObject(i).getString(Sql_Schema_Const_values.REC_Content)
                    );
                    RecommendationResult.arrayList.add(tempRecommendation);
                }
                RecommendationResult.isInLoad = false;
            }

            //GetCustomers
            SqlDS.DataLoadFromWeb<Supplier> SupplierResult = mySqlDS.dataList.get(5);
            SupplierResult.Result = SqlDS.GET(SqlDS.web_url + "sqlQuery.php?table=Suppliers");
            if (!SupplierResult.Result.equals("0 results"))
            {
                JSONArray SupplierArray = new JSONObject(SupplierResult.Result).getJSONArray("products");
                for (int i = 0; i < SupplierArray.length(); i++)
                {
                    //String businessName, Ship shippingMethod, int rate
                    Supplier tempSupplier = new Supplier(
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_ID),
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_firstName),
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_lastName),
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_phone),
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_cellPhone),
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_email),
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_street),
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_num),
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_city),
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_applicationPassword),
                            (SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.ADMIN_block).equals("0")?true:false),
                            SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.SUP_BusinessName),
                            Ship.valueOf(SupplierArray.getJSONObject(i).getString(Sql_Schema_Const_values.SUP_ShippingMethod)),
                            SupplierArray.getJSONObject(i).getInt(Sql_Schema_Const_values.SUP_Rate));
                    SupplierResult.arrayList.add(tempSupplier);
                }
                SupplierResult.isInLoad = false;
            }


            //GetSupplier_Book
            SqlDS.DataLoadFromWeb<Supplier_Book> Supplier_BookResult = mySqlDS.dataList.get(6);
            Supplier_BookResult.Result = SqlDS.GET(SqlDS.web_url + "sqlQuery.php?table=Supplier_Book");
            if (!Supplier_BookResult.Result.equals("0 results"))
            {
                JSONArray Supplier_BookArray = new JSONObject(Supplier_BookResult.Result).getJSONArray("products");
                for (int i = 0; i < Supplier_BookArray.length(); i++)
                {
                    //String supplierID, String bookID, int amount, float price
                    Supplier_Book tempSupplier_Book = new Supplier_Book(
                            Supplier_BookArray.getJSONObject(i).getString(Sql_Schema_Const_values.SB_SupplierID),
                            Supplier_BookArray.getJSONObject(i).getString(Sql_Schema_Const_values.SB_BookID),
                            Supplier_BookArray.getJSONObject(i).getInt(Sql_Schema_Const_values.SB_Amount),
                            (float)Supplier_BookArray.getJSONObject(i).getDouble(Sql_Schema_Const_values.SB_Price));
                    Supplier_BookResult.arrayList.add(tempSupplier_Book);
                }
                Supplier_BookResult.isInLoad = false;
            }

            // GetCartID
            SqlDS.DataLoadFromWeb<String> CartIDResult = mySqlDS.dataList.get(7);
            CartIDResult.Result = SqlDS.GET(SqlDS.web_url + "GetCartLastIndex.php");
            CartIDResult.isInLoad = false;

        }
        catch (Exception ex)
        {
            main_Exeption = ex;
            return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (main_Exeption == null)
        {
            mySqlDS.BuildListBase();
            AndroidSuperApp.BL = mySqlDS;
            LoginScreenAct.startActivity(new Intent(LoginScreenAct, Login_Screen.class));
        }
        else
        {
            Toast.makeText(LoginScreenAct.getApplicationContext(), "התרחשה שגיאה בחיבור", Toast.LENGTH_SHORT).show();
            Toast.makeText(LoginScreenAct.getApplicationContext(), main_Exeption.getMessage(), Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LoginScreenAct.finish();

        }

    }

}
