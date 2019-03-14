/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PC
 */
public class Supplier {

    private StringProperty nameSupplier;
    private StringProperty type;
    private StringProperty addrees;
    private StringProperty phone;
    private StringProperty taxINumber;
    private StringProperty email;
    private StringProperty website;
    private StringProperty notice;

    public Supplier() {
    }

    public Supplier(String nameSupplier, String type, String addrees, String phone, String taxINumber, String email, String website, String notice) {
        this.nameSupplier =new SimpleStringProperty(nameSupplier);
        this.type = new SimpleStringProperty(type);
        this.addrees = new SimpleStringProperty(addrees);
        this.phone = new SimpleStringProperty(phone);
        this.taxINumber = new SimpleStringProperty(taxINumber);
        this.email = new SimpleStringProperty(email);
        this.website = new SimpleStringProperty(website);
        this.notice = new SimpleStringProperty(notice);
    }

    public String getNameSupplier() {
        return nameSupplier.get();
    }

    public void setNameSupplier(String nameSupplier) {
        this.nameSupplier = new SimpleStringProperty(nameSupplier);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }

    public String getAddrees() {
        return addrees.get();
    }

    public void setAddrees(String addrees) {
        this.addrees = new SimpleStringProperty(addrees);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone = new SimpleStringProperty(phone);
    }

    public String getTaxINumber() {
        return taxINumber.get();
    }

    public void setTaxINumber(String taxINumber) {
        this.taxINumber = new SimpleStringProperty(taxINumber);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public String getWebsite() {
        return website.get();
    }

    public void setWebsite(String website) {
        this.website = new SimpleStringProperty(website);
    }

    public String getNotice() {
        return notice.get();
    }

    public void setNotice(String notice) {
        this.notice = new SimpleStringProperty(notice);
    }
    
    
    
}
