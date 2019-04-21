/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import static controller.MainController.infoUser;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import javax.imageio.ImageIO;
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

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
    private PreparedStatement pst2;
    private PreparedStatement pst3;
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
    int returnCuID = 0;
    int returnUserID = 0;
    @FXML
    private Button btn_remove;
    @FXML
    private TextField tf_customer;

    int UserIDReal;
    String returnTempOrderID;

    int temp_qty = 0;

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
        try {
            UserIDReal = getUsersID();
        } catch (SQLException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        }


        tf_productname.setDisable(true);
        tf_price.setDisable(true);
        tf_invoiceID.setDisable(true);
        order_dateInvoice.setDisable(true);


//        tf_productname.setDisable(true);
//        tf_price.setDisable(true);
//        tf_invoiceID.setDisable(true);
//        order_dateInvoice.setDisable(true);


        


        error_qty.setStyle("-fx-text-fill: red;");
        tf_invoiceID.setText(autoOrderID());
        order_dateInvoice.setValue(LocalDate.now());
        searchData = FXCollections.observableArrayList();
        column_search_productname.setCellValueFactory(new PropertyValueFactory<>("pname"));
        column_search_barcode.setCellValueFactory(new PropertyValueFactory<>("code"));

//        tf_search.setOnKeyReleased((KeyEvent ke) -> {
//            if (ke.getCode().equals(KeyCode.ENTER)) {
//                try {
//                    doSearchAction();
//                } catch (SQLException ex) {
//                    Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//        });
        tf_search.setOnKeyReleased((KeyEvent ke) -> {

            try {
                searchData.clear();
                table_search.setItems(searchData);
                doSearchAction();
            } catch (SQLException ex) {
                Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        tf_qty.setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    addToMenu();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
                }
                tf_barcode.requestFocus();

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

        ArrayList<String> resultBarcode;
        try {
            resultBarcode = autoFillBarcode();
            TextFields.bindAutoCompletion(tf_barcode, resultBarcode);
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

//        searchData.clear();
//        table_search.setItems(searchData);
        System.out.println(tf_search.getText());

        searchData.clear();
        table_search.setItems(searchData);
//        System.out.println(tf_search.getText());

        try {

            pst = con.prepareStatement("select PCode,PName from Product where pname LIKE ?");

            pst.setString(1, "%" + tf_search.getText() + "%");
            rs = pst.executeQuery();

            while (rs.next()) {
//                searchData.add(new ProductList(rs.getInt("pid"),rs.getString("barcode"), rs.getString("productname"),""+ rs.getDouble("priceIn"),""+rs.getDouble("priceOut")));

                searchData.add(new ProductListForSearchInInvoice(rs.getString("PCode"), rs.getString("PName")));

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
        autoFillWithBarcode();

    }

    private void clearText() {
        tf_barcode.clear();
        tf_productname.clear();
        tf_price.clear();
        tf_qty.clear();

    }

    public ArrayList<String> autoFillBarcode() throws SQLException {
        ArrayList<String> barcodeList = new ArrayList<>();
        pst = con.prepareStatement("select PCode from Product");
        rs = pst.executeQuery();
        while (rs.next()) {
            barcodeList.add(rs.getString("PCode"));

        }
        rs.close();
        pst.close();

        return barcodeList;
    }

    public ArrayList<String> autoFillCustomer() throws SQLException {
        ArrayList<String> customerList = new ArrayList<>();
        pst = con.prepareStatement("select CuName,CuPhone from Customer");
        rs = pst.executeQuery();
        while (rs.next()) {
            customerList.add(rs.getString(1) + " " + rs.getString(2));
        }
        rs.close();
        pst.close();

        return customerList;
    }

    public void addToMenu() throws SQLException {

        boolean isQtyTrue = ValidationController.isQtySuitable(tf_qty, error_qty, "Qty isn't suitable");
//        boolean isProductEnough = checkQtyProduct();

        if (isQtyTrue) {
            qty = Integer.parseInt(tf_qty.getText());
            boolean checkQty = checkQtyProduct();
            if (checkQty) {
                if (qty != 0) {

                    amount = price * qty;
                    grandTotal += amount;

                    for (OrderList2 item : orderData) {

                        if (ValidationController.sosanhchuoi(barcode, item.getBarcode())) {
                            int table_qty = item.getQty() + qty;
                            boolean checkQty2 = checkQtyProduct_2(table_qty);
                            if (checkQty2) {
                                double table_amount = item.getAmount() + amount;
                                item.setQty(table_qty);
                                item.setAmount(table_amount);
                                lb_total.setText("" + grandTotal);
                                table_order.getItems().set(table_order.getItems().indexOf(item), item);
                                clearText();
                                return;
                            } else {
                                return;
                            }

                        }

                    }

                }
                orderData.add(new OrderList2(++no, productId, barcode, productname, price, qty, amount));
                table_order.setItems(orderData);
                lb_total.setText("" + grandTotal);

                clearText();
            }

        } else {
            AlertDialog.display("Info", "Some information is wrong . Please check !!!");
        }

    }

    private boolean checkQtyProduct() throws SQLException {
        boolean check = false;
        pst = con.prepareStatement("select totalqty from stock where PCode like ?");
        pst.setString(1, tf_barcode.getText());
        rs = pst.executeQuery();
        if (rs.next()) {
            temp_qty = rs.getInt(1);
            if (temp_qty < qty) {
                AlertDialog.display("Info", "The qty which in warehouse is not enough to order !!!");
            } else {
                check = true;
            }

        } else {
            AlertDialog.display("Info", "No qty in this drug yet. Please choose another drugs");
        }

        return check;
    }

    private boolean checkQtyProduct_2(int qty_in) throws SQLException {
        boolean check = false;
        pst = con.prepareStatement("select totalqty from stock where PCode like ?");
        pst.setString(1, tf_barcode.getText());
        rs = pst.executeQuery();
        if (rs.next()) {
            temp_qty = rs.getInt(1);
            if (temp_qty < qty_in) {
                AlertDialog.display("Info", "Your updated qty of this drug is not enough to order.");
            } else {
                check = true;
            }

        } else {
            AlertDialog.display("Info", "No qty in this drug yet. Please choose another drugs");
        }
        return check;
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
        pst.close();

    }

    @FXML
    private void action_addtomenu(ActionEvent event) throws SQLException {

        addToMenu();

    }

    @FXML

    private void action_printInvoice(ActionEvent event) throws IOException {

//        boolean check_barcode = ValidationController.isTextFieldHavingText(tf_barcode);
//        boolean check_productname = ValidationController.isTextFieldHavingText(tf_productname);
//        boolean check_qty = ValidationController.isTextFieldHavingText(tf_qty);
//        boolean check_price = ValidationController.isTextFieldHavingText(tf_price);
//AlertDialog.display("Info", "May be some fields are missing, just check !!!");
        try {
            Invoice();
            returnTempOrderID = tf_invoiceID.getText();
            tf_invoiceID.setText(autoOrderID());
            tf_customer.clear();
//                printInvoice();

        } catch (SQLException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getCuId() throws SQLException {

        int cuid = 1;
        pst = con.prepareStatement("Select CuId from Customer where CuName like ? and CuPhone like ?");
        pst.setString(1, ValidationController.getStringFromText(tf_customer.getText()));
        pst.setString(2, ValidationController.getNumberFromText(tf_customer.getText()));
        rs = pst.executeQuery();
        if (rs.next()) {
            cuid = rs.getInt("CuId");
        }
        rs.close();

        return cuid;

    }

    public int getUsersID() throws SQLException {
        int userID = 0;
        pst = con.prepareStatement("select UsersID from Users where UsersName like ?");
        pst.setString(1, "%" + UserCurrentLogin.getCurrentLogin() + "%");
        rs = pst.executeQuery();
        if (rs.next()) {
            userID = rs.getInt("UsersID");
        }
        rs.close();

        return userID;
    }

    public int getDetailID() throws SQLException {
        int detailID = 1;
        pst = con.prepareStatement("select DetailID from DetailUser where UsersID = ?");
        pst.setInt(1, UserIDReal);

        rs = pst.executeQuery();
        if (rs.next()) {
            detailID = rs.getInt(1);
        }
        rs.close();
        return detailID;
    }

    public void Invoice() throws SQLException, IOException {

//        if (getUsersID() != 0) {
        String sql = "insert into Orders (OrderID,OrderDate,AmountTotal,CuId,UsersID)values(?,?,?,?,?)";
        String sql2 = "Update Customer  set MoneySpend +=? where CuId = ?";
        String sql3 = "Update DetailUser set MoneySold +=? where DetailID= ?";
        if (orderData.isEmpty()) {
            AlertDialog.display("Info", "May be some fields are missing, just check !!!");
        } else {
            try {
                returnCuID = getCuId();
                returnUserID = getUsersID();
                pst = con.prepareStatement(sql);
                pst.setString(1, tf_invoiceID.getText());
                pst.setDate(2, java.sql.Date.valueOf(order_dateInvoice.getValue()));
                pst.setDouble(3, grandTotal);
                pst.setInt(4, returnCuID);
                pst.setInt(5, returnUserID);

                int i = pst.executeUpdate();
                if (i == 1) {
                    sql = "Insert into OrderDetail(OrderID,PId,Qty,SellPrice,Amount)values(?,?,?,?,?)";
                    for (OrderList2 item : orderData) {
                        pst = con.prepareStatement(sql);
                        pst.setString(1, tf_invoiceID.getText());
                        pst.setInt(2, item.getPid());
                        pst.setInt(3, item.getQty());
                        pst.setString(4, "" + item.getPriceOut());
                        pst.setDouble(5, item.getAmount());
                        pst.executeUpdate();

                    }

                    if (getCuId() != 0) {
                        pst2 = con.prepareStatement(sql2);
                        pst2.setDouble(1, grandTotal);
                        pst2.setInt(2, returnCuID);
                        int j = pst2.executeUpdate();

                        pst2.close();
                    }

                    if (getDetailID() != 0) {
                        pst2 = con.prepareStatement(sql3);
                        pst2.setDouble(1, grandTotal);
                        pst2.setInt(2, getDetailID());
                        int k = pst2.executeUpdate();

                        pst2.close();

                    }

                    AlertDialog.display("Info", "Data added into order success !!!");

                    clearText();
                    returnTempOrderID = tf_invoiceID.getText();
                    orderData.clear();
                    table_order.setItems(orderData);

                    printInvoice();

                }

//để đây nó set rỗng tài liệu nhé ông
//                tf_invoiceID.setText(autoOrderID());
            } catch (SQLException ex) {
                Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
//        } else {
//            AlertDialog.display("Warning", "Please check your user 's condition or relogin user !!!");
//
//        }   // Phai thay khi update

//        printInvoice();
    }

    private String autoOrderID() {

        String orderID = "Order00000";

        try {
            pst = con.prepareStatement("select max(OrderID) from Orders");
            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getString(1) == null) {
                    orderID = "Order00000";
                } else {
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

//report
    private void printInvoice() throws IOException {

        String souceFile = "src/report/invoice.jrxml";
        String urlImage = "/image/hyhy.png";
        try {
            Connection connection = controller.ConnectDB.connectSQLServer();
            JasperReport jr = JasperCompileManager.compileReport(souceFile);

            Map<String, Object> params = new HashMap<String, Object>();
            BufferedImage image = ImageIO.read(getClass().getResource("/image/hyhy.png"));
            params.put("image", image);
            params.put("logo", this.getClass().getResourceAsStream(urlImage));
            params.put("Cashier", UserCurrentLogin.getCurrentLogin());
            params.put("Customer", tf_customer.getText()); //tf_customer.getText()
            params.put("OrderID", tf_invoiceID.getText()); //tf_invoiceID.getText()
            params.put("Total", String.valueOf(grandTotal));

            JasperPrint jp = JasperFillManager.fillReport(jr, params, connection);
            JasperViewer jv = new JasperViewer(jp, false);

            jv.setVisible(true);
            jv.setTitle("Bill");

        } catch (ClassNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(OrderProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void mission() {
        infoUser = LoginController.ListUserLogin;
        for (User user : infoUser) {
            if (user.getMission().equals("") && user.getDeparment().equals("")
                    || user.getDeparment().equals("Warehouse")
                    || user.getMission().equals("User") && user.getDeparment().equals("Business")) {
                btn_addtomenu.setDisable(true);
                btn_printInvoice.setDisable(true);
            } else {
                btn_addtomenu.setDisable(false);
                btn_printInvoice.setDisable(false);
            }
        }
    }

        
    //            params.put("Cashier", "aaa"); //UserCurrentLogin.getCurrentLogin()
//            System.out.println(UserCurrentLogin.getCurrentLogin());
//            System.out.println(tf_invoiceID.getText());
//            params.put("Customer", "aaaa"); //ValidationController.getStringFromText(tf_customer.getText()
//            params.put("OrderID", "Order00000");
//            params.put("Total", "123123");

    
    


}
