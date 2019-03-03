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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import model.Member;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class MainController implements Initializable {

    public static ObservableList<Member> Info_Member_Login = FXCollections.observableArrayList();

    private TabPane TabPane;

    private int counte, countst;
    @FXML
    private MenuItem mnStatistical;

    @FXML
    private JFXButton btbInfo;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem mnLogout;
    @FXML
    private MenuItem mnChangePw;
    @FXML
    private MenuItem mnMmember;
    @FXML
    private MenuItem mnExit;
    @FXML
    private MenuItem mnEmployees;
    @FXML
    private MenuItem mnDrugs;
    @FXML
    private MenuItem mnCustomer;
    @FXML
    private MenuItem mnReport;
    @FXML
    private JFXButton btnBack;
    @FXML
    private JFXButton btnAddMember;
    @FXML
    private JFXButton btnAddGroup;
    @FXML
    private JFXButton btnsSatistical;
    @FXML
    private JFXButton btnrReport;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXButton btnHelp;
    @FXML
    private JFXButton btnAddGroup1;

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Info_Member_Login = LoginController.ListMemberLogin;
        for (Member member : Info_Member_Login) {
            System.out.println("User: " + member.getuserName() + "Pass: " + member.getpassword() + "Role: " + member.getrole());
            if (member.getuserName().equals("admin")) {
                mnChangePw.setDisable(true);
                mnExit.setDisable(false);
                btnSearch.setDisable(true);
//                labelA.setText(member.getuserName());
            }

        }
        
        dislayIcon();

    }

    public void dislayIcon() {
        Image iconLogout = new Image(getClass().getResourceAsStream("/image/logout.png"));
        ImageView cameraIconLogOut = new ImageView(iconLogout);
        cameraIconLogOut.setFitHeight(16);
        cameraIconLogOut.setFitWidth(16);
        mnLogout.setGraphic(cameraIconLogOut);
        mnLogout.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        Image iconChangePW = new Image(getClass().getResourceAsStream("/image/collaboration.png"));
        ImageView cameraChangepw = new ImageView(iconChangePW);
        cameraChangepw.setFitHeight(16);
        cameraChangepw.setFitWidth(16);
        mnChangePw.setGraphic(cameraChangepw);
        mnChangePw.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));

        Image iconMT = new Image(getClass().getResourceAsStream("/image/managementteam.png"));
        ImageView cameraTeam = new ImageView(iconMT);
        cameraTeam.setFitHeight(16);
        cameraTeam.setFitWidth(16);
        mnMmember.setGraphic(cameraTeam);
        mnMmember.setAccelerator(new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN));

        Image iconEmployees = new Image(getClass().getResourceAsStream("/image/employees.png"));
        ImageView cameraEmployees = new ImageView(iconEmployees);
        cameraEmployees.setFitHeight(16);
        cameraEmployees.setFitWidth(16);
        mnEmployees.setGraphic(cameraEmployees);
//        btbIcon.setContentDisplay(ContentDisplay.LEFT);
        Image iconDrug = new Image(getClass().getResourceAsStream("/image/drug.png"));
        ImageView cameraDrug = new ImageView(iconDrug);
        cameraDrug.setFitHeight(16);
        cameraDrug.setFitWidth(16);
        mnDrugs.setGraphic(cameraDrug);
        mnDrugs.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));

        Image iconCustommer = new Image(getClass().getResourceAsStream("/image/customer.png"));
        ImageView cameraCustommer = new ImageView(iconCustommer);
        cameraCustommer.setFitHeight(16);
        cameraCustommer.setFitWidth(16);
        mnCustomer.setGraphic(cameraCustommer);

        Image iconReport = new Image(getClass().getResourceAsStream("/image/report.png"));
        ImageView cameraReport = new ImageView(iconReport);
        cameraReport.setFitHeight(16);
        cameraReport.setFitWidth(16);
        mnReport.setGraphic(cameraReport);

        //button action
        Image iconBack = new Image(getClass().getResourceAsStream("/image/logout.png"));
        ImageView cameraBack = new ImageView(iconBack);
        cameraBack.setFitHeight(25);
        cameraBack.setFitWidth(25);
        btnBack.setGraphic(cameraBack);
        //button list
        Image iconInfo = new Image(getClass().getResourceAsStream("/image/report.png"));
        ImageView cameraInfo = new ImageView(iconInfo);
        cameraInfo.setFitHeight(25);
        cameraInfo.setFitWidth(25);
        btbInfo.setGraphic(cameraInfo);
    }

    public void mnStatistical() {
        mnStatistical.setOnAction(e -> {
            countst++;
            if (countst == 1) {
                try {
                    Node productForm = FXMLLoader.load(getClass().getResource("/fxml/Chart.fxml"));
                    Tab tab = new Tab("Chart", productForm);
                    TabPane.getTabs().add(tab);

                } catch (IOException ex) {
                    Logger.getLogger(MainController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mnMmember.setOnAction(e -> {
            counte++;
            if (counte == 1) {
                try {
                    Node productForm = FXMLLoader.load(getClass().getResource("/fxml/Employees.fxml"));
                    Tab tab = new Tab("Employees", productForm);
                    TabPane.getTabs().add(tab);

                } catch (IOException ex) {
                    Logger.getLogger(MainController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    private void handleEmployees(ActionEvent event) {
    }

}
