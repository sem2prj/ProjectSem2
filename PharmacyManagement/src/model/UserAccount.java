/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class UserAccount {
    private String user;
    private String password;
    private List<String> roles; 

    public UserAccount() {
    }

    public UserAccount(String user, String password, String... roles) {
        this.user = user;
        this.password = password;
        this.roles = new ArrayList<String>();
        if (roles!=null) {
            for (String r : roles) {
            this.roles.add(r);
         }
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    
}
