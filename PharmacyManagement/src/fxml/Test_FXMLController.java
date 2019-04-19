/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author admin
 */
public class Test_FXMLController implements Initializable {

    private Connection con;
    private PreparedStatement pst;
    private PreparedStatement pst2;
    private ResultSet rs;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private JFXComboBox<String> combobox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Test_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Test_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        try {
            comboboxDo();
        } catch (SQLException ex) {
            Logger.getLogger(Test_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void action_combobox(ActionEvent event) throws SQLException {
        System.out.println("lalalala");
        comboboxDo();
    }
    
    private void comboboxDo() throws SQLException {
        pst = con.prepareStatement("select Supplier from Product where PName like ?");
        pst.setString(1, "thuocdoc2");

        rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString(1));
            combobox.getItems().add(rs.getString(1));

        } 
        combobox.getSelectionModel().selectFirst();
        combobox.getValue();
    }
}
