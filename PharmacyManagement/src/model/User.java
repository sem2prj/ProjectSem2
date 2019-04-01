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
public class User {
    private StringProperty userName;
    private StringProperty password;
    private StringProperty fullname;
    private StringProperty mission;
    private StringProperty deparment;

    public User() {
    }

    public User(String userName, String password, String fullname,String deparment,String mission) {
        this.userName =new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.fullname = new SimpleStringProperty(fullname);
        this.deparment = new SimpleStringProperty(deparment);
        this.mission = new SimpleStringProperty(mission);
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName = new SimpleStringProperty(userName);
    }

    public String getDeparment() {
        return deparment.get();
    }

    public void setDeparment(String deparment) {
        this.deparment = new SimpleStringProperty(deparment);
    }
    
    

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password =new SimpleStringProperty(password);
    }

    public String getFullname() {
        return fullname.get();
    }

    public void setFullname(String fullname) {
        this.fullname =new SimpleStringProperty(fullname);
    }

    public String getMission() {
        return mission.get();
    }

    public void setMission(String mission) {
        this.mission = new SimpleStringProperty(mission);
    }
    
    
    
   
}
