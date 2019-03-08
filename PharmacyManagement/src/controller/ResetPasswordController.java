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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ResetPasswordController implements Initializable {

    @FXML
    private JFXTextField reset_email;
    @FXML
    private JFXTextField reset_phone;
    @FXML
    private DatePicker reset_birth;
    @FXML
    private JFXButton btn_confirm;
    @FXML
    private JFXButton btn_cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void action_confirm(ActionEvent event) {
    }

    @FXML
    private void action_cancel(ActionEvent event) {
    }
    
}
