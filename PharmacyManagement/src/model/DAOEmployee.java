/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Blob;
import java.sql.Date;
import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public interface DAOEmployee {

    ObservableList<Employee> getAllEmployee();

    void insertEmployee(String eplCode,String username,String phone,String email,String addrees,boolean gender,Date dateofBirth,double salary,String position,String department,Blob blobImage,Date dateWork );
    
    void updateEmployee(String eplCode,String username,String phone,String email,String addrees,boolean gender,Date dateofBirth,double salary,String position,String department,Blob blobImage,Date dateWork);

    void deleteEmployee(String eplCode);
    
    ObservableList<Employee> searchCodeEmployee(String eplCode);
    
    
}
