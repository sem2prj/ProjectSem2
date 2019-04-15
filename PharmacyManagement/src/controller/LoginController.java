/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.UserCurrentLogin;
import model.UsernameDAOImplement;

/**
 *
 * @author PC
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane logo_AnchorPane;
    @FXML
    private JFXTextField txt_user;
    @FXML
    private JFXButton btn_Login;
    @FXML
    private JFXPasswordField txt_password;
    @FXML
    private AnchorPane aPane_Login;

    @FXML
    private Label error_username;
    @FXML
    private Label error_password;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public static ObservableList<User> ListUser = FXCollections.observableArrayList();
    public static ObservableList<User> ListUserLogin = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        UsernameDAOImplement userDAOIm = new UsernameDAOImplement();
        ListUser = userDAOIm.getAllUser();
        for (User user : ListUser) {
            System.out.println(user.getUserName());
        }

//        txt_user.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                login();
//            }
//        });
//        
//        txt_password.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                login();
//            }
//        });
    }

    @FXML
    private void handle_Login(ActionEvent event) {
        login();
    }

    private void login() {
        boolean isUserNameNotEmpty = controller.ValidationController.isTextFieldHavingText(txt_user, error_username, "Username must be filled out");
        if (isUserNameNotEmpty) {
            txt_user.requestFocus();
        }
        boolean isPasswordNotEmpty = controller.ValidationController.isPasswordFieldHavingText(txt_password, error_password, "Password must be filled out");
        if (isPasswordNotEmpty) {
            txt_password.requestFocus();
        }
        if (isUserNameNotEmpty && isPasswordNotEmpty) {
            boolean check = false;
            for (User user : ListUser) {
                if (txt_user.getText().equals(user.getUserName()) && PasswordHash.encryptPass(txt_password.getText()).equals(user.getPassword())) {
                    check = true;
                    ListUserLogin.add(user);
                    UserCurrentLogin.setCurrentLogin(txt_user.getText());
                    Stage stage = (Stage) aPane_Login.getScene().getWindow();
                    stage.getIcons().clear();
                    stage.close();
                    stage.setTitle("Main");
                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
                        stage.getIcons().add(new Image("/image/hyhy.png"));
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (check == false) {
//                Alert alert = new Alert(Alert.AlertType.NONE, "Invalid Email or Password", ButtonType.OK);
//                alert.setTitle("Invalid");
//                alert.showAndWait();
                
                 AlertDialog.display("Notice", "Invalid Email or Password");
            }
        }
    }

//    private void handleExit(ActionEvent event) {
//        Stage stage = (Stage) aPane_Login.getScene().getWindow();
//        stage.close();
//      System.exit(0);
//    }


//        if (txt_user.getText().equalsIgnoreCase("")) {
//            Alert alert = new Alert(Alert.AlertType.NONE, "Invalid Email", ButtonType.OK);
//            txt_user.requestFocus();
//            return;
//        }
//        if (txt_password.getText().equalsIgnoreCase("")) {
//            Alert alert = new Alert(Alert.AlertType.NONE, "Invalid Password", ButtonType.OK);
//            txt_password.requestFocus();
//            return;
//        }
//        if (isUserNameNotEmpty && isPasswordNotEmpty) {
//            boolean check = false;
//            for (User user : ListUser) {
//                if (txt_user.getText().equals(user.getUserName()) && PasswordHash.encryptPass(txt_password.getText()).equals(user.getPassword())) {
//                    check = true;
//                    ListUserLogin.add(user);
//                    Stage stage = (Stage) aPane_Login.getScene().getWindow();
//                    stage.getIcons().clear();
//                    stage.close();
//
//                    stage.setTitle("Main");
//                    Parent root;
//                    try {
//                        root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
//                        Scene scene = new Scene(root);
//                        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
//                        stage.getIcons().add(new Image("/image/hyhy.png"));
//                        stage.setScene(scene);
//                        stage.show();
//                    } catch (IOException ex) {
//                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//            if (check == false) {
//                Alert alert = new Alert(Alert.AlertType.NONE, "Invalid Email or Password", ButtonType.OK);
//                alert.setTitle("Invalid");
//                alert.showAndWait();
//            }
//        }
    }

//    private void handleExit(ActionEvent event) {
//        Stage stage = (Stage) aPane_Login.getScene().getWindow();
//        stage.close();
//
//    }



