/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PC
 */
public class Customer {

    private StringProperty CustomerCode;
    private StringProperty Name;
    private StringProperty Addrees;
    private StringProperty Phone;
    private StringProperty Email;
    private IntegerProperty Level;

    public Customer() {
    }

    public Customer(String CustomerCode, String Name, String Addrees, String Phone, String Email, Integer Level) {
        this.CustomerCode = new SimpleStringProperty(CustomerCode);
        this.Name = new SimpleStringProperty(Name);
        this.Addrees = new SimpleStringProperty(Addrees);
        this.Phone = new SimpleStringProperty(Phone);
        this.Email = new SimpleStringProperty(Email);
        this.Level = new SimpleIntegerProperty(Level);
    }

    public String getCustomerCode() {
        return CustomerCode.get();
    }

    public void setCustomerCode(String CustomerCode) {
        this.CustomerCode = new SimpleStringProperty(CustomerCode);
    }

    public String getName() {
        return Name.get();
    }

    public void setName(String Name) {
        this.Name = new SimpleStringProperty(Name);
    }

    public String getAddrees() {
        return Addrees.get();
    }

    public void setAddrees(String Addrees) {
        this.Addrees = new SimpleStringProperty(Addrees);
    }

    public String getPhone() {
        return Phone.get();
    }

    public void setPhone(String Phone) {
        this.Phone = new SimpleStringProperty(Phone);
    }

    public String getEmail() {
        return Email.get();
    }

    public void setEmail(String Email) {
        this.Email = new SimpleStringProperty(Email);
    }

    public Integer getLevel() {
        return Level.get();
    }

    public void setLevel(Integer Level) {
        this.Level = new SimpleIntegerProperty(Level);
    }
    
    

}
