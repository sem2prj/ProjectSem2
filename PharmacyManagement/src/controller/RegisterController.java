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
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class RegisterController implements Initializable {

    @FXML
    private JFXButton btn_Register;
    @FXML
    private JFXTextField txt_user;
    @FXML
    private JFXTextField txt_email;
    @FXML
    private JFXTextField txt_email1;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXTextField txt_email2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handle_Register(ActionEvent event) {
    }
    
}
