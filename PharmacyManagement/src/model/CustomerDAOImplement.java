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
public class CustomerDAOImplement implements DAOCustomer {

    @Override
    public ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> listCustomer = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer";

        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerCode(rs.getString("CuCode"));
                customer.setName(rs.getString("CuName"));
                customer.setAddrees(rs.getString("CuAddrees"));
                customer.setPhone(rs.getString("CuPhone"));
                customer.setEmail(rs.getString("CuEmail"));
                customer.setLevel(rs.getInt("CuLevel"));

                listCustomer.add(customer);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CustomerDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCustomer;

    }

    @Override
    public void insertCustomer(String cucode1,String customerCode, String name, String addrees, String phone, String email, Integer level) {
        String sql = "IF NOT EXISTS (SELECT * FROM  customer WHERE CuCode= ?) INSERT INTO customer (CuCode,CuName,CuAddrees,CuPhone,CuEmail,CuLevel ) VALUES (?,?,?,?,?,?)";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, cucode1);
            pst.setString(2, customerCode);
            pst.setString(3, name);
            pst.setString(4, addrees);
            pst.setString(5, phone);
            pst.setString(6, email);
            pst.setInt(7, level);
            int i = pst.executeUpdate();
            if (i == 1) {
                AlertDialog.display("Info", "Data Insert Successfully");
            } else {
                AlertDialog.display("Info", "Customer Code already exists");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CustomerDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateCustomer(String customerCode, String name, String addrees, String phone, String email, Integer level) {
        String sql = "UPDATE customer SET CuName=?,CuAddrees=?,CuPhone=?,CuEmail=?,CuLevel=? WHERE CuCode=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, name);
            pst.setString(2, addrees);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setInt(5, level);
            pst.setString(6, customerCode);

            int i = pst.executeUpdate();
            if (i != 0) {
                AlertDialog.display("Info", "Data Update Successfully");
            } else {
                AlertDialog.display("Info", "Data Update is Failing");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CustomerDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteCustomer(String customerCode) {
        String sql = "DELETE FROM customer WHERE CuCode=(?)";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, customerCode);
            int i = pst.executeUpdate();
            if (i != 0) {
                AlertDialog.display("Info", "Data Delete Successfully");
            } else {
                AlertDialog.display("Info", "Data deletion is failing");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CustomerDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
