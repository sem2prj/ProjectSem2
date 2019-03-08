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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
    private StringProperty position;
    private StringProperty department;
    private Blob imageBlob;
    private SimpleObjectProperty<Date> dateWork;

    public Employee() {
    }

    public Employee(String eplCode, String userName, String phone, String email, String addrees, Boolean gender, Date dateBirth, Double salary, String position, String department, Blob imageBlob, Date dateWork) {
        this.EplCode = new SimpleStringProperty(eplCode);
        this.userName = new SimpleStringProperty(userName);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.addrees = new SimpleStringProperty(addrees);
        this.gender = new SimpleBooleanProperty(gender);
        this.dateBirth = new SimpleObjectProperty<>(dateBirth);
        this.salary = new SimpleDoubleProperty(salary);
        this.position = new SimpleStringProperty(position);
        this.department = new SimpleStringProperty(department);
        this.imageBlob = imageBlob;
        this.dateWork = new SimpleObjectProperty<>(dateWork);
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

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position = new SimpleStringProperty(position);
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
