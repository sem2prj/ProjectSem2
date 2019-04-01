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
import java.sql.CallableStatement;
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
        String sql = "{call dbo.getAllEmployee}";

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
                employee.setDepartment(rs.getString("department"));
                employee.setImageBlob(rs.getBlob("blogImage"));
                employee.setDateWork(rs.getDate("dateWork"));
                employee.setId(rs.getInt("UserId"));
                employee.setRole(rs.getString("roles"));
                listEmployee.add(employee);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeeDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEmployee;
    }

    @Override
    public void insertEmployee(String eplCode, String phone, String email, String addrees, boolean gender, Date dateofBirth, double salary, String department, Blob blobImage, Date dateWork, String roles, String username, String Pass) {
        String sql2 = "INSERT INTO DetailUser(Code,Phone,Email,Addrees,Sex,BirthDay,Salary,Department,ImageBlob,WorkDay,Mission) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection connection = controller.ConnectDB.connectSQLServer();
            PreparedStatement pst2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            pst2.setString(1, eplCode);
            pst2.setString(2, phone);
            pst2.setString(3, email);
            pst2.setString(4, addrees);
            pst2.setBoolean(5, gender);
            pst2.setDate(6, dateofBirth);
            pst2.setDouble(7, salary);
            pst2.setString(8, department);
            pst2.setBlob(9, blobImage);
            pst2.setDate(10, dateWork);
            pst2.setString(11, roles);
            pst2.executeUpdate();

            ResultSet rs = pst2.getGeneratedKeys();
            rs.next();
            Object key = rs.getObject(1);

            String sql = "{call dbo.InsertUser(?,?,?)}";
            CallableStatement pst = connection.prepareCall(sql);
            pst.setString(1, username);
            pst.setString(2, Pass);
            pst.setString(3, String.valueOf(key));
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
    public void updateEmployee(String eplCode, String phone, String email, String addrees, boolean gender, Date dateofBirth, double salary, String department, Blob blobImage, Date dateWork, String roles, String username, int id) {
        String sql = "UPDATE DetailUser SET Phone=?,Email=?,Addrees=?,Sex=?,BirthDay=?,Salary=?,Department=?,ImageBlob=?,WorkDay=?,Mission=? WHERE Code=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {

            pst.setString(1, phone);
            pst.setString(2, email);
            pst.setString(3, addrees);
            pst.setBoolean(4, gender);
            pst.setDate(5, dateofBirth);
            pst.setDouble(6, salary);

            pst.setString(7, department);
            pst.setBlob(8, blobImage);
            pst.setDate(9, dateWork);
            pst.setString(10, roles);
            pst.setString(11, eplCode);
            int i = pst.executeUpdate();

            String sql2 = "UPDATE Users SET UsersName=? WHERE UsersID=?";
            PreparedStatement pst2 = connection.prepareStatement(sql2);
            pst2.setString(1, username);
            pst2.setInt(2, id);

            int j = pst2.executeUpdate();
            if (i != 0 && j != 0) {
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
        String sql = "DELETE FROM DetailUser WHERE Code=(?)";
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
        String sql = "select ImageBlob from DetailUser where Code=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement prepareStatement = connection.prepareStatement(sql);) {
            prepareStatement.setString(1, eplCode);
            ResultSet rs = prepareStatement.executeQuery();
            Image image = null;
            if (rs.next()) {
                Blob botto = rs.getBlob("ImageBlob");
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
