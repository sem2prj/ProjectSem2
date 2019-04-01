/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.AlertDialog;
import java.sql.CallableStatement;
import java.sql.Connection;
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
public class UsernameDAOImplement implements DAOUser{

    @Override
    public ObservableList<User> getAllUser() {
       ObservableList<User> listUser = FXCollections.observableArrayList();
        String sql = "{call dbo.getUserMission}";  
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("username"));
                user.setFullname(rs.getString("fullname"));
                user.setPassword(rs.getString("pass"));
                user.setDeparment(rs.getString("department"));
                user.setMission(rs.getString("mission"));
                listUser.add(user);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsernameDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUser;
    }

    @Override
    public void updateUser(String code, String password) {
       String sql = "{call dbo.updatePassCode(?,?)}";
          try (Connection connection = controller.ConnectDB.connectSQLServer();
                CallableStatement pst = connection.prepareCall(sql);) {
            pst.setString(1, password);
            pst.setString(2, code);
            int i = pst.executeUpdate();
            if (i != 0 ) {
                AlertDialog.display("Info", "Data Update Successfully");
            } else {
                AlertDialog.display("Info", "Data Update is Failing");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeeDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
