/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PC
 */
public class Drug {
    private StringProperty DCode;
    private StringProperty nameDrug;
    private StringProperty type;
    private StringProperty UNit;
    private DoubleProperty priceIn;
    private DoubleProperty saleprice;
    private StringProperty supplier;
    private IntegerProperty amount;
    private StringProperty desciption;

    public Drug() {
    }

    public Drug(String dCode, String nameDrug, String type, String uNit, Double priceIn, Double saleprice, String supplier, Integer amount, String desciption) {
        this.DCode =new SimpleStringProperty(dCode);
        this.nameDrug = new SimpleStringProperty(nameDrug);
        this.type = new SimpleStringProperty(type);
        this.UNit = new SimpleStringProperty(uNit);
        this.priceIn = new SimpleDoubleProperty(priceIn);
        this.saleprice = new SimpleDoubleProperty(saleprice);
        this.supplier = new SimpleStringProperty(supplier);
        this.amount = new SimpleIntegerProperty(amount);
        this.desciption =  new SimpleStringProperty(desciption);
    }

    public String getDCode() {
        return DCode.get();
    }

    public void setDCode(String dCode) {
        this.DCode = new SimpleStringProperty(dCode);
    }

    public String getNameDrug() {
        return nameDrug.get();
    }

    public void setNameDrug(String nameDrug) {
        this.nameDrug = new SimpleStringProperty(nameDrug);
    }

    public String getUNit() {
        return UNit.get();
    }

    public void setUNit(String uNit) {
        this.UNit =new SimpleStringProperty(uNit);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }

    public Double getPriceIn() {
        return priceIn.get();
    }

    public void setPriceIn(Double priceIn) {
        this.priceIn = new SimpleDoubleProperty(priceIn);
    }

    public Double getSaleprice() {
        return saleprice.get();
    }

    public void setSaleprice(Double saleprice) {
        this.saleprice =new SimpleDoubleProperty(saleprice);
    }

    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String supplier) {
        this.supplier = new SimpleStringProperty(supplier);
    }

    public Integer getAmount() {
        return amount.get();
    }

    public void setAmount(Integer amount) {
        this.amount = new SimpleIntegerProperty(amount);
    }

    public String getDesciption() {
        return desciption.get();
    }

    public void setDesciption(String desciption) {
        this.desciption = new SimpleStringProperty(desciption);
    }
    
}
