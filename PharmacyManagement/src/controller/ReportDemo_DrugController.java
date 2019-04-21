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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ReportDemo_DrugController implements Initializable {

    @FXML
    private TableView<DrugReport> table_drug;
    @FXML
    private TableColumn<DrugReport, String> cloumn_name;
    @FXML
    private TableColumn<DrugReport, String> cloumn_code;
    @FXML
    private TableColumn<DrugReport, String> cloumn_date;
    @FXML
    private TableColumn<DrugReport, String> cloumn_select;

    private ObservableList<DrugReport> data ;
    @FXML
    private Button button;
    @FXML
    private JFXTextField tf_name;
    @FXML
    private JFXTextField tf_address;
    @FXML
    private JFXTextField tf_phone;
    @FXML
    private JFXTextField tf_buyprice;
    @FXML
    private JFXTextField tf_sellprice;
    @FXML
    private TableColumn<DrugReport, String> cloumn_qty;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList(
        
        
        
        );
        
        
        
        cloumn_name.setCellValueFactory(new PropertyValueFactory<DrugReport, String>("name"));
        cloumn_code.setCellValueFactory(new PropertyValueFactory<DrugReport, String>("barcode"));
        cloumn_qty.setCellValueFactory(new PropertyValueFactory<DrugReport, String>("qty"));
        cloumn_date.setCellValueFactory(new PropertyValueFactory<DrugReport, String>("date"));
        cloumn_select.setCellValueFactory(new PropertyValueFactory<DrugReport, String>("select"));
        cloumn_qty.setStyle("-fx-alignment: CENTER;");
        cloumn_select.setStyle("-fx-alignment: CENTER;");
        cloumn_date.setStyle("-fx-alignment: CENTER;");
        
        tf_name.setText("Sun Pharmacy");
        tf_address.setText("1801 Texas, USA");
        tf_phone.setText("1800-8485-5211");
        tf_buyprice.setText("15000");
        tf_sellprice.setText("49000");
        
        
        table_drug.setItems(data);
    }    

    @FXML
    private void deleteAction(ActionEvent event) {
        
        delete();
    }
    
    private void delete(){
        ObservableList<DrugReport> dataForDelete = FXCollections.observableArrayList();
        
        for (DrugReport bean : data){
            if(bean.getSelect().isSelected())
            {
                dataForDelete.add(bean);
            }
        }
        
        data.removeAll(dataForDelete);
    
    }
    
    
    
    public static class DrugReport{
        private final  SimpleStringProperty name;
        private final  SimpleStringProperty barcode;
        private final  SimpleStringProperty qty;
        private final  SimpleStringProperty date;
        private CheckBox select;
        
        
        DrugReport(String fname,String fbarcode,String fqty,String fdate){
        this.name = new SimpleStringProperty(fname);
        this.barcode = new SimpleStringProperty(fbarcode);
        this.qty = new SimpleStringProperty(fqty);
        this.date =  new SimpleStringProperty(fdate);
        this.select = new CheckBox();      
        
        }
        
        public String getName(){
            return name.get();
        }
        
        public void setName(String fname){
            name.set(fname);
        }
        
        public String getBarcode(){
            return barcode.get();
        }
        
        public void setBarcode(String fbarcode){
            barcode.set(fbarcode);
        }
        
        public String getQty(){
            return qty.get();
        }
        
        public void setQty(String fqty){
            qty.set(fqty);
        }
        
        public String getDate(){
            return date.get();
        }
        
        public void setDate(String fdate){
            date.set(fdate);
        }
        
        public CheckBox getSelect(){
            return select;
        }
        
        public void setSelect(CheckBox select){
           this.select = select;
        }
        
        


    }
}
