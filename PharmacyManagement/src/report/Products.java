/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author admin
 */
public class Products {
    private StringProperty product;
    private StringProperty price;
    private StringProperty pqty;
    private StringProperty amount;

    public Products(String product, String price, String pqty, String amount) {
        this.product =new SimpleStringProperty (product);
        this.price = new SimpleStringProperty(price);
        this.pqty = new SimpleStringProperty(pqty);
        this.amount = new SimpleStringProperty(amount);
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product.get();
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = new SimpleStringProperty(product);
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price.get();
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = new SimpleStringProperty(price);
    }

    /**
     * @return the pqty
     */
    public String getPqty() {
        return pqty.get();
    }

    /**
     * @param pqty the pqty to set
     */
    public void setPqty(String pqty) {
        this.pqty = new SimpleStringProperty(pqty);
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount.get();
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = new SimpleStringProperty(amount);
    }

   
    
    
}
