/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import model.Member;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class MainController implements Initializable {

    public static ObservableList<Member> Info_Member_Login = FXCollections.observableArrayList();

    @FXML
    private TabPane TabPane;

    private boolean counte, countst;
    @FXML
    private MenuItem mnStatistical;

    @FXML
    private MenuBar menuBar;

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
    @FXML
    private MenuItem mnChangePw;
    @FXML
    private MenuItem mnMmember;
    @FXML
    private MenuItem mnExit;
    @FXML
    private MenuItem mnLogout;
    @FXML
    private MenuItem mnEmployees;
    @FXML
    private MenuItem mnDrugs;
    @FXML
    private MenuItem mnCustomer;
    @FXML
    private MenuItem mnReport;

    @FXML
    private JFXButton btnEmployee;

    //Animation pane
    @FXML
    private AnchorPane pane1;
    @FXML
    private AnchorPane pane2;
    @FXML
    private AnchorPane pane3;

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animationPane();
        dislayIcon();
        mnStatistical();
//        Info_Member_Login = LoginController.ListMemberLogin;
//        for (Member member : Info_Member_Login) {
//            System.out.println("User: " + member.getuserName() + "Pass: " + member.getpassword() + "Role: " + member.getrole());
//            if (member.getuserName().equals("admin")) {
//                btnHander.setDisable(true);
//                text.setText(member.getuserName());
//            }

//        }
    }

    @FXML
    private void handleEmployees(ActionEvent event) {
    }

    private void animationPane() {
        pane1.setStyle("-fx-background-image:url(\"/image/Hydrangeas.jpg\")");
        pane2.setStyle("-fx-background-image:url(\"/image/Jellyfish.jpg\")");
        pane3.setStyle("-fx-background-image:url(\"/image/Penguins.jpg\")");

        FadeTransition fdT1 = new FadeTransition(Duration.seconds(3), pane3);
        fdT1.setFromValue(1);
        fdT1.setToValue(0);
        fdT1.play();

        fdT1.setOnFinished(e1 -> {
            FadeTransition fdT2 = new FadeTransition(Duration.seconds(3), pane2);
            fdT2.setFromValue(1);
            fdT2.setToValue(0);
            fdT2.play();
            fdT2.setOnFinished(e2 -> {
                FadeTransition fdT3 = new FadeTransition(Duration.seconds(3), pane3);
                fdT3.setFromValue(1);
                fdT3.setToValue(0);
                fdT3.play();
                fdT3.setOnFinished(e3 -> {
                    FadeTransition fdT4 = new FadeTransition(Duration.seconds(3), pane2);
                    fdT4.setFromValue(0);
                    fdT4.setToValue(1);
                    fdT4.play();
                    fdT4.setOnFinished(e4 -> {
                        FadeTransition fdT5 = new FadeTransition(Duration.seconds(3), pane3);
                        fdT5.setFromValue(0);
                        fdT5.setToValue(1);
                        fdT5.play();
                        fdT5.setOnFinished(e6 -> {
                            FadeTransition fdT6 = new FadeTransition(Duration.seconds(3), pane1);
                            fdT6.setFromValue(0);
                            fdT6.setToValue(1);
                            fdT6.play();
                            fdT6.setOnFinished(e7 -> {
                                FadeTransition fdT7 = new FadeTransition(Duration.seconds(3), pane3);
                                animationPane();
                            });
                        });

                    });
                });

            });
        });
        
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
        cameraBack.setFitHeight(20);
        cameraBack.setFitWidth(20);
        btnEmployee.setGraphic(cameraBack);

    }

    public void mnStatistical() {
        btnEmployee.setOnAction(event -> {
            countst=true;
            if (countst) {
                try {
                    Node productForm = FXMLLoader.load(getClass().getResource("/fxml/Employee.fxml"));
                    Tab tab = new Tab("Employee", productForm);
                    TabPane.getTabs().add(tab);
                    

                } catch (IOException ex) {
                    Logger.getLogger(MainController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
           
        });
//        mnMmember.setOnAction(e -> {
//            counte++;
//            if (counte == 1) {
//                try {
//                    Node productForm = FXMLLoader.load(getClass().getResource("/fxml/Employees.fxml"));
//                    Tab tab = new Tab("Employees", productForm);
//                    TabPane.getTabs().add(tab);
//
//                } catch (IOException ex) {
//                    Logger.getLogger(MainController.class
//                            .getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
    }

}
