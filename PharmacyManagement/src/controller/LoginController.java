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
import model.Member;
import model.MemberDAOIplement;

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
    private JFXButton btn_Exit;
    
    @FXML
    private Label error_username;
    @FXML
    private Label error_password;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    
    public static ObservableList<Member> ListMember = FXCollections.observableArrayList();
        public static ObservableList<Member> ListMemberLogin = FXCollections.observableArrayList();

        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        MemberDAOIplement mberDAO = new MemberDAOIplement();
        ListMember = mberDAO.getAllMember();
    }

    @FXML
    private void handle_Login(ActionEvent event) throws IOException {
        boolean isUserNameNotEmpty = controller.ValidationController.isTextFieldHavingText(txt_user, error_username, "username is requied");
        boolean isPasswordNotEmpty = controller.ValidationController.isPasswordFieldHavingText(txt_password, error_password, "password is requied");
        
        
        if(isUserNameNotEmpty && isPasswordNotEmpty){
            login();
        }
        
    }

    private void login() throws IOException {
          if (!txt_user.getText().isEmpty() && !txt_password.getText().isEmpty()) {
            boolean check = false;
            for (Member member : ListMember) {
                if (txt_user.getText().equals(member.getuserName()) && PasswordHash.encryptPass(txt_password.getText()).equals(member.getpassword())) {
                    check = true;
                    ListMemberLogin.add(member);
                    Stage stage = (Stage) aPane_Login.getScene().getWindow();
                    stage.getIcons().clear();
                    stage.close();
                    //loading scene main

                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
//                    Image applicationIcon = new Image(getClass().getResourceAsStream("/image/main.png"));
//                    stage.getIcons().add(applicationIcon);

                    stage.setTitle("Main");

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                break;
            }
            if(check==false){
                
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.NONE, "Invalid Email or Password", ButtonType.OK);
//            Stage stageicondialog = (Stage) alert.getDialogPane().getScene().getWindow();
//            stageicondialog.getIcons().add(new Image("image/invalid.png"));
            alert.setTitle("Invalid");
            alert.showAndWait();
        }

        
    }

   
    @FXML
    private void handle_Exit(ActionEvent event) {
        Stage stage = (Stage) aPane_Login.getScene().getWindow();
        stage.close();
    }

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
    
}
