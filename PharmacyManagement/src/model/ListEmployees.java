/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author PC
 */
public class ListEmployees {
    
    private String Code;
    private String Name;
    private String Addrees;
    private String Phone;
    private String Roles;
    private String Date_created;

    public ListEmployees(String Code, String Name, String Addrees, String Phone, String Roles, String Date_created) {
        this.Code = Code;
        this.Name = Name;
        this.Addrees = Addrees;
        this.Phone = Phone;
        this.Roles = Roles;
        this.Date_created = Date_created;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddrees() {
        return Addrees;
    }

    public void setAddrees(String Addrees) {
        this.Addrees = Addrees;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getRoles() {
        return Roles;
    }

    public void setRoles(String Roles) {
        this.Roles = Roles;
    }

    public String getDate_created() {
        return Date_created;
    }

    public void setDate_created(String Date_created) {
        this.Date_created = Date_created;
    }

    


    
    
    
    
}
