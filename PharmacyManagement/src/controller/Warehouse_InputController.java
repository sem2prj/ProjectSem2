/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import static controller.MainController.infoUser;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.User;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class Warehouse_InputController implements Initializable {

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private TextField tf_drug;
    @FXML
    private TextField tf_qty;
    @FXML
    private TextField tf_amount;

    private ComboBox<String> combobox_congno;
    @FXML
    private TextField tf_already;
    @FXML
    private TextField tf_remain;

    private Connection con;
    private PreparedStatement pst;
    private PreparedStatement pst2;
    private ResultSet rs;
    @FXML
    private JFXComboBox<String> combobox_supplier;
    @FXML
    private JFXComboBox<String> combobox_status;
    @FXML
    private JFXDatePicker date_drug;
    @FXML
    private JFXDatePicker date_Liabilities;
    @FXML
    private Label lb_1;
    @FXML
    private Label lb_2;
    @FXML
    private Label lb_3;

    int pid = 1;
    int stockid = 1;
    String barcode = "";
    String supplier = "";
    int qty = 0;
    double amount = 0;
    double already = 0;
    double remain = 0;
    java.sql.Date dayOfLiabilities = null;

    double cal_amount = 0;
    double cal_already = 0;
    double cal_remain = 0;

    private ObservableList<WarehouseInput> data;

    @FXML
    private Button button;
    @FXML
    private Button button_clear;
    @FXML
    private TableView<WarehouseInput> tableview;
    @FXML
    private TableColumn<WarehouseInput, String> column_barcode;
    @FXML
    private TableColumn<WarehouseInput, String> column_name;
    @FXML
    private TableColumn<WarehouseInput, String> column_supplier;
    @FXML
    private TableColumn<WarehouseInput, String> column_qty;
    @FXML
    private Label lb_drug;
    @FXML
    private Label lb_qty;
    @FXML
    private Label lb_amount;
    @FXML
    private Label lb_already;
    @FXML
    private Label lb_remain;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportDemo_EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportDemo_EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mission();

        ArrayList<String> resultTFDrug;
        try {
            resultTFDrug = autoFillDrugTFByDrugName();
            TextFields.bindAutoCompletion(tf_drug, resultTFDrug);
        } catch (SQLException ex) {
            Logger.getLogger(Warehouse_InputController.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = FXCollections.observableArrayList();

        column_barcode.setCellValueFactory(new PropertyValueFactory<WarehouseInput, String>("barcode"));
        column_name.setCellValueFactory(new PropertyValueFactory<WarehouseInput, String>("name"));
        column_supplier.setCellValueFactory(new PropertyValueFactory<WarehouseInput, String>("supplier"));
        column_qty.setCellValueFactory(new PropertyValueFactory<WarehouseInput, String>("qty"));

        date_drug.setValue(LocalDate.now());
        date_Liabilities.setValue(LocalDate.now());

        combobox_status.setStyle("-fx-alignment: CENTER;");
        combobox_status.getItems().addAll("Pay enough", "Liabilities");
        combobox_status.getSelectionModel().selectFirst();
        combobox_status.getValue();
        comboboxStatusDo();

        tf_amount.setText("0");
        tf_remain.setText("0");
        tf_already.setText("0");

        tf_already.setOnKeyReleased((KeyEvent ke) -> {

            cal_amount = Double.parseDouble(tf_amount.getText());
            cal_already = Double.parseDouble(tf_already.getText());
            if (cal_already > cal_amount) {
                AlertDialog.display("Info", "The money,which u already paying is greater than amount paid money. Please check!!");
                tf_already.setText("0");
            } else {
                cal_remain = cal_amount - cal_already;
                tf_remain.setText(String.valueOf(cal_remain));
            }

        });

        try {
            tableViewDo();
        } catch (SQLException ex) {
            Logger.getLogger(Warehouse_InputController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ArrayList<String> autoFillDrugTFByDrugName() throws SQLException {
        ArrayList<String> TFDrugList = new ArrayList<>();
        pst = con.prepareStatement("select PCode from Product");
        rs = pst.executeQuery();
        while (rs.next()) {
            TFDrugList.add(rs.getString(1));
        }
        return TFDrugList;
    }

    @FXML
    private void action_demo1(MouseEvent event) throws SQLException {
        comboboxSupplierDo();
    }

    private void comboboxSupplierDo() throws SQLException {
//        System.out.println("Doan test la : " + tf_drug.getText());
        combobox_supplier.getItems().clear();
        pst = con.prepareStatement("select Supplier from Product where PCode like ?");
        pst.setString(1, tf_drug.getText());
        rs = pst.executeQuery();

        if (rs.next()) {
            System.out.println(rs.getString(1));
            if (rs.getString(1) == null) {
                combobox_supplier.getItems().addAll("None");
                combobox_supplier.getSelectionModel().selectFirst();
                combobox_supplier.getValue();
            } else {
                combobox_supplier.getItems().add(rs.getString(1));
                combobox_supplier.getSelectionModel().selectFirst();
                combobox_supplier.getValue();
            }

        }

    }

    @FXML
    private void action_comboboxStatus(ActionEvent event) {
        comboboxStatusDo();
    }

    private void comboboxStatusDo() {
        if (combobox_status.getValue() != null) {
            switch (combobox_status.getValue().toLowerCase()) {
                case "pay enough":
                    lb_1.setVisible(false);
                    lb_2.setVisible(false);
                    lb_3.setVisible(false);
                    tf_already.setVisible(false);
                    tf_remain.setVisible(false);
                    date_Liabilities.setVisible(false);
                    break;
                case "liabilities":
                    lb_1.setVisible(true);
                    lb_2.setVisible(true);
                    lb_3.setVisible(true);
                    tf_already.setVisible(true);
                    tf_remain.setVisible(true);
                    date_Liabilities.setVisible(true);
                    break;
                default:
                    lb_1.setVisible(false);
                    lb_2.setVisible(false);
                    lb_3.setVisible(false);
                    tf_already.setVisible(false);
                    tf_remain.setVisible(false);
                    date_Liabilities.setVisible(false);
                    break;
            }

        }
    }

    @FXML
    private void action_import(ActionEvent event) throws SQLException {
        comboboxSupplierDo();
        boolean dk1 = true;
        if (combobox_supplier.getValue().equalsIgnoreCase("none")) {
            dk1 = false;
        };
        boolean dk2 = ValidationController.isTextFieldHavingText(tf_drug, lb_drug, "Barcode must not empty");
        boolean dk3 = ValidationController.isTextFieldHavingText(tf_qty, lb_qty, "Qty must not empty");
        boolean dk4 = ValidationController.isTextFieldHavingText(tf_amount, lb_amount, "U have to pay, right?");
        boolean dk5 = ValidationController.isTextFieldHavingText(tf_already, lb_already, "This field must be filled");
        boolean dk6 = ValidationController.isTextFieldHavingText(tf_remain, lb_remain, "This field must be fille");
        if (dk1 && dk2 && dk3 && dk4 && dk5 && dk6) {
            boolean dk7 = ValidationController.isTextFieldTypeNumber(tf_amount, lb_amount, "This field must be number type only");
            boolean dk8 = ValidationController.isTextFieldTypeNumber(tf_already, lb_already, "This field must be number type only");
            boolean dk9 = ValidationController.isTextFieldTypeNumber(tf_remain, lb_remain, "This field must be number type only");
            boolean dk10 = ValidationController.isTextFieldTypeNumber(tf_qty, lb_qty, "This field must be number type only");
            if (dk7 && dk8 && dk9 & dk10) {
                cal_amount = Double.parseDouble(tf_amount.getText());
                cal_already = Double.parseDouble(tf_already.getText());
                cal_remain = Double.parseDouble(tf_remain.getText());
                if (combobox_status.getValue().equalsIgnoreCase("liabilities")) {
                    if (cal_amount == cal_already + cal_remain) {

                        barcode = tf_drug.getText();
                        pid = getPId();
                        supplier = combobox_supplier.getValue().toString();
                        qty = Integer.parseInt(tf_qty.getText());
                        amount = Double.parseDouble(tf_amount.getText());
                        already = Double.parseDouble(tf_already.getText());
                        remain = Double.parseDouble(tf_remain.getText());
                        if (combobox_status.getValue() != null) {
                            switch (combobox_status.getValue().toLowerCase()) {
                                case "pay enough":
                                    dayOfLiabilities = null;
                                    break;
                                case "liabilities":
                                    dayOfLiabilities = java.sql.Date.valueOf(date_Liabilities.getValue());
                                    break;
                                default:
                                    dayOfLiabilities = null;
                                    break;
                            }

                        }

                        importGoods();

                    } else {
                        AlertDialog.display("Info", "Make sure paid money = already money + remain money. Money can't be wrong,right?");
                    }
                } else {
                    barcode = tf_drug.getText();
                    pid = getPId();
                    supplier = combobox_supplier.getValue().toString();
                    qty = Integer.parseInt(tf_qty.getText());
                    amount = Double.parseDouble(tf_amount.getText());
                    already = Double.parseDouble(tf_already.getText());
                    remain = Double.parseDouble(tf_remain.getText());
                    if (combobox_status.getValue() != null) {
                        switch (combobox_status.getValue().toLowerCase()) {
                            case "pay enough":
                                dayOfLiabilities = null;
                                break;
                            case "liabilities":
                                dayOfLiabilities = java.sql.Date.valueOf(date_Liabilities.getValue());
                                break;
                            default:
                                dayOfLiabilities = null;
                                break;
                        }

                    }

                    importGoods();
                }

            } else {
                AlertDialog.display("Info", "Some fields must be number type only.");
            }
        } else {
            AlertDialog.display("Info", "Some fields must not empty or none.");
        }

//        if(Validation = true) {
//        }
    }
    
    private int getPId() throws SQLException{
        int b = 1;
        pst = con.prepareStatement("select PId from Product where PCode like ?");
        pst.setString(1,barcode );
        rs=pst.executeQuery();
        if(rs.next()){
            b = rs.getInt(1);
        }
        
        return b;
    }
    
    private void importGoods() throws SQLException {

        String sql1 = "select stockID from stock where PCode like ?";
        String sql2 = "insert into stock (PId,PCode,Supplier,totalqty) values(?,?,?,?) ";
        String sql3 = "insert into stockdetail(stockID,qty,amount,already,remain,drugexdate,liabilitiesexdate,stockdetaildate) \n"
                + "values (?,?,?,?,?,?,?,?)";
        String sql4 = "update stock set totalqty += ? where stockid = ? ";

        pst = con.prepareStatement(sql1);
        pst.setString(1, barcode);
        rs = pst.executeQuery();
        if (rs.next()) {
            stockid = rs.getInt(1);
            pst = con.prepareStatement(sql4);
            pst.setInt(1, qty);
            pst.setInt(2, stockid);
            pst.executeUpdate();

            pst = con.prepareStatement(sql3);
            pst.setInt(1, stockid);
            pst.setInt(2, qty);
            pst.setDouble(3, amount);
            pst.setDouble(4, already);
            pst.setDouble(5, remain);
            pst.setDate(6, java.sql.Date.valueOf(date_drug.getValue()));
            pst.setDate(7, dayOfLiabilities);
            pst.setDate(8, java.sql.Date.valueOf(LocalDate.now()));
            pst.execute();
            AlertDialog.display("Info", "Drug has been imported!!!");
            clearText();
            tableViewDo();

        } else {
            pst = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, pid );
            pst.setString(2, barcode);
            pst.setString(3, supplier);
            pst.setInt(4, qty);

            int i = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            Object key = rs.getObject(1);

            if (i == 1) {
                pst = con.prepareStatement(sql3);
                pst.setInt(1, Integer.parseInt(String.valueOf(key)));
                pst.setInt(2, qty);
                pst.setDouble(3, amount);
                pst.setDouble(4, already);
                pst.setDouble(5, remain);
                pst.setDate(6, java.sql.Date.valueOf(date_drug.getValue()));
                pst.setDate(7, dayOfLiabilities);
                pst.setDate(8, java.sql.Date.valueOf(LocalDate.now()));
                pst.execute();
                AlertDialog.display("Info", "Drug has been imported!!!");
                clearText();
                tableViewDo();
            }

        }

    }

    private void clearText() throws SQLException, SQLException {
        tf_drug.clear();
        comboboxSupplierDo();
        tf_qty.clear();
        tf_amount.clear();
        tf_already.clear();
        tf_remain.clear();
        date_drug.setValue(LocalDate.now());
        date_Liabilities.setValue(LocalDate.now());
        combobox_status.getItems().clear();
        combobox_status.getItems().addAll("Pay enough", "Liabilities");
        combobox_status.getSelectionModel().selectFirst();
        combobox_status.getValue();

    }

    @FXML
    private void action_clear(ActionEvent event) throws SQLException, SQLException {
        clearText();
//        comboboxSupplierDo();

    }

    private void tableViewDo() throws SQLException {
        String sql_tableview = "select PCode,Supplier,totalqty from stock";
        pst = con.prepareStatement(sql_tableview);
        rs = pst.executeQuery();
        data.clear();
        while (rs.next()) {
            data.add(new WarehouseInput(rs.getString(1), rs.getString(2), rs.getString(3)));

        }

        for (WarehouseInput item : data) {
            String sql = "select PName from Product where PCode like ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, item.getBarcode());
            rs = pst.executeQuery();
            if (rs.next()) {
                item.setName(rs.getString(1));
            }
        }

        tableview.setItems(data);

    }
    
    
    private void mission() {
        infoUser = LoginController.ListUserLogin;
        for (User user : infoUser) {
            if (user.getMission().equals("") && user.getDeparment().equals("")||user.getDeparment().equals("Sell")) {
                button.setDisable(true);
                button.setStyle("-fx-background-color: #393535; -fx-text-fill: white;");
                data.clear();
            }else{
                button.setDisable(false);
            }
        }
    }

    public static class WarehouseInput {

        private final SimpleStringProperty barcode;
        private final SimpleStringProperty name;
        private final SimpleStringProperty supplier;
        private final SimpleStringProperty qty;

        WarehouseInput(String fbarcode, String fsupplier, String fqty) {
            this.barcode = new SimpleStringProperty(fbarcode);
            this.name = new SimpleStringProperty("");
            this.supplier = new SimpleStringProperty(fsupplier);
            this.qty = new SimpleStringProperty(fqty);

        }

        public String getBarcode() {
            return barcode.get();
        }

        public void setBarcode(String fbarcode) {
            barcode.set(fbarcode);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String fname) {
            name.set(fname);
        }

        public String getSupplier() {
            return supplier.get();
        }

        public void setSupplier(String fsupplier) {
            supplier.set(fsupplier);
        }

        public String getQty() {
            return qty.get();
        }

        public void setQty(String fqty) {
            qty.set(fqty);
        }

    }
}
