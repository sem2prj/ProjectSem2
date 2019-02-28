/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class MedrepsController implements Initializable {

    @FXML
    private TableView<?> tableDrug;

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    @FXML
    private JFXButton btnSell;
    @FXML
    private JFXTextField txtSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MedrepsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        addColumnDrug();
        search();
    }

    public void addColumnDrug() {
        TableColumn no = new TableColumn("No");
        no.setPrefWidth(90);
        tableDrug.getColumns().add(no);
        TableColumn code = new TableColumn("Code");
        code.setPrefWidth(110);
        tableDrug.getColumns().add(code);
        TableColumn drugName = new TableColumn("Drug Name");
        drugName.setPrefWidth(200);
        tableDrug.getColumns().add(drugName);
        TableColumn tOfMedicine = new TableColumn("Type Of Medicine");
        tOfMedicine.setPrefWidth(200);
        tableDrug.getColumns().add(tOfMedicine);
        TableColumn saleprice = new TableColumn("Saleprice");
        saleprice.setPrefWidth(200);
        tableDrug.getColumns().add(saleprice);
        TableColumn description = new TableColumn("Description");
        description.setPrefWidth(224);
        tableDrug.getColumns().add(description);
    }

    @FXML
    private void handleSell(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Sell.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void search() {
        txtSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (txtSearch.getText().equals("")) {
                    System.out.println(">>>>>>>>>>>>>>ok");
                }else if (true) {
                    
                }
                
            }
        });

    }
}
