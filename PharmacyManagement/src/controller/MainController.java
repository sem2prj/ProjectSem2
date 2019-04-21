/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.User;
//import model.User;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class MainController implements Initializable {

//    public static ObservableList<Member> Info_Member_Login = FXCollections.observableArrayList();
    public static ObservableList<User> infoUser = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnEmployee;

    //Animation pane
    @FXML
    private AnchorPane pane1;
    @FXML
    private AnchorPane pane2;
    @FXML
    private AnchorPane pane3;
    @FXML
    private ImageView drawImage;
    @FXML
    private AnchorPane pane4;
    @FXML
    private AnchorPane opacityPane;
    @FXML
    private AnchorPane drawerPane;
    @FXML
    private JFXButton btnDrug;
    @FXML
    private JFXButton btnCustomer;
    @FXML
    private JFXButton btnStore;
    @FXML
    private JFXButton btnPayment;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnSupplier;
    @FXML
    private JFXButton btnIE;
    @FXML
    private Label lbDateYear;
    @FXML
    private Label lbDateM;
    @FXML
    private Label lbDateDay;

    final private Timer timer = new Timer(true);
    final private LinkedList<TimerTask> taskList = new LinkedList<TimerTask>();
    Connection con;
    @FXML
    private Label txtUser;
    @FXML
    private Label txtMission;

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //        animationPane();
//        dislayIcon();
//        mnStatistical();
//        Info_Member_Login = LoginController.ListMemberLogin;
//        for (Member member : Info_Member_Login) {
//            System.out.println("User: " + member.getuserName() + "Pass: " + member.getpassword() + "Role: " + member.getrole());
//            if (member.getuserName().equals("admin")) {
//                btnHander.setDisable(true);
//                text.setText(member.getuserName());
//            }

//        }
            con = controller.ConnectDB.connectSQLServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        infoUser = LoginController.ListUserLogin;
        for (User user : infoUser) {
            System.out.println(user.getMission());
            txtUser.setText(user.getUserName());
            txtMission.setText(user.getMission());
        }
        //Animation 
        opacityPane.setVisible(false);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), opacityPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), drawerPane);
        translateTransition.setByX(-600);
        translateTransition.play();
        pane1.setStyle("-fx-background-image: url(\"/image/21.jpg\")");
        pane2.setStyle("-fx-background-image: url(\"/image/22.jpg\")");
        pane3.setStyle("-fx-background-image: url(\"/image/23.jpg\")");
        pane4.setStyle("-fx-background-image: url(\"/image/24.jpg\")");
        animationPane();
        drawImage.setOnMouseClicked(event -> {
            opacityPane.setVisible(true);
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), opacityPane);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });
        opacityPane.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), opacityPane);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();
            fadeTransition1.setOnFinished(event1 -> {
                opacityPane.setVisible(false);
            });
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });
        //Multi-threaded Timer 
        multiThreadedTimer();
    }

    private void multiThreadedTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int AM_PM = calendar.get(Calendar.AM_PM);
                if (AM_PM == 0) {
                    if (minute < 10 && minute >= 0) {
                        String times = hour + ":" + ("0" + minute) + ":" + second;
                        String date = (month + 1) + "/" + day + "/" + (year);
                        Platform.runLater(() -> {
                            lbDateDay.setText(times);
                            lbDateYear.setText(date);
                            lbDateM.setText("AM");
                        });
                    } else if (minute >= 10 && minute <= 60) {
                        String times = hour + ":" + (minute) + ":" + second;
                        String date = (month + 1) + "/" + day + "/" + (year);
                        Platform.runLater(() -> {
                            lbDateDay.setText(times);
                            lbDateYear.setText(date);
                            lbDateM.setText("AM");
                        });
                    }
                } else if (AM_PM == 1) {
                    if (minute < 10 && minute >= 0) {
                        String times = hour + ":" + ("0" + minute) + ":" + second;
                        String date = (month + 1) + "/" + day + "/" + (year);
                        Platform.runLater(() -> {
                            lbDateDay.setText(times);
                            lbDateYear.setText(date);
                            lbDateM.setText("PM");
                        });
                    } else if (minute >= 10 && minute <= 60) {
                        String times = hour + ":" + (minute) + ":" + second;
                        String date = (month + 1) + "/" + day + "/" + (year);
                        Platform.runLater(() -> {
                            lbDateDay.setText(times);
                            lbDateYear.setText(date);
                            lbDateM.setText("PM");
                        });
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
        taskList.add(task);
        if (taskList.isEmpty()) {
            taskList.removeFirst().cancel();
        }
    }

    private void animationPane() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), pane4);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(3), pane3);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();
            fadeTransition1.setOnFinished(event1 -> {
                FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(3), pane2);
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                fadeTransition2.play();
                fadeTransition2.setOnFinished(event2 -> {
                    FadeTransition fadeTransition00 = new FadeTransition(Duration.seconds(3), pane2);
                    fadeTransition00.setFromValue(0);
                    fadeTransition00.setToValue(1);
                    fadeTransition00.play();
                    fadeTransition00.setOnFinished(event3 -> {
                        FadeTransition fadeTransition11 = new FadeTransition(Duration.seconds(3), pane3);
                        fadeTransition11.setFromValue(0);
                        fadeTransition11.setToValue(1);
                        fadeTransition11.play();
                        fadeTransition11.setOnFinished(event4 -> {
                            FadeTransition fadeTransition22 = new FadeTransition(Duration.seconds(3), pane4);
                            fadeTransition22.setFromValue(0);
                            fadeTransition22.setToValue(1);
                            fadeTransition22.play();
                            fadeTransition22.setOnFinished(event5 -> {
                                animationPane();
                            });
                        });
                    });
                });
            });
        });
    }

    @FXML
    private void handleDrug(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Drug.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/drug.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Drug");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleEmployee(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Employee.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/employee.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Employee");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Customer.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/customer.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSuppier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Supplier.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/supplier.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.DECORATED.UNDECORATED);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Supplier");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleLogOut(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);

        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(this.getClass().getResource("/image/hyhy.png").toString()));
//        alert.initStyle(StageStyle.UTILITY);

        alert.setContentText("Are You Sure?");
        ButtonType okButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(okButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == okButton) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            stage.getIcons().add(new Image("/image/hyhy.png"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } else if (result.get() == noButton) {
        }
    }

    @FXML
    private void handlePayment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/OrderProduct.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("/css/customer.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.UTILITY);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        scene.getStylesheets().add(getClass().getResource("/css/order.css").toExternalForm());
        stage.setTitle("Payment");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void handleReport(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ReportMain.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/reportMain.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.DECORATED.UNDECORATED);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Report");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleStatistic(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/chartTotalAmount.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/chartTotal.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.DECORATED.UNDECORATED);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Statistic");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleStore(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Warehouse_Input.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/warehousecss.css").toExternalForm());
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.initStyle(StageStyle.DECORATED.UNDECORATED);
        stage.getIcons().add(new Image("/image/hyhy.png"));
        stage.setTitle("Warehouse");
        stage.setScene(scene);
        stage.show();
    }
}
