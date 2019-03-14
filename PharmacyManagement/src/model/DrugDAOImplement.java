/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.AlertDialog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public class DrugDAOImplement implements DAODrug {

    @Override
    public ObservableList<Drug> getAllDrug() {
        ObservableList<Drug> listDrug = FXCollections.observableArrayList();
        String sql = "SELECT * FROM drug";
        
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Drug drug = new Drug();
                drug.setDCode(rs.getString("dcode"));
                drug.setNameDrug(rs.getString("namedrug"));
                drug.setType(rs.getString("typedrug"));
                drug.setUNit(rs.getString("unit"));
                drug.setPriceIn(rs.getDouble("pricein"));
                drug.setSaleprice(rs.getDouble("saleprice"));
                drug.setSupplier(rs.getString("supplier"));
                drug.setAmount(rs.getInt("amount"));
                drug.setDesciption(rs.getString("desciption"));
                
                listDrug.add(drug);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DrugDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDrug;
    }

    @Override
    public void insertDrug(String dCode, String nameDrug, String type, String uNit, Double priceIn, Double saleprice, String supplier, Integer amount, String desciption) {
       String sql="INSERT INTO drug (dcode, namedrug,typedrug,unit,pricein,saleprice,supplier,amount,desciption) VALUES(?,?,?,?,?,?,?,?,?)";
       
       try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, dCode);
            pst.setString(2, nameDrug);
            pst.setString(3, type);
            pst.setString(4, uNit);
            pst.setDouble(5, priceIn);
            pst.setDouble(6, saleprice);
            pst.setString(7, supplier);
            pst.setInt(8, amount);
            pst.setString(9, desciption);
            
            int i = pst.executeUpdate();

            if (i !=0) {
                AlertDialog.display("Info", "Data Insert Successfully");
            }else{
                  AlertDialog.display("Info", "Data Insert Failure");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DrugDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    @Override
    public void updateDrug(String dCode, String nameDrug, String type, String uNit, Double priceIn, Double saleprice, String supplier, Integer amount, String desciption) {
        String sql="UPDATE drug SET  namedrug=?,typedrug=?,unit=?,pricein=?,saleprice=?,supplier=?,amount=?,desciption=? WHERE dcode=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            
            pst.setString(1, nameDrug);
            pst.setString(2, type);
            pst.setString(3, uNit);
            pst.setDouble(4, priceIn);
            pst.setDouble(5, saleprice);
            pst.setString(6, supplier);
            pst.setInt(7, amount);
            pst.setString(8, desciption);
            pst.setString(9, dCode);
            
            int i = pst.executeUpdate();

            if (i !=0 ) {
                AlertDialog.display("Info", "Data Update Successfully");
            }else{
                AlertDialog.display("Info", "Data Update Failure");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DrugDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    @Override
    public void deleteDrug(String dCode) {
        String sql = "DELETE FROM drug WHERE dcode=(?)";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, dCode);
            int i=pst.executeUpdate();
            if (i !=0) {
                AlertDialog.display("Info", "Data Delete Successfully");
            }else{
                AlertDialog.display("Info", "Data Delete Failure");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DrugDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    @Override
    public ObservableList<Drug> searchDrug(String dCode) {
        ObservableList<Drug> listDrug = FXCollections.observableArrayList();
        String sql = "SELECT * FROM drug WHERE dcode = (?) ";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, dCode);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Drug drug = new Drug();
                drug.setDCode(rs.getString("dcode"));
                drug.setNameDrug(rs.getString("namedrug"));
                drug.setType(rs.getString("typedrug"));
                drug.setUNit(rs.getString("unit"));
                drug.setPriceIn(rs.getDouble("pricein"));
                drug.setSaleprice(rs.getDouble("saleprice"));
                drug.setSupplier(rs.getString("supplier"));
                drug.setAmount(rs.getInt("amount"));
                drug.setDesciption(rs.getString("desciption"));
                
                listDrug.add(drug);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeeDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDrug;   
    }

}
