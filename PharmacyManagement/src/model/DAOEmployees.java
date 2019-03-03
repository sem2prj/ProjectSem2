/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public interface DAOEmployees {

    ObservableList<Employees> getAllEmployee();

    void insertEmployees(int employee_id,String name,String phone,String email,Date hire_date,String jobid,int salary,double commission,int managerid,int deparmentid );

    void updateEmployees(int employee_id,String name,String phone,String email,Date hire_date,String jobid,int salary,double commission,int managerid,int deparmentid );

    void deleteEmployees(int employee_id);
    
    ObservableList<Employees> searchIdEmployee(int code);
    
    
}
