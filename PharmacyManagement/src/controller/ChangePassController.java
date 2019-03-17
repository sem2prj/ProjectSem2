/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Member;
import model.UsernameDAOImplement;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ChangePassController implements Initializable {

    @FXML
    private JFXTextField txtUser;
    @FXML
    private Label lbUser;
    @FXML
    private Label lbPass;
    @FXML
    private JFXPasswordField txtPass;
    @FXML
    private Label lbConfirm;
    @FXML
    private JFXPasswordField txtConfirm;

    public static ObservableList<Member> ListMembers = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cssError();
    }

    @FXML
    private void handleChangePassword(ActionEvent event) throws SQLException {
        boolean txtUsernotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtUser, lbUser, "User is requied");
        boolean txtPasswordTrue = controller.ValidationController.isPasswordTrueType(txtPass, lbPass, "Password is requied");
        boolean txtRePass = controller.ValidationController.arePasswordAndREPasswordSame(txtPass, txtConfirm, lbConfirm, "Not match");
        if (txtUsernotEmpty && txtPasswordTrue && txtRePass) {
            UsernameDAOImplement userImp = new UsernameDAOImplement();
            String password = PasswordHash.encryptPass(txtConfirm.getText());
            userImp.updateUser(txtUser.getText(), txtConfirm.getText());
            clear();
        }
    }

    private void cssError() {
        lbUser.setStyle("-fx-text-fill:orange");
        lbPass.setStyle("-fx-text-fill:orange");
        lbConfirm.setStyle("-fx-text-fill:orange");
    }
    private void clear(){
        txtUser.clear();
        txtPass.clear();
        txtConfirm.clear();
    }

}
