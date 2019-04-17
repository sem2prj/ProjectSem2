/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ChartTotalAmountController implements Initializable {

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    @FXML
    private LineChart<String, Double> lineChartMonth;
    @FXML
    private LineChart<String, Double> lineChartDay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ChartTotalAmountController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Months
        loadLineChartMonth();
        //Days
        loadLineChartDay();
    }

    private void loadLineChartMonth() {
        String sql = "Select Month(OrderDate), Sum(AmountTotal)from Orders\n"
                + "Where YEAR(OrderDate)=Year(getdate())\n"
                + "GROUP BY Month(OrderDate)\n"
                + "ORDER BY Month(OrderDate)";
        XYChart.Series<String, Double> seriesMonth = new XYChart.Series<>();
        seriesMonth.setName("Total Amount For Every Month");
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                seriesMonth.getData().add(new XYChart.Data<>(rs.getString(1), rs.getDouble(2)));
            }
            lineChartMonth.getData().add(seriesMonth);

        } catch (SQLException ex) {
            Logger.getLogger(ChartTotalAmountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadLineChartDay() {
        String sql = "Select DAY(OrderDate), Sum(AmountTotal) from Orders\n"
                + "Where Month(OrderDate)=Month(getdate()) and YEAR(OrderDate)=Year(getdate())\n"
                + "GROUP BY DAY(OrderDate)\n"
                + "ORDER BY DAY(OrderDate)";
        XYChart.Series<String, Double> seriesDay = new XYChart.Series<>();
        seriesDay.setName("Total Amount For Every Day");
        
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                seriesDay.getData().add(new XYChart.Data<>(rs.getString(1), rs.getDouble(2)));
            }
            lineChartDay.getData().add(seriesDay);

        } catch (SQLException ex) {
            Logger.getLogger(ChartTotalAmountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
