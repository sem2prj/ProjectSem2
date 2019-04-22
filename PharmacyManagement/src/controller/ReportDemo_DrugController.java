/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import com.jfoenix.controls.JFXTextField;
import static controller.MainController.infoUser;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.AnchorPane;
import model.User;

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
    
    private Connection con;
    private PreparedStatement pst;
    private PreparedStatement pst2;
    private ResultSet rs;
    @FXML
    private AnchorPane anchorpane;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
           
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportDemo_DrugController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportDemo_DrugController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mission();
        
        data = FXCollections.observableArrayList();
        
               
        cloumn_name.setCellValueFactory(new PropertyValueFactory<DrugReport, String>("name"));
        cloumn_code.setCellValueFactory(new PropertyValueFactory<DrugReport, String>("barcode"));
        cloumn_qty.setCellValueFactory(new PropertyValueFactory<DrugReport, String>("qty"));
        cloumn_date.setCellValueFactory(new PropertyValueFactory<DrugReport, String>("date"));
        cloumn_select.setCellValueFactory(new PropertyValueFactory<DrugReport, String>("select"));
        cloumn_qty.setStyle("-fx-alignment: CENTER;");
        cloumn_select.setStyle("-fx-alignment: CENTER;");
        cloumn_date.setStyle("-fx-alignment: CENTER;");
        
        tf_name.setText("Aptech Education");
        tf_address.setText("38 Yen Bai, Danang");
        tf_phone.setText("0236.3.779.779"); //02363779779
        tf_buyprice.setText("tuyensinh@softech.vn");
        tf_sellprice.setText("2019");
        
        try {
            allTimeAction();
        } catch (SQLException ex) {
            Logger.getLogger(ReportDemo_DrugController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table_drug.setItems(data);
    }    

    @FXML
    private void deleteAction(ActionEvent event) throws SQLException {
        
        delete();
    }
    
    private void delete() throws SQLException{
        ObservableList<DrugReport> dataForDelete = FXCollections.observableArrayList();
        
        for (DrugReport bean : data){
            if(bean.getSelect().isSelected())
            {
                dataForDelete.add(bean);
                String sql = "update stockdetail set status =1 where stockdetailID = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, Integer.valueOf(bean.getStockdetailid()));
                int i = pst.executeUpdate();
                
                if(i==1){
                    System.out.println("Tat thong bao thanh cong");
                }
                        
                
                
            }
        }
        
        data.removeAll(dataForDelete);
    
    }
    
    private void allTimeAction() throws SQLException {
        String sql = "select stock.PCode,stockdetail.qty,stockdetail.drugexdate,stockdetail.stockdetailID   \n"
                + "from stock\n"
                + "INNER JOIN stockdetail\n"
                + "on stock.stockID = stockdetail.stockID";
        
        pst = con.prepareStatement(sql);
        rs= pst.executeQuery();
        while(rs.next()){
            data.add(new DrugReport(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
        }
        for(DrugReport item : data){
            pst = con.prepareStatement("select PName from Product where PCode like ?");
            pst.setString(1,item.getBarcode());
            rs= pst.executeQuery();
            if(rs.next()){
                item.setName(rs.getString(1));
            }
        
        }
    
    }
    
     private void mission() {
        infoUser = LoginController.ListUserLogin;
        for (User user : infoUser) {
            if (user.getMission().equals("") && user.getDeparment().equals("")||user.getDeparment().equals("Business")&&user.getMission().equals("User")) {
                button.setDisable(true);
                button.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                data.clear();
            }else{
                button.setDisable(false);
            }
        }
     }
    
    
    public static class DrugReport{
        private final  SimpleStringProperty name;
        private final  SimpleStringProperty barcode;
        private final  SimpleStringProperty qty;
        private final  SimpleStringProperty date;
        private final  SimpleStringProperty stockdetailid;
        private CheckBox select;
        
        
        DrugReport(String fbarcode,String fqty,String fdate,String fstockdetailid){
        this.name = new SimpleStringProperty("");
        this.barcode = new SimpleStringProperty(fbarcode);
        this.qty = new SimpleStringProperty(fqty);
        this.date =  new SimpleStringProperty(fdate);
        this.stockdetailid = new SimpleStringProperty(fstockdetailid);
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
        
        public String getStockdetailid(){
            return stockdetailid.get();
        }
        
        public void setStockdetailid(String fstockdetailid){
            stockdetailid.set(fstockdetailid);
        }
        
        public CheckBox getSelect(){
            return select;
        }
        
        public void setSelect(CheckBox select){
           this.select = select;
        }
        
        


    }
}
