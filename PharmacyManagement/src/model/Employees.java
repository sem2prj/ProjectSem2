/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PC
 */
public class Employees{

    private IntegerProperty employee_id;
    private StringProperty Name;
    private StringProperty email;
    private StringProperty phone_number;
    private SimpleObjectProperty<Date> hire_date;
    private StringProperty job_id;
    private IntegerProperty salary;
    private DoubleProperty commission_pct;
    private IntegerProperty manager_id;
    private IntegerProperty department_id;
    
    public Employees() {
    }
    
    public Employees(int employee_id, String Name, String email, String phone_number,Date hire_date, String job_id, int salary,double commission_pct,int manager_id,int department_id) {
        this.employee_id = new SimpleIntegerProperty(employee_id);
        this.Name = new SimpleStringProperty(Name);
        this.email = new SimpleStringProperty(email);
        this.phone_number = new SimpleStringProperty(phone_number);
        this.hire_date = new SimpleObjectProperty<>(hire_date);
        this.job_id = new SimpleStringProperty(job_id);
        this.salary = new SimpleIntegerProperty(salary);
        this.commission_pct= new SimpleDoubleProperty(commission_pct);
        this.manager_id=new SimpleIntegerProperty(manager_id);
        this.department_id=new SimpleIntegerProperty(department_id);
    }

    public Integer getEmployee_id() {
        return employee_id.get();
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = new SimpleIntegerProperty(employee_id);
    }
       
    
    public String getName() {
        return Name.get();
    }
    
    public void setName(String Name) {
        this.Name = new SimpleStringProperty(Name);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email =new SimpleStringProperty(email);
    }

    public String getPhone_number() {
        return phone_number.get();
    }

    public void setPhone_number(String phone_number) {
        this.phone_number =new SimpleStringProperty(phone_number);
    }

    public Object getHire_date() {
        return hire_date.get();
    }

    public void setHire_date(Date hire_date) {
        this.hire_date =new SimpleObjectProperty<>(hire_date) ;
    }

    public String getJob_id() {
        return job_id.get();
    }

    public void setJob_id(String job_id) {
        this.job_id =new SimpleStringProperty(job_id);
    }

    public Integer getSalary() {
        return salary.get();
    }

    public void setSalary(Integer salary) {
        this.salary = new SimpleIntegerProperty(salary);
    }

    public Double getCommission_pct() {
        return commission_pct.get();
    }

    public void setCommission_pct(Double commission_pct) {
        this.commission_pct = new SimpleDoubleProperty(commission_pct);
    }

    public Integer getManager_id() {
        return manager_id.get();
    }

    public void setManager_id(Integer manager_id) {
        this.manager_id = new SimpleIntegerProperty(manager_id);
    }

    public Integer getDepartment_id() {
        return department_id.get();
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id =new SimpleIntegerProperty(department_id);
    }
    
    
    
    
    

    
    
   
    
   
}
