/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public interface DAOCustomer {
    ObservableList<Customer> getAllCustomer();
    
    void insertCustomer(String cucode1,String customerCode,String name,String addrees,String phone,String email,Integer level );
    
    void updateCustomer(String customerCode,String name,String addrees,String phone,String email,Integer level);

    void deleteCustomer(String customerCode);
    

}
