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
import com.backend.enums.Category;
import com.backend.model.backend.Backend;
import com.R;

import java.io.Serializable;
import java.util.ArrayList;

import static com.AndroidSuperApp.getContex;

/**
 * Created by User 1 on 18/11/2015.
 */
public class ListDS implements Backend, Serializable
{

    private static final long serialVersionUid = 1L;
    ArrayList<Book> BookList;
    ArrayList<Customer> CustomerList;
    ArrayList<Recommendation> RecommendationList;
    ArrayList<Supplier> SupplierList;
    ArrayList<Supplier_Book> SupplierBookList;
    ArrayList<Admin> AdminList;
    ArrayList<Cart> CartList;
    ArrayList<Order> OrderList;

    String CartRunID;
    String OrderRunID;



    public ListDS()
    {
        BookList = new ArrayList<Book>();
        CustomerList = new ArrayList<Customer>();
        RecommendationList = new ArrayList<Recommendation>();
        SupplierList = new ArrayList<Supplier>();
        SupplierBookList = new ArrayList<Supplier_Book>();
        AdminList = new ArrayList<Admin>();
        CartList = new ArrayList<Cart>();
        OrderList = new ArrayList<Order>();
        //id's for the serial number of cart/order
        CartRunID = this.GenerateRunCartID();
        OrderRunID = this.GenerateRunOrderID();
    }


    @Override
    public User GetUserByMail(String mail)
    {
        for (Customer c: CustomerList)
        {
            if (c.getContactInfo().getEmail().toLowerCase().equals(mail.toLowerCase()))
                return c;
        }

        for (Supplier s: SupplierList)
        {
            if (s.getContactInfo().getEmail().toLowerCase().equals(mail.toLowerCase()))
                return s;
        }

        for (Admin a: AdminList)
        {
            if (a.getContactInfo().getEmail().toLowerCase().equals(mail.toLowerCase()))
                return a;
        }

        return null;
    }

    @Override
    public User GetUserByID(String id) {
        for (Customer c: CustomerList)
        {
            if (c.getID().toLowerCase().equals(id.toLowerCase()))
                return c;
        }

        for (Supplier s: SupplierList)
        {
            if (s.getID().toLowerCase().equals(id.toLowerCase()))
                return s;
        }
        return null;
    }

    @Override
    public void AddBook(Book book) throws Exception
    {

        if (SearchBookByID(book.getID()) == null)
            BookList.add(book);
        else
            throw new Exception(getContex().getResources().getString(R.string.BookDupIDErr));
    }

    @Override
    public void AddBook(Book book, Supplier_Book supBook) throws Exception {
        if (GetEditableSupplierByID(supBook.getSupplierID())!=null)//check if the supplier exist
        {
            this.AddBook(book);
            this.AddSupplier_Book(supBook);
        }
        else
            throw  new Exception(getContex().getString(R.string.BookNoSupErr));
    }

    @Override
    public void UpdateBook(Book book) throws Exception {

        Book MyBook = SearchBookByID(book.getID());


        if (MyBook==null)
            throw new Exception(getContex().getResources().getString(R.string.BookNotFoundErr));
        else
        {
            this.DeleteBook(MyBook);
            this.AddBook(book);
        }


    }

    @Override
    public void DeleteBook(Book book) throws Exception
    {
        Book MyBook = this.SearchBookByID(book.getID());
        if (MyBook==null)
            throw new Exception(getContex().getResources().getString(R.string.BookNotFoundErr));
        else
        {
            BookList.remove(MyBook);
        }

    }

    private Book SearchBookByID(String bookID)
    {
        for (Book b : BookList)
        {
            if(b.getID().equals(bookID))
            {
                return b;
            }
        }

        return null;
    }

    private void AddSupplier_Book(Supplier_Book supplier_Book) throws Exception
    {
        Supplier_Book sp = SearchSupplier_BookById(supplier_Book);

        if (sp == null && SearchBookByID(supplier_Book.getBookID())!=null && GetSupplierByID(supplier_Book.getSupplierID())!=null)
            SupplierBookList.add(supplier_Book);
        else
            throw new  Exception(getContex().getResources().getString(R.string.SupBookDupRecordErr));

    }

    private void UpdateSupplier_Book(Supplier_Book supplier_Book) throws Exception
    {
        this.DeleteSupplier_Book(SearchSupplier_BookById(supplier_Book));
        this.AddSupplier_Book(supplier_Book);

    }

    private void DeleteSupplier_Book(Supplier_Book supplier_Book) throws Exception
    {
        Supplier_Book sp = SearchSupplier_BookById(supplier_Book);
        if (sp==null)
            throw new Exception(getContex().getResources().getString(R.string.SupBookNotFound));
        else
            SupplierBookList.remove(sp);
    }

    private Supplier_Book SearchSupplier_BookById(Supplier_Book supBook)//get sp by the suplier id and book id
    {
        for (Supplier_Book sp : SupplierBookList)
        {
            if (sp.getBookID().equals(supBook.getBookID()) && sp.getSupplierID().equals(supBook.getSupplierID()))
            {
                return sp;
            }
        }

        return null;
    }


    @Override
    public void AddCustomer(Customer customer) throws Exception{
        if (GetCustomerByID(customer.getID())==null)
             CustomerList.add(customer);
        else
            throw new Exception(getContex().getResources().getString(R.string.CusAlreadyExistErr));

    }

    @Override
    public void UpdateCustomer(Customer customer) throws Exception
    {
        boolean update=false;
        Customer c =GetCustomerByID(customer.getID());
        if (c!=null) {
            DeleteCustomer(c);
            AddCustomer(customer);
        }
        else
            throw new Exception(getContex().getResources().getString(R.string.CusNotExistErr));

    }

    @Override
    public void DeleteCustomer(Customer customer) throws Exception
    {
        Customer c=GetCustomerByID(customer.getID());
        if (c==null)
            throw new Exception(getContex().getResources().getString(R.string.CusNotExistErr));
        else
            CustomerList.remove(c);
    }

    private Customer GetCustomerByID(String cid)
    {
        for (Customer c : CustomerList)
        {
            if (c.getID().equals(cid))
                return c;
        }
        return null;
    }


    @Override
    public void AddSupplier(Supplier supplier) throws Exception
    {
        Supplier s = this.GetEditableSupplierByID(supplier.getID());

        if (s == null)
            SupplierList.add(supplier);
        else
            throw new Exception(getContex().getResources().getString(R.string.SupplierAlreadyExistErr));
    }

    @Override
    public void UpdateSupplier(Supplier supplier) throws Exception
    {
        this.DeleteSupplier(supplier);
        this.AddSupplier(supplier);

    }

    @Override
    public void DeleteSupplier(Supplier supplier) throws Exception
    {
        Supplier s = this.GetEditableSupplierByID(supplier.getID());

        if (s != null)
            SupplierList.remove(s);
        else
            throw new Exception(getContex().getResources().getString(R.string.SupNotFoundErr));

    }
    //function on suplier-book with book parametter
    @Override
    public void addBookToSupplier(Supplier_Book sbook) throws Exception
    {
        this.AddSupplier_Book(sbook);
    }

    @Override
    public void removeBookFromSupplier(Supplier_Book sbook) throws Exception
    {
        this.DeleteSupplier_Book(sbook);
    }

    @Override
    public void updateBookOnSupplier(Supplier_Book sbook) throws Exception
    {
        this.UpdateSupplier_Book(sbook);
    }

    @Override
    public void AddAdmin(Admin admin) throws Exception
    {
        Admin a = FindAdminByID(admin.getID());

        if (a == null)
            AdminList.add(admin);
        else
            throw new Exception(getContex().getResources().getString(R.string.AdminAlreadyExistErr));

    }

    @Override
    public void UpdateAdmin(Admin admin) throws Exception
    {
        this.DeleteAdmin(admin);
        this.AddAdmin(admin);

    }

    @Override
    public void DeleteAdmin(Admin admin) throws Exception
    {
        Admin a = FindAdminByID(admin.getID());

        if (a != null)
            AdminList.remove(a);
        else
            throw new Exception(getContex().getResources().getString(R.string.AdminNotexistErr));


    }

    private Admin FindAdminByID (String AdminID) throws Exception
    {
        for (Admin item : AdminList)
        {
            if (item.getID().equals(AdminID))
                return item;
        }

        return null;
    }

    @Override
    public void AddRecommendation(Recommendation recommendation) throws Exception
    {//user can recomend only once
        Recommendation r = GetRecommendationByIDs(recommendation.getIDRecomended(), recommendation.getIDRecomends());

        boolean RecomendedFound = false, RecomendsFound = false;

        if (r == null)
        {
            Book b = this.GetBooksByParameters("id",recommendation.getIDRecomended()).get(0);
            if (b != null)
                RecomendedFound = true;
            else
            {
                Supplier s = this.GetSupplierByID(recommendation.getIDRecomended());
                if (s != null)
                    RecomendedFound = true;
            }
            Supplier s = this.GetSupplierByID(recommendation.getIDRecomends());
            if (s != null)
                RecomendsFound = true;
            else
            {
                Customer c = this.GetCustomerByID(recommendation.getIDRecomends());
                if (c != null)
                    RecomendsFound = true;
            }

            if (RecomendedFound && RecomendsFound )
                RecommendationList.add(recommendation);
            else
                throw new Exception(getContex().getResources().getString(R.string.RecoomnededNotFound));
        }
        else
            throw new Exception(getContex().getResources().getString(R.string.RecAlreadyExistErr));

    }

    @Override
    public void UpdateRecommendation(Recommendation recommendation) throws Exception
    {
        this.DeleteRecommendation(recommendation);
        this.AddRecommendation(recommendation);

    }

    @Override
    public void DeleteRecommendation(Recommendation recommendation) throws Exception
    {
        Recommendation r = GetRecommendationByIDs(recommendation.getIDRecomended(), recommendation.getIDRecomends());

        if (r != null)
            RecommendationList.remove(r);
        else
            throw new Exception(getContex().getResources().getString(R.string.RecNotExistErr));

    }

    private Recommendation GetRecommendationByIDs(String person, String object)
    {
        for (Recommendation r : RecommendationList)
        {
            if (r.getIDRecomended().equals(person) && r.getIDRecomends().equals(object))
                return  r;
        }

        return null;
    }

    @Override
    public ArrayList<Recommendation> GetRecommendationByRecommendedID(String string)
    {
        ArrayList<Recommendation> myList = new ArrayList<Recommendation>();
        for (Recommendation r : RecommendationList)
        {
            if (r.getIDRecomended().equals(string))
                myList.add(r);
        }


        return myList;

    }

    @Override
    public void AddCart(Cart cart) throws Exception
    {
        Cart c = GetCartByID(cart.getID());
        if ( c != null)
            throw new Exception(getContex().getResources().getString(R.string.CartAlreadyExistErr));

        cart.setID(CartRunID);
        CartList.add(cart);
        CartIDNext();
    }

    @Override
    public void UpdateCart(Cart cart) throws Exception
    {
        Cart c = GetCartByID(cart.getID());

        if (c != null)
        {
            CartList.remove(c);
            CartList.add(DiscountPolicy(cart));
        }
        else
            throw new Exception(getContex().getResources().getString(R.string.CartNotExistErr));
        this.DeleteCart(cart);
    }

    private void DeleteCart(Cart cart) throws Exception
    {
        Cart c = GetCartByID(cart.getID());

        if (c != null) {
            // Delete all cart order
            for (Order o : OrderList) {
                if (o.getCartId().equals(c.getID())) {
                    this.DeleteOrder(o);
                }
            }
            CartList.remove(c);
        }
        else
            throw new Exception(getContex().getResources().getString(R.string.CartNotExistErr));

    }

    private Cart GetCartByID(String CartID)
    {
        for (Cart c : CartList)
        {
            if (c.getID().equals(CartID))
            {
                return c;
            }
        }

        return null;
    }

    @Override
    public ArrayList<Order> GetAllCartOrders(String CartID)
    {
        ArrayList<Order> Result = new ArrayList<Order>();

        for (Order or : OrderList)
        {
            if (or.getCartId().equals(CartID))
                Result.add(or);
        }

        return new ArrayList<Order>(Result);
    }

    @Override
    public void AddOrder(Order order) throws Exception
    {
        Cart c = GetCartByID(order.getCartId());
        if ( c == null)
            throw new Exception(getContex().getResources().getString(R.string.CartNotExistErr));

        Order o = GetOrderByID(order.getId());
        if (o != null)
            throw new Exception(getContex().getResources().getString(R.string.OrderAlreadyExistErr));

        // Add order
        order.setId(this.OrderRunID);
        this.OrderIDNext();
        OrderList.add(order);

        // Update Cart
        c.setNumOfOrders(c.getNumOfOrders()+1);
        Supplier_Book sb  = SearchSupplier_BookById(new Supplier_Book(order.getSupplierId(),order.getBookId()));
        c.setOriginalPrice(c.getOriginalPrice()+(order.getAmount()* sb.getPrice()));
        this.UpdateCart(c);

    }

    @Override
    public void UpdateOrder(Order order) throws Exception
    {
        Order o = GetOrderByID(order.getId());
        if (o == null)
            throw new Exception(getContex().getResources().getString(R.string.OrderNotExistErr));
        else
        {
            // Update Cart
            Cart c = GetCartByID(order.getCartId());
            Supplier_Book sb  = SearchSupplier_BookById(new Supplier_Book(o.getSupplierId(),o.getBookId()));
            c.setOriginalPrice(c.getOriginalPrice()-(o.getAmount()* sb.getPrice())+(order.getAmount()* sb.getPrice()));
            this.UpdateCart(c);

            // Update order
            OrderList.remove(o);
            OrderList.add(order);
        }



    }

    @Override
    public void DeleteOrder(Order order) throws Exception
    {
        Order o = GetOrderByID(order.getId());
        if (o == null)
            throw new Exception(getContex().getResources().getString(R.string.OrderNotExistErr));
        else
        {
            // Update Cart
            Cart c = GetCartByID(order.getCartId());
            c.setNumOfOrders(c.getNumOfOrders()-1);
            Supplier_Book sb  = SearchSupplier_BookById(new Supplier_Book(o.getSupplierId(),o.getBookId()));
            c.setOriginalPrice(c.getOriginalPrice()-(o.getAmount()* sb.getPrice()));
            this.UpdateCart(c);

            // Remove Order
            OrderList.remove(o);
        }

    }

    private Order GetOrderByID(String OrderID)
    {
        for (Order o : OrderList)
        {
            if (o.getId().equals(OrderID))
                return  o;
        }

        return null;
    }

    @Override
    public ArrayList<Book> GetAllBooks()
    {
        return new ArrayList<Book>(BookList);
    }

    @Override
    public ArrayList<Book> GetBooksByParameters(String... List) throws Exception
    {

        ArrayList<Book> Source = GetAllBooks();
        ArrayList<Book> Result = GetAllBooks();


        for (int i = 0; i < List.length; i = i+2)
        {
            String Parameter = List[i];
            String Value = List[i+1];
            switch (Parameter)
            {
                case "Id":
                case "ID":
                case "id":
                    for (Book b : BookList)
                    {
                        if (b.getID().equals(List[i+1]))
                        {
                            ArrayList<Book> tResult1 = new ArrayList<Book>();
                            tResult1.add(b);
                            return tResult1;
                        }
                    }

                    break;
                case "BOOKNAME":
                case "bookname":
                case "bookName":
                case "BookName":

                    for (Book b : Source)
                    {
                        if (!b.getBookName().equals(Value))
                        {
                            Result.remove(b);
                        }
                    }

                    break;

                case "Writer":
                case "writer":
                case "WRITER":

                    for (Book b : Source)
                    {
                        if (!b.getWriter().GetFullName().equals(Value))
                        {
                            Result.remove(b);
                        }
                    }

                    break;

                case "Publisher":
                case "PUBLISHER":
                case "publisher":

                    for (Book b : Source)
                    {
                        if (!b.getPublisher().equals(Value))
                        {
                            Result.remove(b);
                        }
                    }

                    break;

                case "ThickCover":
                case "thickcover":
                case "THICKCOVER":
                case "thickCover":

                    boolean bool = this.ConvertStringToBool(Value);

                    for (Book b : Source)
                    {


                        if (bool)
                        {
                            if (!b.isThickCover())
                                Result.remove(b);
                        }
                        else
                        {
                            if (b.isThickCover())
                                Result.remove(b);
                        }
                    }

                    break;

                case "Year":
                case "year":
                case "YEAR":

                    for (Book b : Source)
                    {
                        if (!Integer.toString(b.getYear()).equals(Value))
                        {
                            Result.remove(b);
                        }
                    }


                    break;

                case "Category":
                case "category":
                case "CATEGORY":

                    for (Book b : Source)
                    {
                        if (!b.getCategory().equals(Category.valueOf(Value)))
                        {
                            Result.remove(b);
                        }
                    }

                    break;

                default:
                    throw new Exception("Parameter name not valid!!");

            }

            return Result;

        }
        return null;
    }

    private boolean ConvertStringToBool(String s) throws Exception
    {
        if (s.equals("True") || s.equals("true") || s.equals("TRUE"))
            return true;

        if (s.equals("False") || s.equals("false")  || s.equals("FALSE"))
            return false;

        throw new Exception("Boolen Parameter not valid!!");
    }




    @Override
    public ArrayList<Supplier_Book> GetSupplierByBook(String BookID)//get the supplier that have the book
    {
        ArrayList<Supplier_Book> Result = new ArrayList<Supplier_Book>();

        for (Supplier_Book sp : SupplierBookList)
        {
            if (sp.getBookID().equals(BookID))
                Result.add(sp);
        }

        return new ArrayList<Supplier_Book>(Result);
    }

    private ArrayList<Supplier_Book> GetEditableSupplierByBook(String BookID)
    {
        ArrayList<Supplier_Book> Result = new ArrayList<Supplier_Book>();

        for (Supplier_Book sp : SupplierBookList)
        {
            if (sp.getBookID().equals(BookID))
                Result.add(sp);
        }

        return Result;
    }

    @Override
    public Supplier GetSupplierByID(String supplierID) throws Exception
    {
        for (Supplier s : SupplierList)
        {
            if (s.getID().equals(supplierID))
            {
                return new Supplier(s);
            }
        }

        return null;
    }

    private Supplier GetEditableSupplierByID(String supplierID)
    {
        for (Supplier s : SupplierList)
        {
            if (s.getID().equals(supplierID))
            {
                return s;
            }
        }

        return null;
    }

    @Override
    public String GenerateRunCartID()
    {
        //List restart in every open not need to find last index;
        return getContex().getResources().getString(R.string.CartIdStartRunIfForList);
    }

    @Override
    public void CartIDNext()
    {
        int SN = GetNumPartOfID(CartRunID);
        CartRunID = CartRunID.substring(0,5) + Integer.toString(++SN);
    }

    @Override
    public String GenerateRunOrderID()
    {
        //List restart in every open not need to find last index;
        return getContex().getResources().getString(R.string.OrderIdStartRunIfForList);
    }

    @Override
    public void OrderIDNext()
    {
        int SN = GetNumPartOfID(OrderRunID);
        OrderRunID = OrderRunID.substring(0,5) + Integer.toString(++SN);

    }

    private int GetNumPartOfID(String ID)
    {
        return Integer.parseInt(ID.substring(5));
    }

    @Override
    public Cart DiscountPolicy(Cart cart) throws Exception
    {//the discount policy of the app
        int TotalPrice = 0;
        int TotalOrders = 0;

        for (Order or : GetAllCartOrders(cart.getID()))
        {
            TotalOrders++;
            Supplier_Book sb  = SearchSupplier_BookById(new Supplier_Book(or.getSupplierId(),or.getBookId()));
            TotalPrice += (or.getAmount()* sb.getPrice());
        }

        if (TotalOrders >= 2)
        {
            if (TotalPrice >= 500)
            {
                cart.setDiscountPrecent(10);
                if (TotalPrice >= 1000)
                {
                    cart.setDiscountPrecent(25);
                    if (TotalPrice >= 2000)
                        cart.setDiscountPrecent(35);
                }
            }
        }

        cart.setTotalPrice(TotalPrice* ((100-cart.getDiscountPrecent())/100));


        return cart;
    }
}
