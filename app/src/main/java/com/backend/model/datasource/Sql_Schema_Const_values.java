package com.backend.model.datasource;

/**
 * Created by ליאל on 04/01/2016.
 */
public final class Sql_Schema_Const_values
{
//    private String ID;
//    private String BookName;
//    private Name Writer;
//    private String Publisher;
//    private boolean ThickCover;
//    private int Year;
//    private com.backend.enums.Category Category;
//    private String URL;


    // Book Table
    public static String BOOK_ID = "id";
    public static String BOOK_BookName = "name";
    public static String BOOK_Writerf = "writer_f_name";
    public static String BOOK_Writerl = "writer_l_name";
    public static String BOOK_Publisher = "publisher";
    public static String BOOK_ThickCover = "thick_cover";
    public static String BOOK_Year = "year";
    public static String BOOK_Category = "category";
    public static String BOOK_URL = "url";


//    private String CustomerID;
//    private float TotalPrice;
//    private int DiscountPrecent;
//    private int NumOfOrders;
//    private float OriginalPrice;

    // Cart Table
    public static String CART_CustomerID = "CustomerID";
    public static String CART_TotalPrice = "TotalPrice";
    public static String CART_DiscountPrecent = "DiscountPrecent";
    public static String CART_NumOfOrders = "NumOfOrders";
    public static String CART_OriginalPrice = "OriginalPrice";



//    String ID,
//    String firstName,
//    String lastName,
//    String phone,
//    String cellPhone,
//    String email,
//    String street,
//    String num,
//    String city,
//    String applicationPassword,
//    boolean block,
//    com.backend.enums.Level level

    // Admin Table
    // UserData
    public static String ADMIN_ID = "id";
    public static String ADMIN_firstName = "firstName";
    public static String ADMIN_lastName = "lastName";
    public static String ADMIN_phone = "phone";
    public static String ADMIN_cellPhone = "cellPhone";
    public static String ADMIN_email = "email";
    public static String ADMIN_street = "street";
    public static String ADMIN_num = "num";
    public static String ADMIN_city = "city";
    public static String ADMIN_applicationPassword = "applicationPassword";
    public static String ADMIN_block = "block";
    // AdminData
    public static String ADMIN_level = "level";


//    private PayWay PaymentMethod;
//    private String RecommendedBy;
//    private int NumOfRecommends;

    // Customer Table
    public static String Customer_PaymentMethod = "PaymentMethod";
    public static String Customer_RecommendedBy = "RecommendedBy";
    public static String Customer_NumOfRecommends = "NumOfRecommends";


//    private String CartId;
//    private String SupplierId;
//    private String BookId;
//    private int Amount;

    // Order Table
    public static String CartId = "CartId";
    public static String SupplierId = "SupplierId";
    public static String BookId = "BookId";
    public static String Amount = "Amount";

//    private String IDRecomends;
//    private String IDRecomended;
//    private int Rate;
//    private String Content;

    public static String REC_IDRecomends = "IDRecomends";
    public static String REC_IDRecomended = "IDRecomended";
    public static String REC_Rate = "Rate";
    public static String REC_Content = "Content";

//    private String BusinessName;
//    private Ship ShippingMethod;
//    private int Rate;

    public static String SUP_BusinessName = "BusinessName";
    public static String SUP_ShippingMethod = "ShippingMethod";
    public static String SUP_Rate = "Rate";


//    private String SupplierID;
//    private String BookID;
//    private int Amount;
//    private float Price;

    public static String SB_SupplierID = "SupplierID";
    public static String SB_BookID = "BookID";
    public static String SB_Amount = "Amount";
    public static String SB_Price = "Price";









}
