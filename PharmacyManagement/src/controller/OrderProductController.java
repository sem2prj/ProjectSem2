/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class OrderProductController implements Initializable {

    @FXML
    private JFXDatePicker order_dateInvoice;
    @FXML
    private TextField tf_barcode;
    @FXML
    private TextField tf_productname;
    @FXML
    private TextField tf_qty;
    @FXML
    private TextField tf_price;
    @FXML
    private Button btn_add;
 
    @FXML
    private Label lb_total;
    @FXML
    private Button btn_remove;
    @FXML
    private TextField tf_invoiceID;
       
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    
    
    int no = 0;
    int productId = 0;
    String barcode = "";
    String productName = "";
    double priceOut = 0.0;
    int qty = 0;
    double amount = 0.0;
    private double grandTotal = 0.0;
    
    @FXML
    private TableView<OrderList> table_order;
    @FXML
    private TableColumn<OrderList, Integer> column_invoice_no;
    @FXML
    private TableColumn<OrderList, Integer> column_invoice_productid;
    @FXML
    private TableColumn<OrderList, String> column_invoice_barcode;
    @FXML
    private TableColumn<OrderList, String> column_invoice_productname;
    @FXML
    private TableColumn<OrderList, Double> column_invoice_priceout;
    @FXML
    private TableColumn<OrderList, Integer> column_invoice_qty;
    @FXML
    private TableColumn<OrderList, Double> column_invoice_amount;
    @FXML
    private TableView<?> table_search;
    @FXML
    private TableColumn<?, ?> column_search_productname;
    @FXML
    private TableColumn<?, ?> column_search_barcode;
    
    private ObservableList<OrderList> orderData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        order_dateInvoice.setValue(LocalDate.now());
        
        orderData = FXCollections.observableArrayList();
        
        column_invoice_no.setCellValueFactory(new PropertyValueFactory<>("no"));
        column_invoice_productid.setCellValueFactory(new PropertyValueFactory<>("productId"));
        column_invoice_barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        column_invoice_productname.setCellValueFactory(new PropertyValueFactory<>("productName"));
        column_invoice_priceout.setCellValueFactory(new PropertyValueFactory<>("priceOut"));
        column_invoice_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        column_invoice_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        
        
    }    

    @FXML
    private void action_add(ActionEvent event) throws SQLException {
        pst = con.prepareStatement("Select * from products where barcode = ?");
        pst.setString(1, tf_barcode.getText());
        rs = pst.executeQuery();
               if(rs.next()){
                    productId = rs.getInt("pid");
                    barcode = rs.getString("barcode");
                    productName = rs.getString("productname");
                    priceOut = rs.getDouble("priceout");
                    tf_productname.setText(productName);
                    tf_price.setText(""+priceOut);
                    tf_qty.requestFocus();
               }
               rs.close();
    }

    @FXML
    private void handle_order(ActionEvent event) {
        qty = Integer.parseInt(tf_qty.getText());
        if(qty!=0){
            amount = priceOut * qty;
            grandTotal += amount;
            
            for (OrderList item : orderData){
                if(item.getProductId()==productId){
                    int table_qty = item.getQty()+ qty;
                    double table_amount = item.getAmount() + amount;
                    item.setQty(table_qty);
                    item.setAmount(table_amount);
                    lb_total.setText(""+grandTotal);
                    table_order.getItems().set(table_order.getItems().indexOf(item), item);
                    clearText();
                    return;
                }
            }
            orderData.add(new OrderList(++no, productId, barcode, productName, priceOut, qty, amount));
            table_order.setItems(orderData);
            lb_total.setText(""+grandTotal);
            clearText();
        }
        else{
            AlertDialog.display("Info", "Hey, where is my qty ?");
        }
        
    }
    
        private void clearText(){
        tf_barcode.clear();
        tf_barcode.requestFocus();
        tf_productname.clear();
        tf_price.clear();
        tf_qty.clear();
        
    }
    
    
    
    
    
}
