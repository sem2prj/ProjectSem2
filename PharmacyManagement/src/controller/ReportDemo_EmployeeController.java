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
import javafx.scene.control.Alert;
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
public class ReportDemo_EmployeeController implements Initializable {

    @FXML
    private TableView<EmployeeReport> table_view;
    @FXML
    private TableColumn<EmployeeReport, String> column_manv;
    @FXML
    private TableColumn<EmployeeReport, String> column_tennv;
    @FXML
    private TableColumn<EmployeeReport, String> column_sdt;
    @FXML
    private TableColumn<EmployeeReport, String> column_ngayvaolam;
    @FXML
    private TableColumn<EmployeeReport, String> column_bophan;
    @FXML
    private TableColumn<EmployeeReport, String> column_chucvu;
    @FXML
    private TableColumn<EmployeeReport, String> column_tiendaban;
    @FXML
    private JFXComboBox<String> combobox;
    @FXML
    private Label lb_tu;
    @FXML
    private Button button;
    @FXML
    private JFXDatePicker date_1;
    @FXML
    private Label lb_den;
    @FXML
    private JFXDatePicker date_2;
    
    private ObservableList<EmployeeReport> data;
   
// AlertDialog.display("Info", "Ngay");  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        data = FXCollections.observableArrayList(
             new EmployeeReport("NV001", "Adam", "0090541551", "01-01-1991", "Sale", "Nhan vien", "10000000"),
                new EmployeeReport("NV002", "Frank", "0090541551", "01-01-1991", "Sale", "Nhan vien", "20000000"),
                new EmployeeReport("NV003", "Rook", "0090541551", "01-01-1991", "Sale", "Nhan vien", "30000000"),
                new EmployeeReport("NV004", "Michael", "0090541551", "01-01-1991", "Sale", "Nhan vien", "40000000"),
                new EmployeeReport("NV005", "Scot", "0090541551", "01-01-1991", "Sale", "Nhan vien", "50000000")
        );
      
        column_manv.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("manv"));
        column_tennv.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("tennv"));
        column_sdt.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("sdt"));
        column_ngayvaolam.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("ngayvaolam"));
        column_bophan.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("bophan"));
        column_chucvu.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("chucvu"));
        column_tiendaban.setCellValueFactory(new PropertyValueFactory<EmployeeReport, String>("tiendaban"));
        
        comboboxDefault();
        comboboxDo();
        table_view.setItems(data);
    }

    private void comboboxDefault() {
        combobox.getItems().addAll("Ngay", "Tuan", "Thang", "Nam", "Custom");
        combobox.getSelectionModel().selectFirst();
        combobox.getValue();
    }

    @FXML
    private void action_combobox(ActionEvent event) {
        comboboxDo();
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
    private void deleteAction(ActionEvent event) {
    }

    public static class EmployeeReport {

        private final SimpleStringProperty manv;
        private final SimpleStringProperty tennv;
        private final SimpleStringProperty sdt;
        private final SimpleStringProperty ngayvaolam;
        private final SimpleStringProperty bophan;
        private final SimpleStringProperty chucvu;
        private final SimpleStringProperty tiendaban;

        EmployeeReport(String fmanv, String ftennv, String fsdt, String fngayvaolam, String fbophan, String fchucvu, String ftiendaban) {
            this.manv = new SimpleStringProperty(fmanv);
            this.tennv = new SimpleStringProperty(ftennv);
            this.sdt = new SimpleStringProperty(fsdt);
            this.ngayvaolam = new SimpleStringProperty(fngayvaolam);
            this.bophan = new SimpleStringProperty(fbophan);
            this.chucvu = new SimpleStringProperty(fchucvu);
            this.tiendaban = new SimpleStringProperty(ftiendaban);

        }

        public String getManv() {
            return manv.get();
        }

        public void setManv(String fmanv) {
            manv.set(fmanv);
        }

        public String getTennv() {
            return tennv.get();
        }

        public void setTennv(String ftennv) {
            tennv.set(ftennv);
        }

        public String getSdt() {
            return sdt.get();
        }

        public void setSdt(String fsdt) {
            sdt.set(fsdt);
        }

        public String getNgayvaolam() {
            return ngayvaolam.get();
        }

        public void setNgayvaolam(String fngayvaolam) {
            ngayvaolam.set(fngayvaolam);
        }

        public String getBophan() {
            return bophan.get();
        }

        public void setBophan(String fbophan) {
            bophan.set(fbophan);
        }

        public String getChucvu() {
            return chucvu.get();
        }

        public void setChucvu(String fchucvu) {
            chucvu.set(fchucvu);
        }

        public String getTiendaban() {
            return tiendaban.get();
        }

        public void setTiendaban(String ftiendaban) {
            tiendaban.set(ftiendaban);
        }

    }

}
