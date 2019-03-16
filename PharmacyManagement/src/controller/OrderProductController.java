/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.*;

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
    private TextField tf_invoiceID;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

//    int no = 0;
//    int productId = 0;
//    String barcode = null;
//    String productName = null;
//    double priceOut = 0.0;
//    int qty = 0;
//    double amount = 0.0;
//    private double grandTotal = 0.0;
    
      private ObservableList<OrderList> orderData;
      private ObservableList<ProductListForSearchInInvoice> searchData;

  
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
    private TableView<ProductListForSearchInInvoice> table_search;
    @FXML
    private TableColumn<ProductListForSearchInInvoice, String> column_search_productname;
    @FXML
    private TableColumn<ProductListForSearchInInvoice, String> column_search_barcode;

  

    @FXML
    private Button btn_add2;
    @FXML
    private TableView<OrderList> table_order;
    @FXML
    private JFXTextField tf_search;

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
        searchData = FXCollections.observableArrayList(); 
        column_search_productname.setCellValueFactory(new PropertyValueFactory<>("productname"));
        column_search_barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
            tf_search.setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    doSearchAction();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                       }
        });

        try {
            doSearchAction();
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //        orderData = FXCollections.observableArrayList();

//        column_invoice_no.setCellValueFactory(new PropertyValueFactory<>("no"));
//        column_invoice_productid.setCellValueFactory(new PropertyValueFactory<>("productId"));
//        column_invoice_barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
//        column_invoice_productname.setCellValueFactory(new PropertyValueFactory<>("productName"));
//        column_invoice_priceout.setCellValueFactory(new PropertyValueFactory<>("priceOut"));
//        column_invoice_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
//        column_invoice_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }
    
 

    
    public void doSearchAction() throws SQLException {
        
        searchData.clear();
        table_search.setItems(searchData);
        System.out.println(tf_search.getText());
  

        try {
            pst = con.prepareStatement("select barcode,productname from products where productname LIKE ?");
            pst.setString(1, "%" + tf_search.getText() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
//                searchData.add(new ProductList(rs.getInt("pid"),rs.getString("barcode"), rs.getString("productname"),""+ rs.getDouble("priceIn"),""+rs.getDouble("priceOut")));
                searchData.add(new ProductListForSearchInInvoice(rs.getString("barcode"), rs.getString("productname")));
                 table_search.setItems(searchData);

//                        searchData.add(new ProductList2(rs.getString("barcode"), rs.getString("productname"), "" + rs.getDouble("priceIn"), "" + rs.getDouble("priceOut")));
//                        table_search.setItems(searchData);
            }
            
            rs.close();

            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
                
    
    @FXML
    public void action_add(ActionEvent event) throws SQLException {
//        scanbarcode();
//doSearchAction();
    }

    private void clearText() {
        tf_barcode.clear();
        tf_barcode.requestFocus();
        tf_productname.clear();
        tf_price.clear();
        tf_qty.clear();

    }

    private void scanbarcode() throws SQLException {
//        pst = con.prepareStatement("Select * from products where barcode = ?");
//        pst.setString(1, tf_barcode.getText());
//        rs = pst.executeQuery();
//        if (rs.next()) {
//            productId = rs.getInt("pid");
//            barcode = rs.getString("barcode");
//            productName = rs.getString("productname");
//            priceOut = rs.getDouble("priceout");
//            tf_productname.setText(productName);
//            tf_price.setText("" + priceOut);
//            tf_qty.requestFocus();
//        }
//        rs.close();
    }

    @FXML
    private void action_addtomenu(ActionEvent event) {
//        qty = Integer.parseInt(tf_qty.getText());
//        System.out.println(qty);
//        if (qty != 0) {
//            amount = priceOut * qty;
//            grandTotal += amount;
//
////            for (OrderList item : orderData) {
////                if (item.getProductId() == productId) {
////                    int table_qty = item.getQty() + qty;
////                    double table_amount = item.getAmount() + amount;
////                    item.setQty(table_qty);
////                    item.setAmount(table_amount);
////                    lb_total.setText("" + grandTotal);
////                    table_order.getItems().set(table_order.getItems().indexOf(item), item);
////                    clearText();
////                    return;
////                }
////            }
//            orderData.add(new OrderList(++no, productId, barcode, productName, priceOut, qty, amount));
//            table_order.setItems(orderData);
//            lb_total.setText("" + grandTotal);
//            clearText();
//        } else {
//            AlertDialog.display("Info", "Hey, where is my qty ?");
//        }
    }

}
