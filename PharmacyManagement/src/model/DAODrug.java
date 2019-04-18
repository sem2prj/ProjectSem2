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
public interface DAODrug {

    ObservableList<Drug> getAllDrug();

    void insertDrug(String Code1,String nameSuper,String supplierSuper,String DCode, String Name, String Categories, String Unit, Blob Image, String Status, Double BuyPrice, Double SellPrice, String Supplier,String description);

    void updateDrug(String DCode, String Name, String Categories, String Unit, Blob Image, String Status, Double BuyPrice, Double SellPrice, String Supplier,String description,int id);

    void deleteDrug(String dCode);
    
    Image getImage(String DCode);
    
}
