package com.backend.model.backend;

import com.backend.entities.*;

import java.util.ArrayList;


/**
 * Created by Yosi Carmeli on 17/11/2015.
 */
public interface Backend
{

    //add book
    public void AddBook(Book book)throws Exception;
    //add book by supplier
    public void AddBook(Book book, Supplier_Book supBook)throws Exception;
    public void UpdateBook (Book book) throws Exception;
    public void DeleteBook (Book book)throws Exception;

    public User GetUserByMail(String mail);
    public User GetUserByID(String id);

    public void AddCustomer(Customer customer)throws Exception;
    public void UpdateCustomer (Customer customer)throws Exception;
    public void DeleteCustomer (Customer customer)throws Exception;

    public void AddSupplier(Supplier supplier)throws Exception;
    public void UpdateSupplier (Supplier supplier)throws Exception;
    public void DeleteSupplier (Supplier supplier)throws Exception;

    public void addBookToSupplier (Supplier_Book sbook)throws Exception;
    public void removeBookFromSupplier (Supplier_Book sbook)throws Exception;
    public void updateBookOnSupplier (Supplier_Book sbook) throws Exception;

    public void AddAdmin(Admin admin)throws Exception;
    public void UpdateAdmin (Admin admin)throws Exception;
    public void DeleteAdmin (Admin admin)throws Exception;

    public void AddRecommendation(Recommendation recommendation)throws Exception;
    public void UpdateRecommendation (Recommendation recommendation)throws Exception;
    public void DeleteRecommendation (Recommendation recommendation)throws Exception;

    public ArrayList<Recommendation> GetRecommendationByRecommendedID(String string);

    public void AddCart(Cart cart)throws Exception;
    public void UpdateCart (Cart cart)throws Exception;
    //return all the orders in the specific cart
    public ArrayList<Order> GetAllCartOrders(String CartID);

    public void AddOrder(Order order)throws Exception;
    public void UpdateOrder (Order order)throws Exception;
    public void DeleteOrder (Order order)throws Exception;

    public ArrayList<Book> GetAllBooks()throws Exception;
    //get books by parameters
    public ArrayList<Book> GetBooksByParameters(String ... List)throws Exception;
    //return the supplier who have the book
    public ArrayList<Supplier_Book> GetSupplierByBook(String BookID)throws Exception;

    public Supplier GetSupplierByID(String supplierID)throws Exception;

    public String GenerateRunCartID();
    public void CartIDNext();

    public String GenerateRunOrderID();
    public void OrderIDNext();
    //calculate the discount to the specific cart
    public Cart DiscountPolicy(Cart cart) throws Exception;

}
