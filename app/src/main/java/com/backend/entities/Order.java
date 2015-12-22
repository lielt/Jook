package com.backend.entities;

public class Order
{
    private String Id;
    private String CartId;
    private String SupplierId;
    private String BookId;
    private int Amount;

    public Order(String id, String cartId, String supplierId, String bookId, int amount) {
        Id = id;
        CartId = cartId;
        SupplierId = supplierId;
        BookId = bookId;
        Amount = amount;
    }

    public String getId() {
        return Id;
    }

    public String getCartId() {
        return CartId;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public String getBookId() {
        return BookId;
    }

    public int getAmount() {
        return Amount;
    }


    public void setId(String id) { Id = id;  }

    public void setCartId(String cartId) {
        CartId = cartId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
