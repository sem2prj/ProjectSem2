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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
public class RegisterController implements Initializable {

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
    private PreparedStatement pst = null;
//    private PreparedStatement pst2 = null;
    private ResultSet rs;
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
    private JFXTextField tf_email;
    @FXML
    private Label error_email;

    @FXML
    private JFXComboBox<String> combobox_position;
    @FXML
    private JFXComboBox<String> combobox_department;
    @FXML
    private JFXComboBox<String> combobox_mission;
    @FXML
    private JFXDatePicker date_work;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("LALALALALALALALA");

// Giai quyet combobox_sex
        combobox_sex.getItems().addAll("Male", "Female");
        combobox_sex.getSelectionModel().selectFirst();
        combobox_sex.getValue();

//Combobox_position
        combobox_position.getItems().addAll("Employee", "Manager", "Chef", "Chairman");
        combobox_position.getSelectionModel().selectFirst();
        combobox_position.getValue();
        

//Combobox_department
        combobox_department.getItems().addAll("Sales", "Accountant", "General");
        combobox_department.getSelectionModel().selectFirst();
        combobox_department.getValue();

//Combobox_mission
        combobox_mission.getItems().addAll("User", "Supervision", "Admin", "President");
        combobox_mission.getSelectionModel().selectFirst();
        combobox_mission.getValue();

// Set default for date_birth
        date_birth.setValue(LOCAL_DATE.localDate("01-01-1991"));

// Set default for date_work
        date_work.setValue(LocalDate.now());

// Set color for textfield
//        error_email.setStyle("-fx-text-fill: red;");
//        error_name.setStyle("-fx-text-fill: red;");
//        error_address.setStyle("-fx-text-fill: red;");
//        error_phone.setStyle("-fx-text-fill: red;");
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

        boolean isEmailTrue = controller.ValidationController.isEmailSuitable(tf_email, error_email, "ex: abc@yahoo.com.vn");
        boolean isPhoneTrue = controller.ValidationController.isPhoneSuitable(tf_phone, error_phone, "ex: +84 925 111 4456, 0905999999,01238888888....");
        boolean isUsernameTrue = controller.ValidationController.isUsernameTrueType(tf_username, error_username, "username is not suitable");
        boolean isPasswordTrue = controller.ValidationController.isPasswordTrueType(pf_password, error_password, "password is not suitable");

        if (isUserNameNotEmpty && isPasswordNotEmpty && isREPasswordNotEmpty && arePasswordsametoREPassword
                && isEmailNotEmpty && isNameNotEmpty && isAddressNotEmpty && isPhoneNotEmpty) {
            if (isUsernameTrue && isPasswordTrue && isEmailTrue && isPhoneTrue) {
                try {

//                    String manhanvien = "NV" + Employees.classInstances;
                    String username = tf_username.getText();
                    String password = PasswordHash.encryptPass(pf_password.getText());
//                    String role_user = "admin";
                    String diachi = tf_address.getText().trim().replaceAll(" +", " ");
                    String tennhanvien = tf_name.getText().trim().replaceAll(" +", " ");
                    String phone = tf_phone.getText();
                    Date ngaysinh = java.sql.Date.valueOf(date_birth.getValue());
                    int gioitinh;
                    if (combobox_sex.getValue().equalsIgnoreCase("Male")) {
                        gioitinh = 1; //Nam la 1
                    } else {
                        gioitinh = 0; //Nu la 0
                    }
                    String position = combobox_position.getValue();
                    String department = combobox_department.getValue();
                    String mission = combobox_mission.getValue();
                    Date workday = java.sql.Date.valueOf(date_work.getValue());
                    String email = tf_email.getText();

                    con = controller.ConnectDB.connectSQLServer();
                    pst = con.prepareStatement("INSERT INTO DetailUser(Phone,Email,Addrees,Sex,BirthDay,Position,Department,Mission,WorkDay) VALUES(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                    pst.setString(1, phone);
                    pst.setString(2, email);
                    pst.setString(3, diachi);
                    pst.setInt(4, gioitinh);
                    pst.setDate(5, ngaysinh);
                    pst.setString(6, position);
                    pst.setString(7, department);
                    pst.setString(8, mission);
                    pst.setDate(9, workday);

//                    pst1.setString(2, username);
//                    pst1.setString(3, password);
//                    pst1.setString(4, role_user);
//
//
//
//                    pst2.setString(1, manhanvien);
//                    pst2.setString(2, tennhanvien);
//                    pst2.setString(3, username);
//                    pst2.setString(4, role_user);
//                    pst2.setString(5, diachi);
//                    pst2.setString(6, phone);
//                    pst2.setDate(7, ngaysinh);
//                    pst2.setInt(8, gioitinh);
                    pst.executeUpdate();
                    rs = pst.getGeneratedKeys();
                    rs.next();
                    Object key = rs.getObject(1);
                    String sql = "insert into Users(DetailID,UsersName,UsersPass,UsersFullName)values (?,?,?,?)";
                    pst = con.prepareStatement(sql);
                    
                    pst.setInt(1, Integer.parseInt(String.valueOf(key)));

//                    pst.setString(1, String.valueOf(key));
                    pst.setString(2, username);
                    pst.setString(3, password);
                    pst.setString(4, tennhanvien);
                    int a = pst.executeUpdate();

                    if (a == 1) {
                        System.out.println("Data update for 2 tables success");
                    }

                    pst.close();
                    con.close();

                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Stage stage = (Stage) anchorPane.getScene().getWindow();
                stage.close();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

//                Image applicationIcon = new Image(getClass().getResourceAsStream("/image/Login-icon.png"));
//                stage.getIcons().add(applicationIcon);
//        stage.setResizable(false);
                Scene scene = new Scene(root);
                stage.setTitle("Login");

                stage.setScene(scene);
                stage.show();

            }
        }
    }

}
