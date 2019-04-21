/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ReportDemoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleExpriredDrug(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ReportExpiredDrugs.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/reportDrug.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("ExpiredDrugs");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleRevenue(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ReportGrossRevenue.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/reportRevenue.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Revenue");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleEployeeYear(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ReportEmployee.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/reportEmployee.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Revenue");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ReportCustomer.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/reportCustomer.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleWareHouse(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ReportDetailWarehouse.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/reportDetailWareHouse.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("DetailWareHouse");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleLibilities(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ReportDemo_Liabilities.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/reportLiabilities.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Liabilities");
        stage.setScene(scene);
        stage.show();
    }

}
