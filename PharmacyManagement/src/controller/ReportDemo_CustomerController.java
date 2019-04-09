/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ReportDemo_CustomerController implements Initializable {

    @FXML
    private TableView<CustomerReport> table_view;
    @FXML
    private TableColumn<CustomerReport, String> column_tenkh;
    @FXML
    private TableColumn<CustomerReport, String> column_makh;
    @FXML
    private TableColumn<CustomerReport, String> column_diachi;
    @FXML
    private TableColumn<CustomerReport, String> column_sdt;
    @FXML
    private TableColumn<CustomerReport, String> column_email;
    @FXML
    private TableColumn<CustomerReport, String> column_level;
    @FXML
    private TableColumn<CustomerReport, String> column_tien;
    @FXML
    private Button button;
    @FXML
    private Label lb_tongtien;
    @FXML
    private Label lb_solan;
    @FXML
    private Label lb_tien;
    @FXML
    private Label lb_lan;
    @FXML
    private JFXTextField tf_search;

    private ObservableList<CustomerReport> data;
    @FXML
    private ImageView image_view;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data=FXCollections.observableArrayList(
                new CustomerReport("Adam", "KH0001", "HaiChau", "0908122588", "a@gmail.com", "Vip", "1000000"),
                new CustomerReport("Adam", "KH0001", "HaiChau", "0908122588", "a@gmail.com", "Vip", "1000000"),
                new CustomerReport("Adam", "KH0001", "HaiChau", "0908122588", "a@gmail.com", "Vip", "1000000"),
                new CustomerReport("Adam", "KH0001", "HaiChau", "0908122588", "a@gmail.com", "Vip", "1000000")
                
        
        );
        column_tenkh.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("tenkh"));
        column_makh.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("makh"));
        column_diachi.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("diachi"));
        column_sdt.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("sdt"));
        column_email.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("email"));        
         column_level.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("level"));       
        column_tien.setCellValueFactory(new PropertyValueFactory<CustomerReport, String>("tien"));       
                
        lb_tien.setText("999999999999");
        lb_lan.setText("10");
        table_view.setItems(data);
        
        Image image = new Image("/image/jimmy.jpg");
        image_view.setImage(image);
    }    

    @FXML
    private void deleteAction(ActionEvent event) {
    }
    
    
    public static class CustomerReport{
        
        private final SimpleStringProperty tenkh;
        private final SimpleStringProperty makh;
        private final SimpleStringProperty diachi;
        private final SimpleStringProperty sdt;
        private final SimpleStringProperty email;
        private final SimpleStringProperty level;
        private final SimpleStringProperty tien;

        CustomerReport(String ftenkh, String fmakh, String fdiachi, String fsdt, String femail, String flevel, String ftien) {
            this.tenkh = new SimpleStringProperty(ftenkh);
            this.makh = new SimpleStringProperty(fmakh);
            this.diachi = new SimpleStringProperty(fdiachi);
            this.sdt = new SimpleStringProperty(fsdt);
            this.email = new SimpleStringProperty(femail);
            this.level = new SimpleStringProperty(flevel);
            this.tien = new SimpleStringProperty(ftien);
        }
        
        public String getTenkh(){
            return tenkh.get();
        }
        
        public void setTenkh(String ftenkh){
            tenkh.set(ftenkh);
        }
        
        public String getMakh(){
            return makh.get();
        }
        
        public void setMakh(String fmakh){
            makh.set(fmakh);
        }
        
        public String getDiachi(){
            return diachi.get();
        }
        
        public void setDiachi(String fdiachi){
            diachi.set(fdiachi);
        }
        
        public String getSdt(){
            return sdt.get();
        }
        
        public void setSdt(String fsdt){
            sdt.set(fsdt);
        }
        
        public String getEmail(){
            return email.get();
        }
        
        public void setEmail(String femail){
            email.set(femail);
        }
        
        public String getLevel(){
            return level.get();
        }
        
        public void setLevel(String flevel){
            level.set(flevel);
        }
        
        public String getTien(){
            return tien.get();
        }
        
        public void setTien(String ftien){
            tien.set(ftien);
        }
    }
}
