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
import javafx.collections.ObservableList;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
//import model.Employee;
//import model.EmployeeDAOImplement;
import model.User;
//import model.Employee;
//import model.Member;
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

    public static ObservableList<User> dataUser;

//    public static ObservableList<Employee> ListMembers = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UsernameDAOImplement eDAOIpl = new UsernameDAOImplement();
        dataUser = eDAOIpl.getAllUser();
        cssError();
    }

    @FXML
    private void handleChangePassword(ActionEvent event) throws SQLException {

        boolean txtUsernotEmpty = controller.ValidationController.isTextFieldNotEmpty(txtUser, lbUser, "UserName must be filled out");
        if (!txtUsernotEmpty) {
            txtUser.requestFocus();
        }
        boolean txtPasswordTrue = controller.ValidationController.isPasswordFieldHavingText(txtPass, lbPass, "Password must be filled out");
        if (!txtPasswordTrue) {
            txtPass.requestFocus();
        }

        boolean isConfirmNotEmpty = controller.ValidationController.isPasswordFieldHavingText(txtConfirm, lbConfirm, "Confirm password must be filled out");
        if (!isConfirmNotEmpty) {
            txtConfirm.requestFocus();
        }

        boolean txtRePass = controller.ValidationController.arePasswordAndREPasswordSame(txtPass, txtConfirm, lbConfirm, "Password and confirmation do not match");
        if (!txtRePass) {
            txtConfirm.requestFocus();
        }

        if (txtUsernotEmpty && txtPasswordTrue && txtRePass) {
            boolean isUsernameTrue = controller.ValidationController.isUsernameTrueType(txtUser, lbUser, "Username is not suitable");
            boolean isPasswordTrue = controller.ValidationController.isPasswordTrueType(txtPass, lbPass, "Password is not suitable");

            if (isPasswordTrue && isUsernameTrue) {
                boolean checkUser = false;
                UsernameDAOImplement userImp = new UsernameDAOImplement();
                String password = PasswordHash.encryptPass(txtConfirm.getText());
                for (User user : dataUser) {
                    if (txtUser.getText().equals(user.getUserName())) {
                        checkUser = true;
                        userImp.updateUserPass(txtUser.getText(), password);
                        clear();
                    }
                }
                if (checkUser == false) {
                    AlertDialog.display("Notice", "User name does not exist");
                }
            }
        }
    }

    private void cssError() {
        lbUser.setStyle("-fx-text-fill:#FF0000");
        lbPass.setStyle("-fx-text-fill:#FF0000");
        lbConfirm.setStyle("-fx-text-fill:#FF0000");
    }

    private void clear() {
        txtUser.clear();
        txtPass.clear();
        txtConfirm.clear();
    }

}
