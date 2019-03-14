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
public interface DAODrug {

    ObservableList<Drug> getAllDrug();

    void insertDrug(String dCode, String nameDrug, String type, String uNit, Double priceIn, Double saleprice, String supplier, Integer amount, String desciption);

    void updateDrug(String dCode, String nameDrug, String type, String uNit, Double priceIn, Double saleprice, String supplier, Integer amount, String desciption);

    void deleteDrug(String dCode);

    ObservableList<Drug> searchDrug(String dCode);
}
