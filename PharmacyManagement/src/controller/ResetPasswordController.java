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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.EmployeeDAOImplement;
import model.LOCAL_DATE;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ResetPasswordController implements Initializable {

    @FXML
    private JFXTextField reset_email;
    @FXML
    private DatePicker reset_birth;
    @FXML
    private JFXButton btn_confirm;
    @FXML
    private JFXButton btn_cancel;
    @FXML
    private JFXTextField reset_username;
    @FXML
    private Label error_email;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Label error_username;

    EmployeeDAOImplement employeeReturn;
    private ObservableList<Employee> listEmployee;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set default for date_birth
        reset_birth.setValue(LOCAL_DATE.localDate("02-01-1991"));
    }

    @FXML
    private void action_confirm(ActionEvent event) {
        boolean isUsernameNotEmpty = controller.ValidationController.isTextFieldHavingText(reset_username, error_username, "username is requied");
        boolean isEmailNotEmpty = controller.ValidationController.isTextFieldHavingText(reset_email, error_email, "email is requied");

        if (isUsernameNotEmpty && isEmailNotEmpty) {
            employeeReturn = new EmployeeDAOImplement();
            listEmployee = employeeReturn.searchCodeEmployee(reset_username.getText(), reset_email.getText(), java.sql.Date.valueOf(reset_birth.getValue()));
            if (listEmployee.listIterator().hasNext()) {
//                sendmail();
            }
        }
    }

    @FXML
    private void action_cancel(ActionEvent event) {
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();
    }

}
