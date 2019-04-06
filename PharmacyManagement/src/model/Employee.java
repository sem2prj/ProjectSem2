/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Blob;
import java.sql.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PC
 */
public class Employee {

    private StringProperty EplCode;
    private StringProperty userName;
    private StringProperty phone;
    private StringProperty email;
    private StringProperty addrees;
    private BooleanProperty gender;
    private SimpleObjectProperty<Date> dateBirth;
    private DoubleProperty salary;
    private StringProperty department;
    private Blob imageBlob;
    private SimpleObjectProperty<Date> dateWork;
    
    private StringProperty Pass;
    private StringProperty Role;

    private IntegerProperty id;
    private StringProperty fullname;
    
    
    public Employee() {
    }
    
//    
//    public Employee(String userName, String password, String fullname,String Role) {
//        this.userName =new SimpleStringProperty(userName);
//        this.Pass = new SimpleStringProperty(password);
//        this.fullname = new SimpleStringProperty(fullname);
//        this.Role = new SimpleStringProperty(Role);
//    }
//    
//
//    public Employee(String eplCode, String userName, String phone, String email, String addrees, Boolean gender, Date dateBirth, Double salary, String department, Blob imageBlob, Date dateWork) {
//        this.EplCode = new SimpleStringProperty(eplCode);
//        this.userName = new SimpleStringProperty(userName);
//        this.phone = new SimpleStringProperty(phone);
//        this.email = new SimpleStringProperty(email);
//        this.addrees = new SimpleStringProperty(addrees);
//        this.gender = new SimpleBooleanProperty(gender);
//        this.dateBirth = new SimpleObjectProperty<>(dateBirth);
//        this.salary = new SimpleDoubleProperty(salary);
//        this.department = new SimpleStringProperty(department);
//        this.imageBlob = imageBlob;
//        this.dateWork = new SimpleObjectProperty<>(dateWork);
//    }

     public Employee(String eplCode, String userName,String fullname, String phone, String email, String addrees, Boolean gender, Date dateBirth, Double salary, String department, Blob imageBlob, Date dateWork, String Pass, Integer id,String Role) {
        this.EplCode = new SimpleStringProperty(eplCode);
        this.userName = new SimpleStringProperty(userName);
        this.fullname=new SimpleStringProperty(fullname);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.addrees = new SimpleStringProperty(addrees);
        this.gender = new SimpleBooleanProperty(gender);
        this.dateBirth = new SimpleObjectProperty<>(dateBirth);
        this.salary = new SimpleDoubleProperty(salary);
        this.department = new SimpleStringProperty(department);
        this.imageBlob = imageBlob;
        this.dateWork = new SimpleObjectProperty<>(dateWork);
        this.Pass = new SimpleStringProperty(Pass);
        this.id = new SimpleIntegerProperty(id);
        this.Role=new SimpleStringProperty(Role);
    }
    
    
    public String getRole() {
        return Role.get();
    }

    public void setRole(String Role) {
        this.Role = new SimpleStringProperty(Role);
    }

    public Integer getId() {
        return id.get();
    }

    public String getFullname() {
        return fullname.get();
    }

    public void setFullname(String fullname) {
        this.fullname = new SimpleStringProperty(fullname);
    }

    
    
    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }
    
     public String getPass() {
        return Pass.get();
    }

    public void setPass(String Pass) {
        this.Pass = new SimpleStringProperty(Pass);
    }
    
    
    public Blob getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(Blob imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getEplCode() {
        return EplCode.get();
    }

    public void setEplCode(String barCode) {
        this.EplCode = new SimpleStringProperty(barCode);
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName = new SimpleStringProperty(userName);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone = new SimpleStringProperty(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public String getAddrees() {
        return addrees.get();
    }

    public void setAddrees(String addrees) {
        this.addrees = new SimpleStringProperty(addrees);
    }

    public Boolean getGender() {
        return gender.get();
    }

    public void setGender(Boolean gender) {
        this.gender = new SimpleBooleanProperty(gender);
    }

    public Object getDateBirth() {
        return dateBirth.get();
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = new SimpleObjectProperty<>(dateBirth);
    }

    public Double getSalary() {
        return salary.get();
    }

    public void setSalary(Double salary) {
        this.salary = new SimpleDoubleProperty(salary);
    }
    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department = new SimpleStringProperty(department);
    }

    public Object getDateWork() {
        return dateWork.get();
    }

    public void setDateWork(Date dateWork) {
        this.dateWork = new SimpleObjectProperty<>(dateWork);
    }

}
