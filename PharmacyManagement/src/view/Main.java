/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author PC
 */
public class Main extends Application {

    public Connection con;
    public PreparedStatement pst;
    public ResultSet rs;

    @Override
    public void start(Stage stage) throws Exception {

<<<<<<< HEAD
//        //Test MainController

=======
        //Test MainController
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
//Test MainController
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
//        //Test MainController
<<<<<<< HEAD
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ReportDetailWarehouse.fxml"));
=======
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ReportDetailWarehouse.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

<<<<<<< HEAD
//Start
//        con = controller.ConnectDB.getConnectTable();
//        pst = con.prepareStatement("select * from Users");
//        rs = pst.executeQuery();
=======
<<<<<<< HEAD
>>>>>>> 26a324929aa960fbbcad78efbfd9bb3568720268
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Warehouse_Input.fxml"));

//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/OrderProduct.fxml"));
<<<<<<< HEAD

=======
>>>>>>> e538d20d93b377d4642555febcc9ff8d684f6505
>>>>>>> 737b5fada00896ccda7012fd658510d2e2141d16
>>>>>>> 33df8e3d20c98e06fe609bd60a14b52090173dbe
//
//        Scene scene = new Scene(root);
//
<<<<<<< HEAD
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();


=======
<<<<<<< HEAD
//        if (rs.next()) {
//            rs.close();
//            pst.close();
//            con.close();
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
//
//           // Image applicationIcon = new Image(getClass().getResourceAsStream("/image/Login-icon.png"));
//         //   stage.getIcons().add(applicationIcon);
//            //stage.setResizable(false);
//            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
//            stage.setTitle("Login");
//            stage.getIcons().add(new Image("/image/hyhy.png"));
//            stage.initStyle(StageStyle.DECORATED);
//            stage.setScene(scene);
//            stage.show();
>>>>>>> 26a324929aa960fbbcad78efbfd9bb3568720268
//
//
//
//        } else {
//
//            rs.close();
//            pst.close();
//            con.close();
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
//
//            Scene scene = new Scene(root);
////            stage.initStyle(StageStyle.UNDECORATED);
//
//            scene.getStylesheets().add(getClass().getResource("/css/register.css").toExternalForm());
//            stage.getIcons().add(new Image("/image/hyhy.png"));
//            stage.setTitle("Register");
//            stage.setScene(scene);
////            stage.setResizable(false);
//            stage.show();
//
//        }
//
=======
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
>>>>>>> 33df8e3d20c98e06fe609bd60a14b52090173dbe

        //Start
        con = controller.ConnectDB.getConnectTable();
        pst = con.prepareStatement("select * from Users");
        rs = pst.executeQuery();


        if (rs.next()) {
            rs.close();
            pst.close();
            con.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

           // Image applicationIcon = new Image(getClass().getResourceAsStream("/image/Login-icon.png"));
         //   stage.getIcons().add(applicationIcon);
            //stage.setResizable(false);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
            stage.setTitle("Login");
            stage.getIcons().add(new Image("/image/hyhy.png"));
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.show();



        } else {

            rs.close();
            pst.close();
            con.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));

            Scene scene = new Scene(root);
//            stage.initStyle(StageStyle.UNDECORATED);

            scene.getStylesheets().add(getClass().getResource("/css/register.css").toExternalForm());
            stage.getIcons().add(new Image("/image/hyhy.png"));
            stage.setTitle("Register");
            stage.setScene(scene);
//            stage.setResizable(false);
            stage.show();

        }
       


    

//        //End
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
