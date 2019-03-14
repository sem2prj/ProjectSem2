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
        String sql = "SELECT * FROM supplier";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setNameSupplier(rs.getString("nameSupplier"));
                supplier.setType(rs.getString("type"));
                supplier.setAddrees(rs.getString("addrees"));
                supplier.setPhone(rs.getString("phone"));
                supplier.setTaxINumber(rs.getString("taxINumber"));
                supplier.setEmail(rs.getString("email"));
                supplier.setWebsite(rs.getString("website"));
                supplier.setNotice(rs.getString("notice"));

                listSupplier.add(supplier);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SupplierDAOIplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSupplier;
    }

    @Override
    public void insertSupplier(String nameSupplier, String type, String addrees, String phone, String taxInumber, String email, String website, String notice) {
        String sql = "INSERT INTO supplier(nameSupplier,type,addrees,phone,taxINumber,email,website,notice) VALUES(?,?,?,?,?,?)";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, nameSupplier);
            pst.setString(2, type);
            pst.setString(3, addrees);
            pst.setString(4, phone);
            pst.setString(5, taxInumber);
            pst.setString(6, email);
            pst.setString(7, website);
            pst.setString(8, notice);

            int i = pst.executeUpdate();
            if (i != 0) {
                AlertDialog.display("Info", "Data Insert Successfully");
            } else {
                AlertDialog.display("Info", "Data Insert Failing");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SupplierDAOIplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateSupplier(String nameSupplier, String type, String addrees, String phone, String taxInumber, String email, String website, String notice) {
        String sql = "UPDATE supplier SET nameSupplier=?,type=?,addrees=?,phone=?,email=?,website=?,notice=? WHERE taxINumber=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, nameSupplier);
            pst.setString(2, type);
            pst.setString(3, addrees);
            pst.setString(4, phone);
            pst.setString(5, email);
            pst.setString(6, website);
            pst.setString(8, notice);
            pst.setString(9, taxInumber);

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
    public void deleteSupplier(String taxInumber) {
        String sql = "DELETE FROM supplier WHERE taxINumber=(?)";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, taxInumber);
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
