/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ReportDemo_GrossRevenueController implements Initializable {

    @FXML
    private JFXComboBox<String> combobox;
    @FXML
    private Label lb_tu;
    @FXML
    private JFXDatePicker date_1;
    @FXML
    private Label lb_den;
    @FXML
    private JFXDatePicker date_2;
    @FXML
    private TableView<RevenueReport> table_view;
    @FXML
    private TableColumn<RevenueReport, String> column_cuahang;
    @FXML
    private TableColumn<RevenueReport, String> column_doanhthu;
    @FXML
    private Button button;
    
    private ObservableList<RevenueReport> data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList(
                new RevenueReport("1000000"),
                new RevenueReport("2000000")
        );
        
        column_cuahang.setCellValueFactory(new PropertyValueFactory<RevenueReport, String>("cuahang"));
        column_doanhthu.setCellValueFactory(new PropertyValueFactory<RevenueReport, String>("doanhthu"));
        
        column_cuahang.setStyle( "-fx-alignment: CENTER;");
        column_doanhthu.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        comboboxDefault();
        comboboxDo();
        table_view.setItems(data);
    }
    
    private void comboboxDefault() {
        combobox.getItems().addAll("Ngay", "Tuan", "Thang", "Nam", "All time","Custom");
        combobox.getSelectionModel().selectFirst();
        combobox.getValue();
    }
        private void comboboxDo() {
        if (combobox.getValue() != null) {
            switch (combobox.getValue().toLowerCase()) {
                case "ngay":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    
                    break;
                case "tuan":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
               
                    break;
                case "thang":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                    
                    break;
                case "nam":
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
            
                    break;
                case "custom":
                    lb_tu.setVisible(true);
                    lb_den.setVisible(true);
                    date_1.setVisible(true);
                    date_2.setVisible(true);
                  
                    break;
                default:
                    lb_tu.setVisible(false);
                    lb_den.setVisible(false);
                    date_1.setVisible(false);
                    date_2.setVisible(false);
                   
                    break;
            }
        }
    }

    @FXML
    private void action_combobox(ActionEvent event) {
         comboboxDo();
    }

    @FXML
    private void OutAction(ActionEvent event) {
    }
    
    
    
    public static class RevenueReport{
        
        private final SimpleStringProperty cuahang ;
        private final SimpleStringProperty doanhthu ;
        
        RevenueReport(String fdoanhthu){
            this.cuahang = new SimpleStringProperty("3D Pharmacy");
            this.doanhthu = new SimpleStringProperty(fdoanhthu);
            
        }
        
        public String getCuahang(){
            return cuahang.get();
        }
        
        public void setCuahang(String fcuahang){
            cuahang.set(fcuahang);
        }
        
        public String getDoanhthu(){
            return doanhthu.get();
        }
        
        public void setDoanhthu(String fdoanhthu){
            doanhthu.set(fdoanhthu);
        }
        
        
    }
}
