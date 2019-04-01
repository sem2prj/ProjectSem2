/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Blob;
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

    private StringProperty Code1;
    private StringProperty DCode;
    private StringProperty Name;
    private StringProperty Categories;
    private StringProperty Unit;
    private Blob Image;
    private StringProperty Status;
    private DoubleProperty BuyPrice;
    private DoubleProperty SellPrice;
    private StringProperty Supplier;
    private StringProperty description;
    private IntegerProperty Id1;

    public Drug() {
    }

    public Drug(String DCode, String Name, String Categories, String Unit, Blob Image, String Status, Double BuyPrice, Double SellPrice,String Supplier, String description, Integer Id1) {
        this.DCode = new SimpleStringProperty(DCode);
        this.Name = new SimpleStringProperty(Name);
        this.Categories = new SimpleStringProperty(Categories);
        this.Unit = new SimpleStringProperty(Unit);
        this.Image = Image;
        this.Status = new SimpleStringProperty(Status);
        this.BuyPrice = new SimpleDoubleProperty(BuyPrice);
        this.SellPrice = new SimpleDoubleProperty(SellPrice);
        this.Supplier = new SimpleStringProperty(Supplier);
        this.description = new SimpleStringProperty(description);
        this.Id1 = new SimpleIntegerProperty(Id1);
    }
    
    public Drug(String Code1,String DCode, String Name, String Categories, String Unit, Blob Image, String Status, Double BuyPrice, Double SellPrice,String Supplier, String description, Integer Id1) {
        this.Code1 = new SimpleStringProperty(Code1);
        this.DCode = new SimpleStringProperty(DCode);
        this.Name = new SimpleStringProperty(Name);
        this.Categories = new SimpleStringProperty(Categories);
        this.Unit = new SimpleStringProperty(Unit);
        this.Image = Image;
        this.Status = new SimpleStringProperty(Status);
        this.BuyPrice = new SimpleDoubleProperty(BuyPrice);
        this.SellPrice = new SimpleDoubleProperty(SellPrice);
        this.Supplier = new SimpleStringProperty(Supplier);
        this.description = new SimpleStringProperty(description);
        this.Id1 = new SimpleIntegerProperty(Id1);
    }
    
    public String getCode1() {
        return Code1.get();
    }

    public void setCode1(String Code1) {
        this.Code1 = new SimpleStringProperty(Code1);
    }

    
    public String getDCode() {
        return DCode.get();
    }

    public void setDCode(String DCode) {
        this.DCode = new SimpleStringProperty(DCode);
    }

    public String getName() {
        return Name.get();
    }

    public void setName(String Name) {
        this.Name = new SimpleStringProperty(Name);
    }

    public String getCategories() {
        return Categories.get();
    }

    public void setCategories(String Categories) {
        this.Categories = new SimpleStringProperty(Categories);
    }

    public String getUnit() {
        return Unit.get();
    }

    public void setUnit(String Unit) {
        this.Unit = new SimpleStringProperty(Unit);
    }

    public Blob getImage() {
        return Image;
    }

    public void setImage(Blob Image) {
        this.Image = Image;
    }

    public String getStatus() {
        return Status.get();
    }

    public void setStatus(String Status) {
        this.Status = new SimpleStringProperty(Status);
    }

    public Double getBuyPrice() {
        return BuyPrice.get();
    }

    public void setBuyPrice(Double BuyPrice) {
        this.BuyPrice = new SimpleDoubleProperty(BuyPrice);
    }

    public Double getSellPrice() {
        return SellPrice.get();
    }

    public void setSellPrice(Double SellPrice) {
        this.SellPrice = new SimpleDoubleProperty(SellPrice);
    }

    public String getSupplier() {
        return Supplier.get();
    }

    public void setSupplier(String Supplier) {
        this.Supplier = new SimpleStringProperty(Supplier);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDesciption(String description) {
        this.description = new SimpleStringProperty(description);
    }
    
        public Integer getId1() {
        return Id1.get();
    }

    public void setId1(Integer Id1) {
        this.Id1 = new SimpleIntegerProperty(Id1);
    }

}
