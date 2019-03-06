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
import java.util.regex.*;  

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
    
    public static boolean isEmailSuitable(TextField tf){
        boolean b = false;
        Pattern p = Pattern.compile("^[a-z][a-z0-9_\\.]{0,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        Matcher m = p.matcher(tf.getText());
        boolean a = m.matches();
        if (a==true){
            b = true;
        }
        return b;
    }
    
    public static boolean isEmailSuitable(TextField tf,Label lb, String errorMessage){
        boolean b = true;
        String msg = null;
        if(!isEmailSuitable(tf)){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean isPhoneSuitable(TextField tf){
        boolean b= false;
        Pattern p = Pattern.compile("^\\+?\\d{1,3}?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$");
        Matcher m = p.matcher(tf.getText());
        boolean a = m.matches();
        if (a==true){
            b= true;
        }
        return b;
    
    }
    
    public static boolean isPhoneSuitable(TextField tf,Label lb,String errorMessage){
        boolean b= true;
        String msg = null;
        if(!isPhoneSuitable(tf)){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
        
    }
    

    public static boolean isUsernameTrueType(TextField tf){
        boolean b= false;
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{3,15}$");
        Matcher m = p.matcher(tf.getText());
        boolean a = m.matches();
        if (a==true){
            b= true;
        }
        return b;
    }
    
    public static boolean isUsernameTrueType(TextField tf,Label lb,String errorMessage){
        boolean b= true;
        String msg = null;
        if(!isUsernameTrueType(tf)){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
        
    }
// Regex Password : Không có khoảng trắng, kéo dài 7-16 ký tự, nghĩa là chấp nhận từ a-z A-Z 0-9 và ký tự đặc biệt
    
    
   public static boolean isPasswordTrueType(TextField tf){
        boolean b= false;
        Pattern p = Pattern.compile("^(?=\\S+$).{7,16}$");
        Matcher m = p.matcher(tf.getText());
        boolean a = m.matches();
        if (a==true){
            b= true;
        }
        return b;
   
   }
   
   public static boolean isPasswordTrueType(TextField tf,Label lb,String errorMessage){
        boolean b= true;
        String msg = null;
        if(!isPasswordTrueType(tf)){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
   
   }
   
    }
    
    
//    @([A-Za-z0-9_]{3,15})

