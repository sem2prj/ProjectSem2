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
public class DrugDAOImplement implements DAODrug {

    @Override
    public ObservableList<Drug> getAllDrug() {
        ObservableList<Drug> listDrug = FXCollections.observableArrayList();
        String sql = "{call dbo.getAllProduct}";

        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Drug drug = new Drug();
                drug.setDCode(rs.getString("Code"));
                drug.setName(rs.getString("Name"));
                drug.setCategories(rs.getString("Categories"));
                drug.setUnit(rs.getString("Unit"));
                drug.setImage(rs.getBlob("Images"));
                drug.setStatus(rs.getString("Statuses"));
                drug.setBuyPrice(rs.getDouble("Buy"));
                drug.setSellPrice(rs.getDouble("Sell"));
                drug.setSupplier(rs.getString("Supplier"));
                drug.setDesciption(rs.getString("Descriptions"));
                drug.setId1(rs.getInt("CatID"));
                listDrug.add(drug);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DrugDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDrug;
    }

    @Override
    public void insertDrug(String Code1,String nameSuper,String supplierSuper,String DCode, String Name, String Categories, String Unit, Blob Image, String Status, Double BuyPrice, Double SellPrice, String Supplier, String description) {
        String sql = "INSERT INTO Categories (CatName) VALUES(?)";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pst.setString(1, Categories);
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            Object key = rs.getObject(1);

            String sql1 = "IF NOT EXISTS (SELECT * FROM  Product WHERE PCode= ? or (PName = ? and  Supplier= ?) )\n" +
"	INSERT INTO Product (PCode,PName,PImage,Unit,Statuses,BuyPrice,SellPrice,Supplier,PDescription,CatID) \n" +
"	VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst1 = connection.prepareStatement(sql1);
            pst1.setString(1, Code1);
            pst1.setString(2, nameSuper);
            pst1.setString(3, supplierSuper);
            pst1.setString(4, DCode);
            pst1.setString(5, Name);
            pst1.setBlob(6, Image);
            pst1.setString(7, Unit);
            pst1.setString(8, Status);
            pst1.setDouble(9, BuyPrice);
            pst1.setDouble(10, SellPrice);
            pst1.setString(11, Supplier);
            pst1.setString(12, description);
            pst1.setInt(13, Integer.parseInt(String.valueOf(key)));
            int i = pst1.executeUpdate();

//            ResultSet rs1 = pst1.getGeneratedKeys();
//            rs1.next();
//            Object key1 = rs.getObject(1);
//
//            String sql2 = "INSERT INTO ExpiredTime(ExDate,PId,Qty) VALUES(?,?,?)";
//            PreparedStatement pst2 = connection.prepareStatement(sql2);
//            pst2.setDate(1, Experied);
//            pst2.setInt(2, Integer.parseInt(String.valueOf(key1)));
//            pst2.setInt(3, Quantity);
//            int i = pst2.executeUpdate();
            if (i ==1) {
                AlertDialog.display("Info", "Data Insert Successfully");
            } else {
                AlertDialog.display("Info", "Product Code already exists or the product names with same provider has been duplicated");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DrugDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateDrug(String DCode, String Name, String Categories, String Unit, Blob Image, String Status, Double BuyPrice, Double SellPrice, String Supplier, String description, int id) {
        String sql = "UPDATE Categories SET CatName=?  WHERE CatId=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, Categories);
            pst.setInt(2, id);
            pst.executeUpdate();

            String sql1 = "UPDATE Product SET PName=?,PImage=?,Unit=?,Statuses=?,BuyPrice=?,SellPrice=?,Supplier=?,PDescription=? WHERE PCode=? ";

            PreparedStatement pst1 = connection.prepareStatement(sql1);
            pst1.setString(1, Name);
            pst1.setBlob(2, Image);
            pst1.setString(3, Unit);
            pst1.setString(4, Status);
            pst1.setDouble(5, BuyPrice);
            pst1.setDouble(6, SellPrice);
            pst1.setString(7, Supplier);
            pst1.setString(8, description);
            pst1.setString(9, DCode);
            int i = pst1.executeUpdate();

//            String sql2 = "UPDATE ExpiredTime SET ExDate=?,Qty=? WHERE ExId=?";
//
//            PreparedStatement pst2 = connection.prepareStatement(sql2);
//            pst2.setDate(1, Experied);
//            pst2.setInt(2, Quantity);
//            pst2.setInt(3, id1);
//            int i = pst2.executeUpdate();
            if (i != 0) {
                AlertDialog.display("Info", "Data Update Successfully");
            } else {
                AlertDialog.display("Info", "Data Update Failure");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DrugDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteDrug(String dCode) {
        String sql = "DELETE FROM Product WHERE PCode=(?)";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, dCode);
            int i = pst.executeUpdate();
            if (i != 0) {
                AlertDialog.display("Info", "Data Delete Successfully");
            } else {
                AlertDialog.display("Info", "Data Delete Failure");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DrugDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Image getImage(String DCode) {
        String sql = "SELECT PImage from Product where PCode=?";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                PreparedStatement prepareStatement = connection.prepareStatement(sql);) {
            prepareStatement.setString(1, DCode);
            ResultSet rs = prepareStatement.executeQuery();
            Image image = null;
            if (rs.next()) {
                Blob botto = rs.getBlob("PImage");
                InputStream is = botto.getBinaryStream();
                image = new Image(is);
                is.close();
            }
            rs.close();
            return image;
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(DrugDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
