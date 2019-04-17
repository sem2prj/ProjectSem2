/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

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
    private ComboBox<String> combobox_ncc;
    @FXML
    private TextField tf_qty;
    @FXML
    private TextField tf_amount;
    @FXML
    private ComboBox<?> combobox_congno;
    @FXML
    private TextField tf_already;
    @FXML
    private TextField tf_remain;

    private Connection con;
    private PreparedStatement pst;
    private PreparedStatement pst2;
    private ResultSet rs;

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
        
        
        tf_drug.setOnKeyReleased((KeyEvent ke) -> {
            textFieldDrugDo();
        

        });
        
        
        
    }
    
    private void textFieldDrugDo(){
          System.out.println(tf_drug.getText());
          
          pst = con.prepareStatement(sql);
    }
    
    private void comboboxDefault(){
        combobox_ncc.getItems().add("None");
        combobox_ncc.getValue();
    
    }

}
