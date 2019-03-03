/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    
    private Connection con;
    private PreparedStatement pst = null;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginFirst_action(ActionEvent event) throws IOException {
        boolean isUserNameNotEmpty = controller.ValidationController.isTextFieldHavingText(tf_username, error_username, "username is requied");
        boolean isPasswordNotEmpty = controller.ValidationController.isPasswordFieldHavingText(pf_password, error_password, "password is requied");
        boolean isREPasswordNotEmpty = controller.ValidationController.isPasswordFieldHavingText(pf_repassword, error_repassword, "repassword is requied");
        boolean arePasswordsametoREPassword = controller.ValidationController.arePasswordAndREPasswordSame(pf_password, pf_repassword, error_samePassword, "Password and Re-password not match");
        

        
        if ( isUserNameNotEmpty && isPasswordNotEmpty && isREPasswordNotEmpty && arePasswordsametoREPassword )
        {
            try {
                con = controller.ConnectDB.connectSQLServer();
                pst = con.prepareStatement("INSERT INTO users VALUES(?,?,?)" );
                String name_user = tf_username.getText();
                String password_user = PasswordHash.encryptPass(pf_password.getText());
                String role_user = "admin";
                pst.setString(1,name_user);
                pst.setString(2, password_user);
                pst.setString(3,role_user);     
                 
                int i = pst.executeUpdate();
                if (i==1){
                    System.out.println("Data Insert Successfully ");
                }
                
                pst.close();
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
