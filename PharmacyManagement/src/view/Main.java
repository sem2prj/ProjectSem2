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

//        //Test MainController
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
//
//            Scene scene = new Scene(root);
//
//            stage.setScene(scene);
////            stage.setResizable(false);
//            stage.show();
        //Start
        con = controller.ConnectDB.connectSQLServer();

        pst = con.prepareStatement("select max(OrderID) from Orders");
        rs = pst.executeQuery();
        System.out.println(rs.getString(1));

        
        
//        if (rs.next()) {
//            rs.close();
//            pst.close();
//            con.close();
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
//
////            Image applicationIcon = new Image(getClass().getResourceAsStream("/image/Login-icon.png"));
////            stage.getIcons().add(applicationIcon);
////            stage.setResizable(false);
//            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
//            stage.setTitle("Login");
////            stage.initStyle(StageStyle.DECORATED);
//            stage.setScene(scene);
//            stage.show();
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
//             scene.getStylesheets().add(getClass().getResource("/css/register.css").toExternalForm());
//            stage.setScene(scene);
//            stage.setResizable(false);
//            stage.show();
//
//        }

        //End
//
//        } else {
//            rs.close();
//            pst.close();
//            con.close();
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginFirstTime.fxml"));
//
//            Scene scene = new Scene(root);
//
//            stage.setScene(scene);
//            stage.show();
//            rs.close();
//            pst.close();
//            con.close();
//
//        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
