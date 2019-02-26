/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;

import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ChartController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
         handleLLoad();
    }

    private void handleLLoad() {
        String sql = "select years,amount from export order by years asc";
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Full");
        try {
            pst = con.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()) {                
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
                
            }
            barChart.getData().add(series);
            
        } catch (SQLException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql2 = "select years,amount from export order by years asc";
        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
        series2.setName("Empty");
        try {
            pst = con.prepareStatement(sql2);
            rs=pst.executeQuery();
            while (rs.next()) {                
                series2.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
                
            }
            barChart.getData().add(series2);
        } catch (SQLException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

}
