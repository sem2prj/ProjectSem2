/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

/**
 *
 * @author admin
 */
public class Products {
    private String product;
    private String price;
    private String pqty;
    private String amount;

    public Products(String product, String price, String pqty, String amount) {
        this.product = product;
        this.price = price;
        this.pqty = pqty;
        this.amount = amount;
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the pqty
     */
    public String getPqty() {
        return pqty;
    }

    /**
     * @param pqty the pqty to set
     */
    public void setPqty(String pqty) {
        this.pqty = pqty;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

   
    
    
}
