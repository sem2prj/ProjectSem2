/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Customer;
import model.CustomerDAOImplement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class CustomerController implements Initializable {

    @FXML
    private TableView<Customer> tableView;
    @FXML
    private TableColumn<Customer, String> columnCode;
    @FXML
    private TableColumn<Customer, String> columnName;
    @FXML
    private TableColumn<Customer, String> columnAddrees;
    @FXML
    private TableColumn<Customer, String> columnPhone;
    @FXML
    private TableColumn<Customer, String> columnEmail;
    @FXML
    private TableColumn<Customer, String> columnLevel;
    @FXML
    private JFXTextField txtCCode;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtAddrees;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtLevel;

    private static ObservableList<Customer> data;

    @FXML
    private Label lbCode;
    @FXML
    private Label lbName;
    @FXML
    private Label lbAddrees;
    @FXML
    private Label lbPhone;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbLevel;
    @FXML
    private JFXTextField txtSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerDAOImplement cDI = new CustomerDAOImplement();
        data = cDI.getAllCustomer();
        loadTable();
        click();
        css();
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        boolean txtLevelnotNumber = controller.ValidationController.isTextFieldTypeNumber(txtLevel, lbLevel, "Level is requied");
        if (!txtLevelnotNumber) {
            txtLevel.requestFocus();
        }

        boolean txtEmailnotEmpty = controller.ValidationController.isEmailSuitable(txtEmail, lbEmail, "Email is requied ,example :voduc204@gmail.com");
        if (!txtEmailnotEmpty) {
            txtEmail.requestFocus();
        }

        boolean txtPhonenotEmpty = controller.ValidationController.isPhoneSuitable(txtPhone, lbPhone, "Phone is requied,example :+84 945 333 666, 0905123456,01233456789");
        if (!txtPhonenotEmpty) {
            txtPhone.requestFocus();
        }
        boolean txtAddreesnotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtAddrees, lbAddrees, "Addrees is requied");
        if (!txtAddreesnotEmpty) {
            txtAddrees.requestFocus();
        }

        boolean txttxtNameEmpty = controller.ValidationController.isTextFieldNotEmpty(txtName, lbName, "Name is requied");
        if (!txttxtNameEmpty) {
            txtName.requestFocus();
        }
        boolean txtCCodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtCCode, lbCode, "Customer code is requied");
        if (!txtCCodenotEmpty) {
            txtCCode.requestFocus();
        }

        if (txtCCodenotEmpty && txttxtNameEmpty && txtAddreesnotEmpty && txtPhonenotEmpty && txtEmailnotEmpty && txtLevelnotNumber) {

            CustomerDAOImplement cDI = new CustomerDAOImplement();
            cDI.insertCustomer(txtCCode.getText(), txtName.getText(), txtAddrees.getText(), txtPhone.getText(), txtEmail.getText(), Integer.parseInt(txtLevel.getText()));
             clear();
            loadTable();
        
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        boolean txtLevelnotNumber = controller.ValidationController.isTextFieldTypeNumber(txtLevel, lbLevel, "Level is requied");
        if (!txtLevelnotNumber) {
            txtLevel.requestFocus();
        }

        boolean txtEmailnotEmpty = controller.ValidationController.isEmailSuitable(txtEmail, lbEmail, "Email is requied ,example :voduc204@gmail.com");
        if (!txtEmailnotEmpty) {
            txtEmail.requestFocus();
        }

        boolean txtPhonenotEmpty = controller.ValidationController.isPhoneSuitable(txtPhone, lbPhone, "Phone is requied,example :+84 945 333 666, 0905123456,01233456789");
        if (!txtPhonenotEmpty) {
            txtPhone.requestFocus();
        }
        boolean txtAddreesnotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtAddrees, lbAddrees, "Addrees is requied");
        if (!txtAddreesnotEmpty) {
            txtAddrees.requestFocus();
        }

        boolean txttxtNameEmpty = controller.ValidationController.isTextFieldNotEmpty(txtName, lbName, "Name is requied");
        if (!txttxtNameEmpty) {
            txtName.requestFocus();
        }
        boolean txtCCodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtCCode, lbCode, "Customer code is requied");
        if (!txtCCodenotEmpty) {
            txtCCode.requestFocus();
        }

        if (txtCCodenotEmpty && txttxtNameEmpty && txtAddreesnotEmpty && txtPhonenotEmpty && txtEmailnotEmpty && txtLevelnotNumber) {

            CustomerDAOImplement cDI = new CustomerDAOImplement();
            cDI.updateCustomer(txtCCode.getText(), txtName.getText(), txtAddrees.getText(), txtPhone.getText(), txtEmail.getText(), Integer.parseInt(txtLevel.getText()));
             clear();
            loadTable();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        boolean txtCCodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtCCode, lbCode, "Customer code is requied");
        if (!txtCCodenotEmpty) {
            txtCCode.requestFocus();
        }
        if (txtCCodenotEmpty) {
            CustomerDAOImplement cDI = new CustomerDAOImplement();
            cDI.deleteCustomer(txtCCode.getText());
             clear();
            loadTable();
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clear();
    }
    
    private void clear(){
        txtCCode.clear();
        txtName.clear();
        txtAddrees.clear();
        txtPhone.clear();
        txtEmail.clear();
        txtLevel.clear();
    }


    private void loadTable() {
        CustomerDAOImplement cDI = new CustomerDAOImplement();
        ObservableList<Customer> listCustomer = cDI.getAllCustomer();
        tableView.setItems(listCustomer);
        columnCode.setCellValueFactory(new PropertyValueFactory<>("CustomerCode"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnAddrees.setCellValueFactory(new PropertyValueFactory<>("Addrees"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        columnLevel.setCellValueFactory(new PropertyValueFactory<>("Level"));
    }

    private void click() {
        tableView.setOnMouseClicked(e -> {
            Customer customer = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
            txtCCode.setText(customer.getCustomerCode());
            txtName.setText(customer.getName());
            txtAddrees.setText(customer.getAddrees());
            txtPhone.setText(customer.getPhone());
            txtEmail.setText(customer.getEmail());
            txtLevel.setText(String.format("%d", customer.getLevel()));
        });
    }

    private void css() {
        lbCode.setStyle("-fx-text-fill:orange");
        lbName.setStyle("-fx-text-fill:orange");
        lbPhone.setStyle("-fx-text-fill:orange");
        lbEmail.setStyle("-fx-text-fill:orange");
        lbAddrees.setStyle("-fx-text-fill:orange");
        lbLevel.setStyle("-fx-text-fill:orange");
    }

    @FXML
    private void handleKeySearch(KeyEvent event) {
        CustomerDAOImplement cDI = new CustomerDAOImplement();
        data = cDI.getAllCustomer();
        FilteredList<Customer> filteredData = new FilteredList<>(data, e -> true);
        txtSearch.setOnKeyReleased(e -> {
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Customer>) customer -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();
                    if (customer.getCustomerCode().contains(newValue)) {
                        return true;
                    } else if (customer.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
//                    else if (customer.getName().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }
                    return false;
                });
            });
            SortedList<Customer> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        });
    }

    private void handleJaspert(ActionEvent event) {

        CustomerDAOImplement cDI = new CustomerDAOImplement();
        data = cDI.getAllCustomer();
        showReport();

    }

    private void showReport() {
        try {

            Connection connection = controller.ConnectDB.connectSQLServer();
            JasperDesign jasperDesign = JRXmlLoader.load("E:\\Java\\ProjectSem2\\PharmacyManagement\\src\\model\\reportCustomer.jrxml");
            String sql = "SELECT * FROM customer";

            JRDesignQuery jrquery = new JRDesignQuery();

            jrquery.setText(sql);

            jasperDesign.setQuery(jrquery);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<String, Object>();

            JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            JRViewer viewer = new JRViewer(JasperPrint);

            viewer.setOpaque(true);

            viewer.setVisible(true);
            System.out.println(JasperPrint.getName());

            JasperExportManager.exportReportToPdfFile(JasperPrint, "D:/test.pdf");
            System.out.println("done");

        } catch (JRException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
