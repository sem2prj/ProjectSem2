/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ReportDetailWarehouseController implements Initializable {

    @FXML
    private JFXComboBox<String> combobox;
    @FXML
    private TableView<LiabilitiesReport> table_view;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_id;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_name;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_totalpaying;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_alreadypaying;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_remain;
    @FXML
    private TableColumn<LiabilitiesReport, String> column_exdate;
    @FXML
    private Button button;

    private ObservableList<LiabilitiesReport> data;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         data = FXCollections.observableArrayList(
         new LiabilitiesReport("Stock0001","Paracetamol","BiG Pharmacy","700","01/01/2021","01/01/2019"),
                 new LiabilitiesReport("Stock0002","Endorphine","Sun Pharmacy","800","01/01/2021","01/09/2019"),
                 new LiabilitiesReport("Stock0003","Mutivitamin control","Vinh Pharmacy","150","01/01/2021","01/01/2019"),
                 new LiabilitiesReport("Stock0004","TooGood for men","Sun Pharmacy","100","01/01/2021","01/01/2019"),
                 new LiabilitiesReport("Stock0005","Extacin","Null Pharmac","200","01/01/2021","01/01/2019")
         
         );
         
        combobox.getItems().addAll("This time", "All time");
        combobox.getSelectionModel().selectFirst();
        combobox.getValue();
        
        column_id.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("id"));
        column_name.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("suppliername"));
        column_totalpaying.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("total"));
        column_alreadypaying.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("already"));
        column_remain.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("remain"));
        column_exdate.setCellValueFactory(new PropertyValueFactory<LiabilitiesReport, String>("expdate"));
        
        table_view.setItems(data);
        
        
    }

    @FXML
    private void OkAction(ActionEvent event) {
    }
    
    
    
    public static class LiabilitiesReport{
        
        private final SimpleStringProperty id;
        private final SimpleStringProperty suppliername;
        private final SimpleStringProperty total;
        private final SimpleStringProperty already;
        private final SimpleStringProperty remain;
        private final SimpleStringProperty expdate;
        
        LiabilitiesReport(String fid,String fsuppliername, String ftotal, String falready,String fremain,String fexpdate ){
            this.id = new SimpleStringProperty(fid);
            this.suppliername = new SimpleStringProperty(fsuppliername);
            this.total = new SimpleStringProperty(ftotal);
            this.already =new SimpleStringProperty(falready);
            this.remain = new SimpleStringProperty(fremain);
            this.expdate = new SimpleStringProperty(fexpdate);
        
        }
        
        public String getId(){
            return id.get();
        }
        
        public void setId(String fid){
            id.set(fid);
        }
        
        public String getSuppliername(){
            return suppliername.get();
        }
        
        public void setSuppliername(String fsuppliername){
            suppliername.set(fsuppliername);
        }
        
        public String getTotal(){
            return total.get();
        }
        
        public void setTotal(String ftotal){
            total.set(ftotal);
        }
        
        public String getAlready(){
            return already.get();
        }
        
        public void setAlready(String falready){
            already.set(falready);
        }
        
        public String getRemain(){
            return remain.get();
        }
        
        public void setRemain(String fremain){
            remain.set(fremain);
        }
        
        public String getExpdate(){
            return expdate.get();
        }
        
        public void setExpdate(String fexpdate){
            expdate.set(fexpdate);
        }
    }
    
}
