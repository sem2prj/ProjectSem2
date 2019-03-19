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

    private ObservableList<OrderList2> orderData;
    private ObservableList<ProductListForSearchInInvoice> searchData;
    @FXML
    private TableView<OrderList2> table_order;

    @FXML
    private TableColumn<OrderList2, Integer> column_invoice_no;
    @FXML
    private TableColumn<OrderList2, String> column_invoice_barcode;
    @FXML
    private TableColumn<OrderList2, String> column_invoice_productname;
    @FXML
    private TableColumn<OrderList2, Double> column_invoice_priceout;
    @FXML
    private TableColumn<OrderList2, Integer> column_invoice_qty;
    @FXML
    private TableColumn<OrderList2, Double> column_invoice_amount;

    @FXML
    private TableView<ProductListForSearchInInvoice> table_search;
    @FXML
    private TableColumn<ProductListForSearchInInvoice, String> column_search_productname;
    @FXML
    private TableColumn<ProductListForSearchInInvoice, String> column_search_barcode;

    @FXML
    private JFXTextField tf_search;

    int no = 0;
    int productId;
    String barcode;
    String productname;
    double price;
    int qty;
    double amount;
    private double grandTotal;
    @FXML
    private Label error_qty;

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
        column_search_productname.setCellValueFactory(new PropertyValueFactory<>("pname"));
        column_search_barcode.setCellValueFactory(new PropertyValueFactory<>("code"));

        tf_search.setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    doSearchAction();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        tf_barcode.setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    autoFillWithBarcode();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });



//        orderData = FXCollections.observableArrayList();
//        column_invoice_no.setCellValueFactory(new PropertyValueFactory<>("no"));
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
            

            pst = con.prepareStatement("select code,pname from Product where pname LIKE ?");
            pst.setString(1, "%" + tf_search.getText() + "%");
            rs = pst.executeQuery();

            while (rs.next()) {
//                searchData.add(new ProductList(rs.getInt("pid"),rs.getString("barcode"), rs.getString("productname"),""+ rs.getDouble("priceIn"),""+rs.getDouble("priceOut")));
                searchData.add(new ProductListForSearchInInvoice(rs.getString("Code"), rs.getString("PName")));
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

        boolean isBarcodeHavingText = ValidationController.isTextFieldNotEmpty(tf_barcode);
        boolean isProductNameHavingText = ValidationController.isTextFieldNotEmpty(tf_productname);
        boolean isQtyHavingText = ValidationController.isTextFieldNotEmpty(tf_qty);
        boolean isPriceHavingText = ValidationController.isTextFieldNotEmpty(tf_price);
        boolean IsQtyNotNegative = ValidationController.isIntegerValueNegative(tf_qty, error_qty, "qty can't be negative");
        boolean isQtyOver1000 = ValidationController.isQtyOver1000(tf_qty, error_qty, "qty can't be over 1000");

        if (isBarcodeHavingText && isProductNameHavingText && isQtyHavingText && isPriceHavingText && IsQtyNotNegative) {
            if (isQtyOver1000) {
                qty = Integer.parseInt(tf_qty.getText());
                if (qty != 0) {
                    amount = price * qty;
                    grandTotal += amount;

                    for (OrderList2 item : orderData) {
                        String str1 = item.getBarcode();

                        if (ValidationController.sosanhchuoi(barcode, str1)) {
                            int table_qty = item.getQty() + qty;
                            double table_amount = item.getAmount() + amount;
                            item.setQty(table_qty);
                            item.setAmount(table_amount);
                            lb_total.setText("" + grandTotal);
                            table_order.getItems().set(table_order.getItems().indexOf(item), item);
                            clearText();
                            return;

                        }

                    }

                    orderData.add(new OrderList2(++no, barcode, productname, price, qty, amount));
                    table_order.setItems(orderData);

                }

                clearText();
            } else {
                AlertDialog.display("Info", "Some field is missing !!!");
            }
        }

    }

    private void clearText() {
        tf_barcode.clear();

        tf_productname.clear();
        tf_price.clear();
        tf_qty.clear();

    }

    public void autoFillWithBarcode() throws SQLException {
        pst = con.prepareStatement("Select * from Product where code = ?");
        pst.setString(1, tf_barcode.getText());
        rs = pst.executeQuery();
        if (rs.next()) {
            barcode = tf_barcode.getText();
            tf_productname.setText(rs.getString("PName"));
            productname = tf_productname.getText();
            tf_price.setText(rs.getString("SellPrice"));
            price = Double.parseDouble(tf_price.getText());
            tf_qty.requestFocus();

        }
        rs.close();
    }

}
