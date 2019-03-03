/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ConnectDB;
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

/**
 *
 * @author PC
 */
public class EmployeesDAOImplement implements DAOEmployees {

    @Override
    public ObservableList<Employees> getAllEmployee() {
        ObservableList<Employees> listEmployees = FXCollections.observableArrayList();
        String sql = "select * from employees";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Employees employees = new Employees();
                employees.setEmployee_id(rs.getInt("code"));
                employees.setName(rs.getString("name"));
                employees.setEmail(rs.getString("email"));
                employees.setPhone_number(rs.getString("phone"));
                employees.setHire_date(rs.getDate("hidate"));
                employees.setJob_id(rs.getString("jobid"));
                employees.setSalary(rs.getInt("salary"));
                employees.setCommission_pct(rs.getDouble("commission"));
                employees.setManager_id(rs.getInt("manager"));
                employees.setDepartment_id(rs.getInt("deparment"));
                listEmployees.add(employees);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeesDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEmployees;
    }

    @Override
    public void insertEmployees(int employee_id, String name, String phone,String email, Date hire_date, String jobid, int salary, double commission, int managerid, int deparmentid) {
        String sql = "insert into employees values('" + employee_id + "','" + name + "','" + phone + "','" + email + "','" + hire_date + "','" + jobid + "','" + salary + "','" + commission + "','" + managerid + "','" + deparmentid + "')";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeesDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateEmployees(int employee_id, String name, String phone, String email, Date hire_date, String jobid, int salary, double commission, int managerid, int deparmentid) {
       String sql="update employees set name='"+name+"',phone='"+phone+"',email='"+email+"',hidate='"+hire_date+"',jobid='"+jobid+"',"
               + "salary='"+salary+"',commission='"+commission+"',manager='"+managerid+"',deparment='"+deparmentid+"'where code='"+employee_id+"' ";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeesDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteEmployees(int employee_id) {
       String sql="delete from employees where code='"+employee_id+"'";
       try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeesDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ObservableList<Employees > searchIdEmployee(int code) {
        ObservableList<Employees> listEmployees = FXCollections.observableArrayList();
        String sql = "select * from employees where code ='"+code+"'";
       try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Employees employees = new Employees();
                employees.setEmployee_id(rs.getInt("code"));
                employees.setName(rs.getString("name"));
                employees.setEmail(rs.getString("email"));
                employees.setPhone_number(rs.getString("phone"));
                employees.setHire_date(rs.getDate("hidate"));
                employees.setJob_id(rs.getString("jobid"));
                employees.setSalary(rs.getInt("salary"));
                employees.setCommission_pct(rs.getDouble("commission"));
                employees.setManager_id(rs.getInt("manager"));
                employees.setDepartment_id(rs.getInt("deparment"));
                listEmployees.add(employees);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmployeesDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEmployees;
    }
     
}
//create table employees(
//	code int,
//	name nvarchar(50),
//	email varchar(50),
//	phone varchar(50),
//	hidate date,
//	jobid varchar(50),
//	salary int,
//	commission float,
//	manager int,
//	deparment int,
//)
//
//insert into employees(code,name,email,phone,hidate,jobid,salary,commission,manager,deparment)
//values(80,'duc','voduc204@gmail.com','099999999','2018-9-9','mmm99',2000,999.9,9,9)