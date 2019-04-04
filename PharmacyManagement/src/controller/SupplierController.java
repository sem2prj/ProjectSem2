/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
import javafx.scene.input.MouseEvent;
import model.Supplier;
import model.SupplierDAOIplement;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class SupplierController implements Initializable {

    @FXML
    private TableView<Supplier> tableView;

    @FXML
    private JFXTextField txtName;
    @FXML
    private Label lbName;
    @FXML
    private JFXTextField txtAddrees;
    @FXML
    private Label lbAddrees;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private Label lbPhone;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private Label lbEmail;
    @FXML
    private JFXTextField txtWebsite;
    @FXML
    private Label lbWebsite;
    @FXML
    private JFXTextField txtNotice;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private Label lbCode;
    @FXML
    private JFXTextField txtTax;
    @FXML
    private Label lbTax;
    @FXML
    private TableColumn<Supplier, String> columnCode;
    @FXML
    private TableColumn<Supplier, String> columnTax;
    @FXML
    private TableColumn<Supplier, String> columnName;
    @FXML
    private TableColumn<Supplier, String> columnAddrees;
    @FXML
    private TableColumn<Supplier, String> columnPhone;
    @FXML
    private TableColumn<Supplier, String> columnEmail;

    private ObservableList<Supplier> supplierData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTable();
        click();
        cssError();
        SupplierDAOIplement sDI = new SupplierDAOIplement();
        supplierData=sDI.getAllSupplier();
        FilteredList<Supplier> filteredData = new FilteredList<>(supplierData, e -> true);
        txtSearch.setOnKeyReleased(e -> {
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Supplier>) supplier -> {
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    if (supplier.getCode().contains(newValue)) {
                        return true;
                    }
                    
                    return false;
                });
            });
            SortedList<Supplier> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        });
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        boolean txtCodeNotEmpty = ValidationController.isTextFieldHavingBarcode(txtCode, lbCode, "Code must be filled out");
        if (!txtCodeNotEmpty) {
            txtCode.requestFocus();
        }
        boolean txtnameNotEmpty = ValidationController.isTextFieldNotEmpty(txtName, lbName, "Name must be filled out");
        if (!txtnameNotEmpty) {
            txtName.requestFocus();
        }
        boolean txtAddreesNotEmpty = ValidationController.isTextFieldNotEmpty(txtAddrees, lbAddrees, "Addrees must be filled out");
        if (!txtAddreesNotEmpty) {
            txtAddrees.requestFocus();
        }
        boolean txtPhoneNotEmpty = ValidationController.isPhoneSuitable(txtPhone, lbPhone, "Phone must be filled out");
        if (!txtPhoneNotEmpty) {
            txtPhone.requestFocus();
        }
        boolean txtEmailNotEmpty = ValidationController.isEmailSuitable(txtEmail, lbEmail, "Format:xxx@yyy.com");
        if (!txtEmailNotEmpty) {
            txtEmail.requestFocus();
        }
        if (txtCodeNotEmpty && txtnameNotEmpty && txtAddreesNotEmpty && txtPhoneNotEmpty && txtEmailNotEmpty) {

            SupplierDAOIplement sDI = new SupplierDAOIplement();
            sDI.insertSupplier(txtCode.getText(),txtCode.getText(), txtName.getText(), txtAddrees.getText(), txtPhone.getText(), txtTax.getText(), txtEmail.getText(), txtWebsite.getText(), txtNotice.getText());
            clear();
            labelEmpty();
            loadTable();
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        boolean txtCodeNotEmpty = ValidationController.isTextFieldHavingBarcode(txtCode, lbCode, "Code must be filled out");
        if (!txtCodeNotEmpty) {
            txtCode.requestFocus();
        }
        boolean txtnameNotEmpty = ValidationController.isTextFieldNotEmpty(txtName, lbName, "Name must be filled out");
        if (!txtnameNotEmpty) {
            txtName.requestFocus();
        }
        boolean txtAddreesNotEmpty = ValidationController.isTextFieldNotEmpty(txtAddrees, lbAddrees, "Addrees must be filled out");
        if (!txtAddreesNotEmpty) {
            txtAddrees.requestFocus();
        }
        boolean txtPhoneNotEmpty = ValidationController.isPhoneSuitable(txtPhone, lbPhone, "Phone must be filled out");
        if (!txtPhoneNotEmpty) {
            txtPhone.requestFocus();
        }
        boolean txtEmailNotEmpty = ValidationController.isEmailSuitable(txtEmail, lbEmail, "Format:xxx@yyy.com");
        if (!txtEmailNotEmpty) {
            txtEmail.requestFocus();
        }
        if (txtCodeNotEmpty && txtnameNotEmpty && txtAddreesNotEmpty && txtPhoneNotEmpty && txtEmailNotEmpty) {

            SupplierDAOIplement sDI = new SupplierDAOIplement();
            sDI.updateSupplier(txtCode.getText(), txtName.getText(), txtAddrees.getText(), txtPhone.getText(), txtTax.getText(), txtEmail.getText(), txtWebsite.getText(), txtNotice.getText());
            clear();
            labelEmpty();
            loadTable();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        boolean txtCodeNotEmpty = ValidationController.isTextFieldHavingBarcode(txtCode, lbCode, "Code must be filled out");
        if (!txtCodeNotEmpty) {
            txtCode.requestFocus();
        }
        if (txtCodeNotEmpty) {
            SupplierDAOIplement sDI = new SupplierDAOIplement();
            sDI.deleteSupplier(txtCode.getText());
            clear();
            labelEmpty();
            loadTable();
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clear();
    }

    private void labelEmpty() {
        lbCode.setText("");
        lbName.setText("");
        lbAddrees.setText("");
        lbPhone.setText("");
        lbEmail.setText("");
    }

    private void cssError() {
        lbCode.setStyle("-fx-text-fill:#daa520");
        lbName.setStyle("-fx-text-fill:#daa520");
        lbAddrees.setStyle("-fx-text-fill:#daa520");
        lbPhone.setStyle("-fx-text-fill:#daa520");
        lbEmail.setStyle("-fx-text-fill:#daa520");
    }

    private void loadTable() {
        SupplierDAOIplement SupIpl = new SupplierDAOIplement();
        ObservableList<Supplier> listSup = SupIpl.getAllSupplier();
        tableView.setItems(listSup);

        columnCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nameSupplier"));
        columnAddrees.setCellValueFactory(new PropertyValueFactory<>("addrees"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnTax.setCellValueFactory(new PropertyValueFactory<>("taxINumber"));
    }

    private void clear() {
        txtCode.clear();
        txtName.clear();
        txtAddrees.clear();
        txtPhone.clear();
        txtTax.clear();
        txtEmail.clear();
        txtWebsite.clear();
        txtNotice.clear();
    }

    private void click() {
        tableView.setOnMouseClicked((MouseEvent e) -> {
            Supplier supplier = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
            txtCode.setText(supplier.getCode());
            txtName.setText(supplier.getNameSupplier());
            txtAddrees.setText(supplier.getAddrees());
            txtPhone.setText(supplier.getPhone());
            txtTax.setText(supplier.getTaxINumber());
            txtEmail.setText(supplier.getEmail());
            txtWebsite.setText(supplier.getWebsite());
            txtNotice.setText(supplier.getNotice());
        });
    }

}
