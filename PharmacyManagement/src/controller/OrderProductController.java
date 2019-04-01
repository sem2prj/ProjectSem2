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
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;
import report.Products;

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
    @FXML
    private Button btn_addtomenu;
    @FXML
    private Button btn_printInvoice;
    @FXML
    private Button btn_remove;
    @FXML
    private TextField tf_customer;

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
        error_qty.setStyle("-fx-text-fill: red;");
        tf_invoiceID.setText(autoOrderID());        
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
        
        ArrayList<String> result;
        try {
            result = autoFillCustomer();
            TextFields.bindAutoCompletion(tf_customer, result);
        } catch (SQLException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        orderData = FXCollections.observableArrayList();
        column_invoice_no.setCellValueFactory(new PropertyValueFactory<>("no"));
        column_invoice_barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        column_invoice_productname.setCellValueFactory(new PropertyValueFactory<>("productName"));
        column_invoice_priceout.setCellValueFactory(new PropertyValueFactory<>("priceOut"));
        column_invoice_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        column_invoice_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    public void doSearchAction() throws SQLException {

        searchData.clear();
        table_search.setItems(searchData);
        System.out.println(tf_search.getText());

        try {
            

            pst = con.prepareStatement("select PCode,PName from Product where PName LIKE ?");
            pst.setString(1, "%" + tf_search.getText() + "%");
            rs = pst.executeQuery();

            while (rs.next()) {
//                searchData.add(new ProductList(rs.getInt("pid"),rs.getString("barcode"), rs.getString("productname"),""+ rs.getDouble("priceIn"),""+rs.getDouble("priceOut")));
                searchData.add(new ProductListForSearchInInvoice(rs.getString(1), rs.getString(2)));
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
    autoFillWithBarcode();

//

    }

    private void clearText() {
        tf_barcode.clear();

        tf_productname.clear();
        tf_price.clear();
        tf_qty.clear();

    }
    
    public ArrayList<String> autoFillCustomer() throws SQLException{
        ArrayList<String> customerList = new ArrayList<>();
        pst = con.prepareStatement("select CuName,CuPhone from Customer");
        rs = pst.executeQuery();
        if (rs.next()) {
            customerList.add(rs.getString(1) + " " + rs.getString(2));           
        }
        rs.close();
        pst.close();
        return customerList;
   }

    public void autoFillWithBarcode() throws SQLException {
        pst = con.prepareStatement("Select Pid,PName,SellPrice from Product where PCode = ?");
        pst.setString(1, tf_barcode.getText());
        rs = pst.executeQuery();
        if (rs.next()) {
            productId = rs.getInt("Pid");
            barcode = tf_barcode.getText();
            tf_productname.setText(rs.getString("PName"));
            productname = tf_productname.getText();
            tf_price.setText(rs.getString("SellPrice"));
            price = Double.parseDouble(tf_price.getText());
            tf_qty.requestFocus();

        }
        rs.close();
    }

    @FXML
    private void action_addtomenu(ActionEvent event) {

        boolean isQtyTrue = ValidationController.isQtySuitable(tf_qty, error_qty, "Qty isn't suitable");
        
        

            if (isQtyTrue) {
                qty = Integer.parseInt(tf_qty.getText());
                if (qty != 0) {
                    
                    amount = price * qty;
                    grandTotal += amount;
                    
                    for (OrderList2 item : orderData) {
                      
                          
                        if (ValidationController.sosanhchuoi(barcode, item.getBarcode())) {
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

                    orderData.add(new OrderList2(++no,productId, barcode, productname, price, qty, amount));
                    table_order.setItems(orderData);
                    lb_total.setText("" + grandTotal);

                }

                clearText();
            } else {
                AlertDialog.display("Info", "Some information is wrong . Please check !!!");
            }

        
    } 

    @FXML
    private void action_printInvoice(ActionEvent event) {
        String sql = "insert into Orders (OrderID,OrderDate)values(?,?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, tf_invoiceID.getText() );
            pst.setDate(2, java.sql.Date.valueOf(order_dateInvoice.getValue() ));
            int  i = pst.executeUpdate();
            if(i==1){
                sql = "Insert into OrderDetail(OrderID,PId,Qty,SellPrice)values(?,?,?,?)";
                for(OrderList2 item : orderData){
                    pst = con.prepareStatement(sql);
                    pst.setString(1,tf_invoiceID.getText());
                    pst.setInt(2,item.getPid());
                    pst.setInt(3, item.getQty());
                    pst.setString(4,""+item.getPriceOut());
                    pst.executeUpdate();
                    
                           
                }
            
            }
            AlertDialog.display("Info", "Data added into order success !!!");
//            printInvoice();
        } catch (SQLException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private String autoOrderID(){
//        String orderID = "Order00000";
//        
//        try {
//            pst = con.prepareStatement("select max(OrderID) from Orders");
//            rs = pst.executeQuery();
//            if(rs.next()){
//                orderID = rs.getString(1);
//                int n = Integer.parseInt(orderID.substring(5)) +1 ;
//                int x = String.valueOf(n).length();
//                orderID = orderID.substring(0, 10 -x) + String.valueOf(n);
//                
//            
//            }
//            rs.close();
//            pst.close();
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return orderID;
            String orderID = "Order00000";
            
        try {
            pst = con.prepareStatement("select max(OrderID) from Orders");
            rs = pst.executeQuery();
            
            if (rs.next()) {
                if (rs.getString(1) == null) 
                { orderID = "Order00000";}
                else{
                    orderID = rs.getString(1);
                    
                    int n = Integer.parseInt(orderID.substring(5)) + 1;
                    int x = String.valueOf(n).length();
                    orderID = orderID.substring(0, 10 - x) + String.valueOf(n);
                }
                
            }
            rs.close();
            pst.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return orderID;

  }
    
    private void printInvoice(){
//        String file = "D:\\Git final\\ProjectSem2\\PharmacyManagement\\src\\report\\Invoice2.jrxml";
//        try {
//            JasperReport jr = JasperCompileManager.compileReport(file);
//            HashMap<String, Object >para = new HashMap<>();
////            para.put("cashier","ai do");
//            
//            ArrayList<Products> plist = new ArrayList<>();
//            
//            for (OrderList2 item : orderData){
//                
//            plist.add(new Products(item.getProductName(),""+item.getPriceOut(),""+item.getQty(),""+item.getAmount()));
//            
//                   
//            }
//            JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(plist);
//            JasperPrint jp = JasperFillManager.fillReport(jr,para,jcs);
////            JasperViewer jv= new JasperViewer(jp,false );
//            JasperViewer.viewReport(jp);
////            jv.setVisible(true);
////            JasperViewer.viewReport(jp);
//            
//        } catch (JRException ex) {
//            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
    }

    @FXML
    private void action_removeItem(ActionEvent event) {
        double pricelast = orderData.get(orderData.size() - 1).getPriceOut();
        int qtylast = orderData.get(orderData.size() - 1).getQty();
        grandTotal -= (pricelast * qtylast);
        orderData.remove(orderData.get(orderData.size() - 1));
        table_order.setItems(orderData);
        lb_total.setText("" + grandTotal);
        
    }
} 


