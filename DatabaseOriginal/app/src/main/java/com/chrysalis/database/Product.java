package com.chrysalis.database;

/**
 * Created by ~chrysalis~ on 11/15/2015.
 */
public class Product {

    private int id;
    private String productname;
    private int quantity;

    public Product() {

    }

    public Product (int ID, String productName, int qty) {
        this.id = ID;
        this.productname = productName;
        this.quantity = qty;
    }

    public Product (String productName, int qty) {
        this.productname = productName;
        this.quantity = qty;
    }

    public void setId (int prodID) {
        this.id = prodID;
    }

    public int getId () {
        return this.id;
    }

    public void setProductName (String productName) {
        this.productname = productName;
    }

    public String getProductName () {
        return this.productname;
    }

    public void setQuantity (int qty) {
        this.quantity = qty;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
