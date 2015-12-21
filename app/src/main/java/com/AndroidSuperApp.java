package com;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.backend.entities.Book;
import com.backend.entities.Cart;
import com.backend.entities.Name;
import com.backend.entities.Recommendation;
import com.backend.entities.Supplier;
import com.backend.entities.Supplier_Book;
import com.backend.entities.User;
import com.backend.enums.Category;
import com.backend.enums.PayWay;
import com.backend.enums.Ship;
import com.backend.model.datasource.ListDS;

public class AndroidSuperApp extends Application {

    private static AndroidSuperApp singleton;

    public static ListDS BL = null;
    private static Context ctx;
    public static User CurrAppUser;
    public static Cart CurrAppCart;

    public static AndroidSuperApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        ctx = this;
        BL = new ListDS();
        try
        {
            buildDefultDataBase();
            CurrAppCart = null;
            CurrAppUser = null;
        }
        catch (Exception ex)
        {
            Toast.makeText(ctx,ex.getMessage(),Toast.LENGTH_LONG);
        }

    }

    public static boolean onLogin(String mail, String pass)
    {
        try
        {
            CurrAppUser = BL.GetUserByMail(mail);

            if (CurrAppUser != null && CurrAppUser.getApplicationPassword().toLowerCase().equals(pass.toLowerCase()))
            {
                CurrAppCart = new Cart();
                CurrAppCart.setCustomerID(CurrAppUser.getID());
                BL.AddCart(CurrAppCart);
                return true;
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(ctx,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return false;

    }

    public static void onLogOut()
    {
        CurrAppCart = null;
        CurrAppUser = null;
    }

    public static Context getContex() { return ctx;}

    private void buildDefultDataBase() throws Exception {

        Book b1 = new Book("JB1","הענק בגנו",new Name("דוד","לוי"),"ידיעות אחרונות",true,2001, Category.History,"http://api.androidhive.info/music/images/adele.png");
        Book b2 = new Book("JB2","הארי פוטר",new Name("גקי","רולינג"),"מעריב",true,2005, Category.History,"http://api.androidhive.info/music/images/mj.png");
        Book b3 = new Book("JB3","שחר ",new Name("עמרם","אסולין"),"דותן",true,2008, Category.History,"http://api.androidhive.info/music/images/arrehman.png");
        Book b4 = new Book("JB4","אורות",new Name("עמרם","אסולין"),"דותן",true,2008, Category.Holy,"http://api.androidhive.info/music/images/arrehman.png");
        Book b5 = new Book("JB5","דוד החקיין",new Name("צור","שלום"),"דותן",true,2008, Category.Holy,"http://images1.ynet.co.il/PicServer4/2015/11/22/6649137/664911801000100490761no.jpg");
        Book b6 = new Book("JB6","בני האור",new Name("עמרם","אסולין"),"דותן",true,2008, Category.Holy,"http://api.androidhive.info/music/images/alexi_murdoch.png");
        Book b7 = new Book("JB7","גמרא",new Name("דוד","אסולין"),"דותן",true,2008, Category.Children,"https://www.hamadaf-y.co.il/media/760049/mainpicgen.jpg");
        Book b8 = new Book("JB8","פוסקים",new Name("עמרם","שלום"),"דותן",true,2008, Category.Children,"http://api.androidhive.info/music/images/alexi_murdoch.png");
        Book b9 = new Book("JB9","פיתגורס",new Name("עמרם","אסולין"),"דותן",true,2008, Category.Children,"https://www.hamadaf-y.co.il/media/67804/tzedaka.png");
        Book b10 = new Book("JB10","עלות השחר",new Name("עמרם","ליאל"),"דותן",true,2008, Category.Professional,"http://api.androidhive.info/music/images/alexi_murdoch.png");
        Book b11 = new Book("JB11","מבצעים אפלים",new Name("חנה","אסולין"),"דותן",true,2008, Category.Professional,"https://www.hamadaf-y.co.il/media/67804/tzedaka.png");
        Book b12 = new Book("JB12","ספרד",new Name("צור","אסולין"),"דותן",true,2008, Category.Professional,"http://api.androidhive.info/music/images/alexi_murdoch.png");


        AndroidSuperApp.BL.AddBook(b1);
        AndroidSuperApp.BL.AddBook(b2);
        AndroidSuperApp.BL.AddBook(b3);
        AndroidSuperApp.BL.AddBook(b4);
        AndroidSuperApp.BL.AddBook(b5);
        AndroidSuperApp.BL.AddBook(b6);
        AndroidSuperApp.BL.AddBook(b7);
        AndroidSuperApp.BL.AddBook(b8);
        AndroidSuperApp.BL.AddBook(b9);
        AndroidSuperApp.BL.AddBook(b10);
        AndroidSuperApp.BL.AddBook(b11);
        AndroidSuperApp.BL.AddBook(b12);


        Supplier s1 = new Supplier("302724208","ליאל","צור","036959917","0523139051","LIEL71@GMAIL.COM","ברזיל","100","ירושלים","12369091",false,"הראל ספרים", Ship.CourierService, PayWay.Credit,5);
        Supplier s2 = new Supplier("304901408","ידידאל","לוק","026589382","0524789621","Yedid@GMAIL.COM","פסגתזאב","100","ירושלים","56756",false,"הדר ספרים", Ship.CourierService, PayWay.BankTransfer,3);

        AndroidSuperApp.BL.AddSupplier(s1);
        AndroidSuperApp.BL.AddSupplier(s2);

        Supplier_Book sp1 = new Supplier_Book(s1.getID(),b1.getID(),4,(float)82.5);
        Supplier_Book sp2 = new Supplier_Book(s2.getID(),b1.getID(),4,(float)100);

        AndroidSuperApp.BL.addBookToSupplier(sp1);
        AndroidSuperApp.BL.addBookToSupplier(sp2);

        Recommendation r1 = new Recommendation(s1.getID(),b1.getID(),3,"אחלה של ספר");

        AndroidSuperApp.BL.AddRecommendation(r1);

    }

}
