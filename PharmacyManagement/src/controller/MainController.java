/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.Member;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class MainController implements Initializable {

    
    public static ObservableList<Member> Info_Member_Login = FXCollections.observableArrayList();
    @FXML
    private MenuItem mn_employees;

    @FXML
    private TabPane TabPane;

    private int counte,countst;
    @FXML
    private MenuItem mnStatistical;

    @FXML
    private Label labelA;
    @FXML
    private JFXButton btnA;
    @FXML
    private JFXButton btnB;
    @FXML
    private JFXButton btnC;

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Info_Member_Login = LoginController.ListMemberLogin;
        for (Member member : Info_Member_Login) {
            System.out.println("User: "+member.getuserName()+"Pass: "+member.getpassword()+"Role: "+member.getrole());
            if (member.getuserName().equals("admin")) {
                btnA.setDisable(true);
                btnB.setDisable(false);
                btnC.setDisable(true);
                labelA.setText(member.getuserName());
            }
            
            
        }
        
        
        
        
        mn_employees.setOnAction(e -> {
            counte++;
            if (counte == 1) {
                try {
                    Node productForm = FXMLLoader.load(getClass().getResource("/fxml/Employees.fxml"));
                    Tab tab = new Tab("Employees", productForm);
                    TabPane.getTabs().add(tab);
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mnStatistical();
    }

    @FXML
    private void handleEmployees(ActionEvent event) {
    }
    
    public void mnStatistical(){
        mnStatistical.setOnAction(e -> {
            countst++;
            if (countst == 1) {
                try {
                    Node productForm = FXMLLoader.load(getClass().getResource("/fxml/Chart.fxml"));
                    Tab tab = new Tab("Chart", productForm);
                    TabPane.getTabs().add(tab);
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
