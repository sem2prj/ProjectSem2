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
    private StringProperty Code;
    private StringProperty nameSupplier;
    private StringProperty addrees;
    private StringProperty phone;
    private StringProperty taxINumber;
    private StringProperty email;
    private StringProperty website;
    private StringProperty notice;

    public Supplier() {
    }

    public Supplier(String Code,String nameSupplier, String addrees, String phone, String taxINumber, String email, String website, String notice) {
        this.Code= new SimpleStringProperty(Code);
        this.nameSupplier =new SimpleStringProperty(nameSupplier);
        this.addrees = new SimpleStringProperty(addrees);
        this.phone = new SimpleStringProperty(phone);
        this.taxINumber = new SimpleStringProperty(taxINumber);
        this.email = new SimpleStringProperty(email);
        this.website = new SimpleStringProperty(website);
        this.notice = new SimpleStringProperty(notice);
        
    }

    public String getCode() {
        return Code.get();
    }

    public void setCode(String Code) {
        this.Code =new SimpleStringProperty(Code);
    }

    public String getNameSupplier() {
        return nameSupplier.get();
    }

    public void setNameSupplier(String nameSupplier) {
        this.nameSupplier = new SimpleStringProperty(nameSupplier);
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
