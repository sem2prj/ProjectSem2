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
public class SupplierDAOIplement implements DAOSupplier {

    @Override
    public ObservableList<Supplier> getAllSupplier() {
        ObservableList<Supplier> listSupplier = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Supplier";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setCode(rs.getString("SuCode"));
                supplier.setNameSupplier(rs.getString("SuName"));
                supplier.setAddrees(rs.getString("SuAddrees"));
                supplier.setPhone(rs.getString("SuPhone"));
                supplier.setTaxINumber(rs.getString("SuTax"));
                supplier.setEmail(rs.getString("SuEmail"));
                supplier.setWebsite(rs.getString("SuWebsite"));
                supplier.setNotice(rs.getString("SuNotice"));

                listSupplier.add(supplier);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SupplierDAOIplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSupplier;
    }

    @Override
    public void insertSupplier(String Code,String Code2, String nameSupplier, String addrees, String phone, String taxInumber, String email, String website, String notice) {
        String sql = "IF NOT EXISTS (SELECT * FROM  Supplier WHERE SuCode= ?)  INSERT INTO Supplier(SuCode,SuName,SuAddrees,SuPhone,SuTax,SuEmail,SuWebsite,SuNotice) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, Code);
            pst.setString(2, Code2);
            pst.setString(3, nameSupplier);
            pst.setString(4, addrees);
            pst.setString(5, phone);
            pst.setString(6, taxInumber);
            pst.setString(7, email);
            pst.setString(8, website);
            pst.setString(9, notice);

            int i = pst.executeUpdate();
            if (i ==1) {
                AlertDialog.display("Info", "Data Insert Successfully");
            } else {
                AlertDialog.display("Info", "Supplier Code already exists");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SupplierDAOIplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateSupplier(String Code, String nameSupplier, String addrees, String phone, String taxInumber, String email, String website, String notice) {
        String sql = "UPDATE supplier SET SuName=?,SuAddrees=?,SuPhone=?,SuTax=?,SuEmail=?,SuWebsite=?,SuNotice=? WHERE SuCode=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {

            pst.setString(1, nameSupplier);
            pst.setString(2, addrees);
            pst.setString(3, phone);
            pst.setString(4, taxInumber);
            pst.setString(5, email);
            pst.setString(6, website);
            pst.setString(7, notice);
            pst.setString(8, Code);

            int i = pst.executeUpdate();
            if (i != 0) {
                AlertDialog.display("Info", "Data Update Successfully");
            } else {
                AlertDialog.display("Info", "Data Update is Failing");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SupplierDAOIplement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void deleteSupplier(String Code) {
        String sql = "DELETE FROM supplier WHERE SuCode=(?)";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, Code);
            int i = pst.executeUpdate();
            if (i != 0) {
                AlertDialog.display("Info", "Data Delete Successfully");
            } else {
                AlertDialog.display("Info", "Data deletion is failing");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SupplierDAOIplement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
