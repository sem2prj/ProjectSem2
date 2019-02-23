/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
import static model.encrypt.encryptmd5;

/**
 *
 * @author PC
 */
public class APPFXMLController implements Initializable {

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
    private JFXButton btn_Exit;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(APPFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handle_Login(ActionEvent event) throws IOException {
        login();
    }

    private void login() throws IOException {
     
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

        if (!txt_user.getText().isEmpty() || !txt_password.getText().isEmpty()) {
            if (txt_user.getText().equals(getUser()) && txt_password.getText().equals(getPassword())) {

                Stage stage = (Stage) aPane_Login.getScene().getWindow();
                stage.close();
                //loading scene main
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));
                stage.setTitle("Main");

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE, "Invalid Email or Password", ButtonType.OK);
                Stage stageicondialog = (Stage) alert.getDialogPane().getScene().getWindow();
                stageicondialog.getIcons().add(new Image("image/invalid.png"));
                alert.setTitle("Invalid");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "Invalid Email or Password", ButtonType.OK);
            Stage stageicondialog = (Stage) alert.getDialogPane().getScene().getWindow();
            stageicondialog.getIcons().add(new Image("image/invalid.png"));
            alert.setTitle("Invalid");
            alert.showAndWait();

        }
    }

    private String getUser() {
        String user = "";
        try {
            pst = con.prepareStatement("select name_user from users where name_user=? ");
            pst.setString(1, txt_user.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                user = rs.getString(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(APPFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    private String getPassword() {
        String password = "";
        try {
            pst = con.prepareStatement("select password_user from users where name_user = ?");
            pst.setString(1, txt_user.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                password = rs.getString(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(APPFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return password;
    }

    @FXML
    private void handle_Exit(ActionEvent event) {
        Stage stage = (Stage) aPane_Login.getScene().getWindow();
        stage.close();
    }

}
