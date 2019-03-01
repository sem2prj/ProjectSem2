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
public class Member {
    private StringProperty userName;
    private StringProperty password;
    private StringProperty role;

    public Member() {
    }
// tao phuong thuc set va get theo property

    public String getuserName() {
        return userName.get();
    }

    public void setuserName(String userName) {
        this.userName = new SimpleStringProperty(userName);
    }

    public String getpassword() {
        return password.get();
    }

    public void setpassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    public String getrole() {
        return role.get();
    }

    public void setrole(String role) {
        this.role = new SimpleStringProperty(role);
    }

    public Member(String userName, String password, String role) {
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);

    }
}
