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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPqty() {
        return pqty;
    }

    public void setPqty(String pqty) {
        this.pqty = pqty;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    
}
