/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
public class OrderList2 {

    private int no;
    private int pid;
    private String barcode;
    private String productName;
    private double priceOut;

    private int qty;
    private double amount;

    public OrderList2(int no, int pid, String barcode, String productName, double priceOut, int qty, double amount) {
        this.no = no;
        this.pid = pid;
        this.barcode = barcode;
        this.productName = productName;
        this.priceOut = priceOut;
        this.qty = qty;
        this.amount = amount;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(double priceOut) {
        this.priceOut = priceOut;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

}
