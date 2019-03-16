/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
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
    private TableColumn<Supplier, String> columnName;
    @FXML
    private TableColumn<Supplier, String> columnType;
    @FXML
    private TableColumn<Supplier, String> columnAddrees;
    @FXML
    private TableColumn<Supplier, String> columnPhone;
    @FXML
    private TableColumn<Supplier, String> columnTIN;
    @FXML
    private TableColumn<Supplier, String> columnEmail;
    @FXML
    private TableColumn<Supplier, String> columnWebsite;
    @FXML
    private TableColumn<Supplier, String> columnNotice;
    @FXML
    private JFXTextField txtName;
    @FXML
    private Label lbName;
    @FXML
    private JFXTextField txtType;
    @FXML
    private Label lbType;
    @FXML
    private JFXTextField txtAddrees;
    @FXML
    private Label lbAddrees;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private Label lbPhone;
    @FXML
    private JFXTextField txtTIN;
    @FXML
    private Label lbTIN;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAdd(ActionEvent event) {
        SupplierDAOIplement sDI=new SupplierDAOIplement();
        sDI.insertSupplier(txtName.getText(), txtType.getText(), txtAddrees.getText(), txtPhone.getText(), txtTIN.getText(), txtEmail.getText(), txtWebsite.getText(), txtNotice.getText());
        
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        SupplierDAOIplement sDI=new SupplierDAOIplement();
        sDI.updateSupplier(txtName.getText(), txtType.getText(), txtAddrees.getText(), txtPhone.getText(), txtTIN.getText(), txtEmail.getText(), txtWebsite.getText(), txtNotice.getText());
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        SupplierDAOIplement sDI=new SupplierDAOIplement();
        sDI.deleteSupplier(txtTIN.getText());
    }

    @FXML
    private void handleClear(ActionEvent event) {
        
    }

    @FXML
    private void handleSearch(KeyEvent event) {
    }
    
}
