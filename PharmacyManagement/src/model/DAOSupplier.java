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
public interface DAOSupplier {
    ObservableList<Supplier> getAllSupplier();
    
    void insertSupplier(String nameSupplier,String type,String addrees,String phone,String taxInumber,String email,String website,String notice);
    
    void updateSupplier(String nameSupplier,String type,String addrees,String phone,String taxInumber,String email,String website,String notice);
   
    void deleteSupplier(String taxInumber);
}
