/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author PC
 */
public class AlertDialog {
//    public static void display(String title, String message){
//        // Empty window
//        Stage window = new Stage();
//        window.setTitle(title);
//        window.setMinWidth(300);
//        window.setMaxHeight(150);
//        window.getIcons().add(new Image("/image/hyhy.png"));
//        
//        
//        Label label = new Label();
//        label.setText(message);
//        Button buttonOk = new Button("OK");
//        buttonOk.setOnAction(e -> window.close());
//        
//        VBox layout = new VBox(5);
//        layout.getChildren().addAll(label, buttonOk);
//        layout.setAlignment(Pos.CENTER);
//        
//        Scene scene = new Scene(layout);
//        
//        window.setScene(scene);
//        window.showAndWait();
//    }
    public static void display(String title, String message){
        // Empty window
        Stage window = new Stage();
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMaxHeight(150);
        window.getIcons().add(new Image("/image/hyhy.png"));
        
        
        Label label = new Label();
        label.setText(message);
        Button buttonOk = new Button("OK");
        buttonOk.setOnAction(e -> window.close());
        
        VBox layout = new VBox(5);
        layout.getChildren().addAll(label, buttonOk);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        
        window.setScene(scene);
        window.showAndWait();
    }
    
}
