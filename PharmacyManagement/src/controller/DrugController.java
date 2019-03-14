/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
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
import model.Drug;
import model.DrugDAOImplement;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class DrugController implements Initializable {

    @FXML
    private TableView<Drug> tableView;
    @FXML
    private JFXTextField txtDcode;
    @FXML
    private JFXTextField txtDname;
    @FXML
    private JFXTextField txtType;
    @FXML
    private JFXTextField txtUnit;
    @FXML
    private JFXTextField txtPricein;
    @FXML
    private JFXTextField txtSaleprice;
    @FXML
    private JFXTextField txtSupplier;
    @FXML
    private JFXTextField txtAmount;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private Label lbcode;
    @FXML
    private Label lbName;
    @FXML
    private Label lbType;
    @FXML
    private Label lbUnit;
    @FXML
    private Label lbPricein;
    @FXML
    private Label lbSaleP;
    @FXML
    private Label lbSupplier;
    @FXML
    private Label lbAmount;
    @FXML
    private Label lbDes;
    @FXML
    private JFXTextField txtSearch;

    private ObservableList<Drug> data;
    ;
    @FXML
    private TableColumn<Drug, String> columnDcode;
    @FXML
    private TableColumn<Drug, String> columnDName;
    @FXML
    private TableColumn<Drug, String> columnDType;
    @FXML
    private TableColumn<Drug, String> columnDUnit;
    @FXML
    private TableColumn<Drug, String> columnPriceIn;
    @FXML
    private TableColumn<Drug, String> columnDSale;
    @FXML
    private TableColumn<Drug, String> columnDSupplier;
    @FXML
    private TableColumn<Drug, String> columnDAmount;
    @FXML
    private TableColumn<Drug, String> columnDDescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DrugDAOImplement dDI = new DrugDAOImplement();

        data = dDI.getAllDrug();

        loadTable();
        click();
        search();
        css();
    }

    @FXML
    private void handleAdd(ActionEvent event) {

//        boolean txtDescriptionnotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtDescription, lbDes, "Description is requied");
//        if (txtDescriptionnotEmpty) {
//            txtDescription.requestFocus();
//        }

        boolean txtAmountnotEmpty = controller.ValidationController.isTextFieldTypeNumber(txtAmount, lbAmount, "Amount is number");
        if (txtAmountnotEmpty) {
            txtAmount.requestFocus();
        }

        boolean txtSuppliernotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtSupplier, lbSupplier, "Supplier is requied");
        if (txtSuppliernotEmpty) {
            txtSupplier.requestFocus();
        }

        boolean txtSalepricenotEmpty = controller.ValidationController.isTextFieldTypeNumber(txtSaleprice, lbSaleP, "Sale price is number");
        if (txtSalepricenotEmpty) {
            txtSaleprice.requestFocus();
        }

        boolean txtPriceinnotEmpty = controller.ValidationController.isTextFieldTypeNumber(txtPricein, lbPricein, "Price In is number");
        if (txtPriceinnotEmpty) {
            txtPricein.requestFocus();
        }

        boolean txtUnitnotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtUnit, lbUnit, "Unit is requied");
        if (!txtUnitnotEmpty) {
            txtUnit.requestFocus();
        }

        boolean txtTypenotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtType, lbType, "Type is requied");
        if (!txtTypenotEmpty) {
            txtType.requestFocus();
        }

        boolean txtDnamenotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtDname, lbName, "Drug Name is requied");
        if (!txtDnamenotEmpty) {
            txtDname.requestFocus();
        }

        boolean txtDcodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtDcode, lbcode, "Drug is requied");
        if (!txtDcodenotEmpty) {
            txtDcode.requestFocus();
        }

        if (txtDcodenotEmpty && txtDnamenotEmpty && txtTypenotEmpty && txtUnitnotEmpty && txtPriceinnotEmpty && txtSalepricenotEmpty && txtSuppliernotEmpty && txtAmountnotEmpty ) {
            DrugDAOImplement dDI = new DrugDAOImplement();
            dDI.insertDrug(txtDcode.getText(), txtDname.getText(), txtType.getText(), txtUnit.getText(), Double.parseDouble(txtPricein.getText()), Double.parseDouble(txtSaleprice.getText()), txtSupplier.getText(), Integer.parseInt(txtAmount.getText()), txtDescription.getText());
            clear();
            loadTable();
        }

    }

    @FXML
    private void handleUpdate(ActionEvent event) {
//        boolean txtDescriptionnotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtDescription, lbDes, "Description is requied");
//        if (txtDescriptionnotEmpty) {
//            txtDescription.requestFocus();
//        }

        boolean txtAmountnotEmpty = controller.ValidationController.isTextFieldTypeNumber(txtAmount, lbAmount, "Amount is number");
        if (txtAmountnotEmpty) {
            txtAmount.requestFocus();
        }

        boolean txtSuppliernotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtSupplier, lbSupplier, "Supplier is requied");
        if (txtSuppliernotEmpty) {
            txtSupplier.requestFocus();
        }

        boolean txtSalepricenotEmpty = controller.ValidationController.isTextFieldTypeNumber(txtSaleprice, lbSaleP, "Sale price is number");
        if (txtSalepricenotEmpty) {
            txtSaleprice.requestFocus();
        }

        boolean txtPriceinnotEmpty = controller.ValidationController.isTextFieldTypeNumber(txtPricein, lbPricein, "Price In is number");
        if (txtPriceinnotEmpty) {
            txtPricein.requestFocus();
        }

        boolean txtUnitnotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtUnit, lbUnit, "Unit is requied");
        if (!txtUnitnotEmpty) {
            txtUnit.requestFocus();
        }

        boolean txtTypenotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtType, lbType, "Type is requied");
        if (!txtTypenotEmpty) {
            txtType.requestFocus();
        }

        boolean txtDnamenotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtDname, lbName, "Drug Name is requied");
        if (!txtDnamenotEmpty) {
            txtDname.requestFocus();
        }

        boolean txtDcodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtDcode, lbcode, "Drug is requied");
        if (!txtDcodenotEmpty) {
            txtDcode.requestFocus();
        }

        if (txtDcodenotEmpty && txtDnamenotEmpty && txtTypenotEmpty && txtUnitnotEmpty && txtPriceinnotEmpty && txtSalepricenotEmpty && txtSuppliernotEmpty && txtAmountnotEmpty ) {
            DrugDAOImplement dDI = new DrugDAOImplement();
            dDI.updateDrug(txtDcode.getText(), txtDname.getText(), txtType.getText(), txtUnit.getText(), Double.parseDouble(txtPricein.getText()), Double.parseDouble(txtSaleprice.getText()), txtSupplier.getText(), Integer.parseInt(txtAmount.getText()), txtDescription.getText());
            clear();
            loadTable();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        boolean txtDcodenotEmpty = controller.ValidationController.isTextFieldHavingBarcode(txtDcode, lbcode, "Drug is requied");
        if (!txtDcodenotEmpty) {
            txtDcode.requestFocus();
        }
        if (txtDcodenotEmpty) {
            DrugDAOImplement dDI = new DrugDAOImplement();
            dDI.deleteDrug(txtDcode.getText());
            loadTable();
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clear();
    }

    private void clear() {
        txtDcode.clear();
        txtDname.clear();
        txtType.clear();
        txtUnit.clear();
        txtPricein.clear();
        txtSaleprice.clear();
        txtSupplier.clear();
        txtAmount.clear();
        txtDescription.clear();
    }

    private void css() {
        lbcode.setStyle("-fx-text-fill:orange");
        lbName.setStyle("-fx-text-fill:orange");
        lbType.setStyle("-fx-text-fill:orange");
        lbUnit.setStyle("-fx-text-fill:orange");
        lbPricein.setStyle("-fx-text-fill:orange");
        lbSaleP.setStyle("-fx-text-fill:orange");
        lbSupplier.setStyle("-fx-text-fill:orange");
        lbAmount.setStyle("-fx-text-fill:orange");
        lbDes.setStyle("-fx-text-fill:orange");
    }

    private void loadTable() {
        DrugDAOImplement dDI = new DrugDAOImplement();
        ObservableList<Drug> listDrug = dDI.getAllDrug();
        tableView.setItems(listDrug);

        columnDcode.setCellValueFactory(new PropertyValueFactory<>("DCode"));
        columnDName.setCellValueFactory(new PropertyValueFactory<>("nameDrug"));
        columnDType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnDUnit.setCellValueFactory(new PropertyValueFactory<>("UNit"));
        columnPriceIn.setCellValueFactory(new PropertyValueFactory<>("priceIn"));
        columnDSale.setCellValueFactory(new PropertyValueFactory<>("saleprice"));
        columnDSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        columnDAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnDDescription.setCellValueFactory(new PropertyValueFactory<>("desciption"));
    }

    private void click() {
        tableView.setOnMouseClicked(e -> {
            Drug drug = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
            txtDcode.setText(drug.getDCode());
            txtDname.setText(drug.getNameDrug());
            txtType.setText(drug.getType());
            txtUnit.setText(drug.getUNit());
            txtPricein.setText(String.format("%.1f", drug.getPriceIn()));
            txtSaleprice.setText(String.format("%.1f", drug.getSaleprice()));
            txtSupplier.setText(drug.getSupplier());
            txtAmount.setText(String.format("%d", drug.getAmount()));
            txtDescription.setText(drug.getDesciption());
        });
    }

    private void search() {
        txtSearch.setOnKeyReleased(e -> {
            DrugDAOImplement dDI = new DrugDAOImplement();
            ObservableList<Drug> listDrug = dDI.searchDrug(txtDcode.getText());
            tableView.setItems(listDrug);
        });
        FilteredList<Drug> filteredData = new FilteredList<>(data, e -> true);
        txtSearch.setOnKeyReleased(e -> {
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Drug>) drug -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (drug.getDCode().contains(newValue)) {
                        return true;
                    } else if (drug.getNameDrug().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
        });
        SortedList<Drug> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

}
