/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employees;
import model.LOCAL_DATE;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class LoginFirstTimeController implements Initializable {

    @FXML
    private JFXTextField tf_username;
    @FXML
    private JFXButton btn_loginFirst;
    @FXML
    private Label error_username;
    @FXML
    private Label error_password;
    @FXML
    private Label error_repassword;
    @FXML
    private Label error_samePassword;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private PasswordField pf_password;
    @FXML
    private PasswordField pf_repassword;

    @FXML
    private JFXTextField tf_name;
    @FXML
    private JFXTextField tf_address;
    @FXML
    private JFXTextField tf_phone;

    private Connection con;
    private PreparedStatement pst1 = null;
    private PreparedStatement pst2 = null;

    @FXML
    private JFXDatePicker date_birth;
    @FXML
    private JFXComboBox<String> combobox_sex;

    @FXML
    private Label error_name;
    @FXML
    private Label error_address;
    @FXML
    private Label error_phone;
    @FXML
    private Label error_birth;
    @FXML
    private JFXTextField tf_email;
    @FXML
    private Label error_email;
    @FXML
    private Label error_email2;
    @FXML
    private Label error_phone2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

// Giai quyet combobox_sex
        combobox_sex.getItems().addAll("Male", "Female");
        combobox_sex.getSelectionModel().selectFirst();
//        combobox_sex.getValue();

// Set default for date_birth
        date_birth.setValue(LOCAL_DATE.localDate("01-01-1991"));

// Set color for textfield    
        error_email.setStyle("-fx-text-fill: red;");
        error_name.setStyle("-fx-text-fill: red;");
        error_address.setStyle("-fx-text-fill: red;");
        error_phone.setStyle("-fx-text-fill: red;");
        error_email2.setStyle("-fx-text-fill: red;");
        error_phone2.setStyle("-fx-text-fill: red;");
    }

    @FXML
    private void loginFirst_action(ActionEvent event) throws IOException {
        boolean isUserNameNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_username, error_username, "username is requied");
        boolean isPasswordNotEmpty = controller.ValidationController.isPasswordFieldHavingText(pf_password, error_password, "password is requied");
        boolean isREPasswordNotEmpty = controller.ValidationController.isPasswordFieldHavingText(pf_repassword, error_repassword, "repassword is requied");
        boolean arePasswordsametoREPassword = controller.ValidationController.arePasswordAndREPasswordSame(pf_password, pf_repassword, error_samePassword, "Password and Re-password not match");
        boolean isEmailNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_email, error_email, "email is requied");
        boolean isNameNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_name, error_name, "name is requied");
        boolean isAddressNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_address, error_address, "address is requied");
        boolean isPhoneNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_phone, error_phone, "phone is requied");

        boolean isEmailTrue = controller.ValidationController.isEmailSuitable(tf_email, error_email2, "ex: abc@yahoo.com.vn");
        boolean isPhoneTrue = controller.ValidationController.isPhoneSuitable(tf_phone, error_phone2, "ex: +84 925 111 4456, 0905999999,01238888888....");
        boolean isUsernameTrue = controller.ValidationController.isUsernameTrueType(tf_username, error_username, "username is not suitable");
        boolean isPasswordTrue = controller.ValidationController.isPasswordTrueType(pf_password, error_password, "password is not suitable");

        if (isUserNameNotEmpty && isPasswordNotEmpty && isREPasswordNotEmpty && arePasswordsametoREPassword
                && isEmailNotEmpty && isNameNotEmpty && isAddressNotEmpty && isPhoneNotEmpty
                && isEmailTrue && isPhoneTrue) {
            if (isUsernameTrue && isPasswordTrue) {
                try {
                    con = controller.ConnectDB.connectSQLServer();
                    pst1 = con.prepareStatement("INSERT INTO users2 VALUES(?,?,?,?)");
                    pst2 = con.prepareStatement("INSERT INTO NhanVien2(manhanvien,tennhanvien,username,role_user,diachi,sodienthoai,ngaysinh,gioitinh) VALUES(?,?,?,?,?,?,?,?)");

                    String manhanvien = "NV" + Employees.classInstances;
                    String username = tf_username.getText();
                    String password = PasswordHash.encryptPass(pf_password.getText());
                    String role_user = "admin";
                    String diachi = tf_address.getText().trim().replaceAll(" +", " ");
                    String tennhanvien = tf_name.getText().trim().replaceAll(" +", " ");
                    String phone = tf_phone.getText();
                    Date ngaysinh =  java.sql.Date.valueOf(date_birth.getValue());
                    int gioitinh;
                    if (combobox_sex.getValue().equalsIgnoreCase("Male")) {
                        gioitinh = 1;
                    } else {
                        gioitinh = 0;
                    }

                    pst1.setString(1, manhanvien);
                    pst1.setString(2, username);
                    pst1.setString(3, password);
                    pst1.setString(4, role_user);

                    int a = pst1.executeUpdate();

                    pst2.setString(1, manhanvien);
                    pst2.setString(2, tennhanvien);
                    pst2.setString(3, username);
                    pst2.setString(4, role_user);
                    pst2.setString(5, diachi);
                    pst2.setString(6, phone);
                    pst2.setDate(7, ngaysinh);
                    pst2.setInt(8, gioitinh);

                    int b = pst2.executeUpdate();

                    if (a == 1 && b == 1) {
                        System.out.println("Data update for 2 tables success");
                        Employees.classInstances++;

                    }

                    pst2.close();
                    pst1.close();

                    con.close();

                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(LoginFirstTimeController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Stage stage = (Stage) anchorPane.getScene().getWindow();
                stage.close();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

                Image applicationIcon = new Image(getClass().getResourceAsStream("/image/Login-icon.png"));
                stage.getIcons().add(applicationIcon);
//        stage.setResizable(false);
                Scene scene = new Scene(root);
                stage.setTitle("Login");

                stage.setScene(scene);
                stage.show();

            }
        }
    }

}
