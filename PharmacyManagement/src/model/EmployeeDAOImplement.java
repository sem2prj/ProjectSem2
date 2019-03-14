/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.AlertDialog;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author PC
 */
public class EmployeeDAOImplement implements DAOEmployee {

    @Override
    public ObservableList<Employee> getAllEmployee() {
        ObservableList<Employee> listEmployee = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee";

        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEplCode(rs.getString("eplCode"));
                employee.setUserName(rs.getString("username"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setAddrees(rs.getString("addrees"));
                employee.setGender(rs.getBoolean("gender"));
                employee.setDateBirth(rs.getDate("dateOfBirth"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setPosition(rs.getString("position"));
                employee.setDepartment(rs.getString("department"));
                employee.setImageBlob(rs.getBlob("blogImage"));
                employee.setDateWork(rs.getDate("dateWork"));

                listEmployee.add(employee);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeeDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEmployee;
    }

    @Override
    public void insertEmployee(String eplCode, String username, String phone, String email, String addrees, boolean gender, Date dateofBirth, double salary, String position, String department, Blob blobImage, Date dateWork) {
        String sql = "INSERT INTO employee (eplCode, username, phone, email, addrees, gender,dateOfBirth,salary,position,department,blogImage,dateWork) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, eplCode);
            pst.setString(2, username);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setString(5, addrees);
            pst.setBoolean(6, gender);
            pst.setDate(7, dateofBirth);
            pst.setDouble(8, salary);
            pst.setString(9, position);
            pst.setString(10, department);
            pst.setBlob(11, blobImage);
            pst.setDate(12, dateWork);
            int i = pst.executeUpdate();

            if (i != 0) {
                AlertDialog.display("Info", "Data Insert Successfully");
            } else {
                AlertDialog.display("Info", "Data Insert Failing");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeeDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateEmployee(String eplCode, String username, String phone, String email, String addrees, boolean gender, Date dateofBirth, double salary, String position, String department, Blob blobImage, Date dateWork) {
        String sql = "UPDATE employee SET username=?,phone=?,email=?,addrees=?,gender=?,dateOfBirth=?,salary=?,position=?,department=?,blogImage=?,dateWork=? WHERE eplCode=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
           
            pst.setString(1, username);
            pst.setString(2, phone);
            pst.setString(3, email);
            pst.setString(4, addrees);
            pst.setBoolean(5, gender);
            pst.setDate(6, dateofBirth);
            pst.setDouble(7, salary);
            pst.setString(8, position);
            pst.setString(9, department);
            pst.setBlob(10, blobImage);
            pst.setDate(11, dateWork);
             pst.setString(12, eplCode);
            int i = pst.executeUpdate();
            if (i != 0) {
                AlertDialog.display("Info", "Data Update Successfully");
            } else {
                AlertDialog.display("Info", "Data Update is Failing");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeeDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteEmployee(String eplCode) {
        String sql = "DELETE FROM employee WHERE eplCode=(?)";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, eplCode);
            int i = pst.executeUpdate();
            if (i != 0) {
                AlertDialog.display("Info", "Data Delete Successfully");
            } else {
                AlertDialog.display("Info", "Data deletion is failing");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeeDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ObservableList<Employee> searchCodeEmployee(String eplCode) {
        ObservableList<Employee> listEmployee = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee WHERE eplCode=(?)";

        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, eplCode);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEplCode(rs.getString("eplCode"));
                employee.setUserName(rs.getString("username"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setAddrees(rs.getString("addrees"));
                employee.setGender(rs.getBoolean("gender"));
                employee.setDateBirth(rs.getDate("dateOfBirth"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setPosition(rs.getString("position"));
                employee.setDepartment(rs.getString("department"));
                employee.setImageBlob(rs.getBlob("blogImage"));
                employee.setDateWork(rs.getDate("dateWork"));

                listEmployee.add(employee);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeeDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listEmployee;
    }
    
//    create table employee (
//	employeId int identity,
//	eplCode varchar(50) ,
//	username varchar(50) ,
//	phone varchar(50) ,
//	email varchar(50) ,
//	addrees varchar(50) ,
//	gender bit,
//	dateOfBirth date,
//	salary float,
//	position varchar(50) ,
//	department varchar(50) ,
//	blogImage varbinary(max),
//	dateWork date,
//    )

    @Override
    public ObservableList<Employee> searchCodeEmployee(String username, String email, Date dateofBirth) {
        ObservableList<Employee> listEmployee = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee WHERE username=?,email=?,dateofBirth=?";

        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setDate(3, dateofBirth);
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEplCode(rs.getString("eplCode"));
                employee.setUserName(rs.getString("username"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setAddrees(rs.getString("addrees"));
                employee.setGender(rs.getBoolean("gender"));
                employee.setDateBirth(rs.getDate("dateOfBirth"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setPosition(rs.getString("position"));
                employee.setDepartment(rs.getString("department"));
                employee.setImageBlob(rs.getBlob("blogImage"));
                employee.setDateWork(rs.getDate("dateWork"));

                listEmployee.add(employee);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeeDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listEmployee;
    }
    
     //converted image on Blob SQL
    @Override
    public Image getImage(String eplCode) {
       String sql = "select blogImage from employee where eplCode=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement prepareStatement = connection.prepareStatement(sql);) {
            prepareStatement.setString(1, eplCode);
            ResultSet rs = prepareStatement.executeQuery();
            Image image = null;
            if (rs.next()) {
                Blob botto = rs.getBlob("blogImage");
                InputStream is = botto.getBinaryStream();
                image = new Image(is);
                is.close();
            }
            rs.close();
            return image;
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(EmployeeDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
