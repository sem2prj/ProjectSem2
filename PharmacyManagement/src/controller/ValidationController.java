/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.Arrays;
import javafx.scene.control.PasswordField;

/**
 *
 * @author admin
 */
public class ValidationController {
    public static boolean isTextFieldHavingText(TextField tf){
        boolean b = false;
        if (tf.getText().length()!= 0 | !tf.getText().isEmpty()){
            b = true;
        }
        
        return b;
    }
    
    public static boolean isTextFieldHavingText(TextField tf,Label lb, String errorMessage){
        boolean b = true;
        String msg = null;
        if(!isTextFieldHavingText(tf)){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }
    
    
    
    
    public static boolean isTextFieldTypeNumber(TextField tf){
        boolean b = false;
        if(tf.getText().matches("([0-9]+(\\.[0-9]+)?)+"))
        {b = true;}
       return b;         
    }

    public static boolean isTextFieldTypeNumber(TextField tf,Label lb, String errorMessage){
        boolean b = true;
        String msg = null;
        if(!isTextFieldTypeNumber(tf)){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }
    
        public static boolean isPasswordFieldHavingText(PasswordField pf){
        boolean b = false;
        if (pf.getText().length()!= 0 | !pf.getText().isEmpty()){
            b = true;
        }
        
        return b;
    }
        
        public static boolean isPasswordFieldHavingText(PasswordField pf,Label lb, String errorMessage){
        boolean b = true;
        String msg = null;
        if(!isPasswordFieldHavingText(pf)){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean arePasswordAndREPasswordSame(PasswordField pf1,PasswordField pf2,Label lb,String errorMessage){
        boolean b = true;
        String msg = null;
        char[] str1 = pf1.getText().toCharArray();
        char[] str2 = pf2.getText().toCharArray();
         if(!Arrays.equals(str1,str2)){
             b= false;
             msg= errorMessage;
         } 
        lb.setText(msg);
        return b;
    }
    
    
}
