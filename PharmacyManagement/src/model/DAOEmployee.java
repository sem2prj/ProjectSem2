/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Blob;
import java.sql.Date;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author PC
 */
public interface DAOEmployee {

    ObservableList<Employee> getAllEmployee();

   void insertEmployee(String eplCode, String phone, String email, String addrees, boolean gender, Date dateofBirth, double salary,  String department, Blob blobImage, Date dateWork,String roles,String username,String Pass);

    void updateEmployee(String eplCode, String phone, String email, String addrees, boolean gender, Date dateofBirth, double salary,  String department, Blob blobImage, Date dateWork,String roles,String username,int id);

    void deleteEmployee(String eplCode);
    
    ObservableList<Employee> searchCodeEmployee(String eplCode);
    ObservableList<Employee> searchCodeEmployee(String username, String email, Date dateofBirth);
    
    Image getImage(String eplCode);
    
    
}
