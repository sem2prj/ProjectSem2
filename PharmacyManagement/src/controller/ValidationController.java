/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.PasswordField;
import java.util.regex.*;

/**
 *
 * @author admin
 */
public class ValidationController {

    public static boolean isTextFieldHavingText(TextField tf) {
        boolean b = false;
        if (tf.getText().length() != 0 || !tf.getText().isEmpty()) {
            b = true;
        }

        return b;
    }

    public static boolean isTextFieldHavingText(TextField tf, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        if (!isTextFieldHavingText(tf)) {
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }

    public static boolean isTextFieldTypeNumber(TextField tf) {
        boolean b = false;
        if (tf.getText().matches("([0-9]+(\\.[0-9]+)?)+")) {
            b = true;
        }
        return b;
    }

    public static boolean isTextFieldTypeNumber(TextField tf, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        if (!isTextFieldTypeNumber(tf)) {
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }

    public static boolean isPasswordFieldHavingText(PasswordField pf) {
        boolean b = false;
        if (pf.getText().length() != 0 | !pf.getText().isEmpty()) {
            b = true;
        }

        return b;
    }

    public static boolean isPasswordFieldHavingText(PasswordField pf, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        if (!isPasswordFieldHavingText(pf)) {
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }

    public static boolean arePasswordAndREPasswordSame(PasswordField pf1, PasswordField pf2, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        char[] str1 = pf1.getText().toCharArray();
        char[] str2 = pf2.getText().toCharArray();
        if (!Arrays.equals(str1, str2)) {
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean sosanhchuoi(String str1, String str2) {
        boolean logic = false;
        char[] a = str1.toCharArray();
        char[] b = str2.toCharArray();
        if (Arrays.equals(a, b)) {
            logic = true;
        }

        return logic;
    }

    public static boolean isEmailSuitable(TextField tf) {
        boolean b = false;
        Pattern p = Pattern.compile("^[a-z][a-z0-9_\\.]{0,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        Matcher m = p.matcher(tf.getText());
        boolean a = m.matches();
        if (a == true) {
            b = true;
        }
        return b;
    }

    public static boolean isEmailSuitable(TextField tf, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        if (!isEmailSuitable(tf)) {
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }

    public static boolean isPhoneSuitable(TextField tf) {
        boolean b = false;
        Pattern p = Pattern.compile("^\\+?\\d{1,3}?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$");
        Matcher m = p.matcher(tf.getText());
        boolean a = m.matches();
        if (a == true) {
            b = true;
        }
        return b;

    }

    public static boolean isPhoneSuitable(TextField tf, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        if (!isPhoneSuitable(tf)) {
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;

    }

    public static boolean isUsernameTrueType(TextField tf) {
        boolean b = false;
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{3,15}$");
        Matcher m = p.matcher(tf.getText());
        boolean a = m.matches();
        if (a == true) {
            b = true;
        }
        return b;
    }

    public static boolean isUsernameTrueType(TextField tf, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        if (!isUsernameTrueType(tf)) {
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;

    }
// Regex Password : Không có khoảng trắng, kéo dài 7-16 ký tự, nghĩa là chấp nhận từ a-z A-Z 0-9 và ký tự đặc biệt

    public static boolean isPasswordTrueType(TextField tf) {
        boolean b = false;
        Pattern p = Pattern.compile("^(?=\\S+$).{7,16}$");
        Matcher m = p.matcher(tf.getText());
        boolean a = m.matches();
        if (a == true) {
            b = true;
        }
        return b;

    }

    public static boolean isPasswordTrueType(TextField tf, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        if (!isPasswordTrueType(tf)) {
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;

    }
    
     public static boolean isTextFieldHavingBarcode(TextField tf){
        boolean b = false;
        if (tf.getText().length()!= 0 | !tf.getText().isEmpty()|tf.getText().length()<13){
            b = true;
        }
        
        return b;
    }
    
    public static boolean isTextFieldHavingBarcode(TextField tf,Label lb, String errorMessage){
        boolean b = true;
        String msg = null;
        if(!isTextFieldHavingText(tf)){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }
    
     
    public static boolean isTextFieldNotEmpty(TextField tf) {
        boolean b = false;
        if (tf.getText().length() != 0 || !tf.getText().isEmpty()) {
            b = true;
        }
        return b;
    }
    
    public static boolean isTextFieldNotEmpty(TextField tf, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        if (!isTextFieldNotEmpty(tf)) {
            
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        
        return b;
    }
    
//    public static boolean isIntegerValueNegative(TextField tf){
//        boolean b = false;
//        int i = Integer.parseInt(tf.getText());
//        if(Integer.signum(i)==-1){
//            b = true;
//        }
//        return b;
//    }
//    
//    public static boolean isIntegerValueNegative(TextField tf, Label lb, String errorMessage){
//        boolean b = true;
//        String msg = null;
//        if(!isIntegerValueNegative(tf)){
//            b = false;
//            msg = errorMessage;
//        
//        }
//        lb.setText(msg);
//        return b;
//    }
    
    
// Regex Qty : Khong duoc bat dau tu so 0, hoac so am , range value 1-999     
    public static boolean isQtySuitable(TextField tf){
   
        boolean b = false;
        Pattern p = Pattern.compile("^[1-9][0-9]{0,2}$");
        Matcher m = p.matcher(tf.getText());
        boolean a = m.matches();
        if (a == true) {
            b = true;
        }
        return b;
    
        
    }
    
    public static boolean isQtySuitable(TextField tf, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        if (!isQtySuitable(tf)) {
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;

    }
    
    public static boolean checkPolymerCode(String code){
        boolean check=true;
        String sql="SELECT Code FROM DetailUser";
        try {
            Connection connection = controller.ConnectDB.connectSQLServer();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {                
                String supercode=rs.getString("Code");
                System.out.println(supercode);
                if (supercode==code) {
                    check=false;
                }
            }
                  
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ValidationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return check;
        
    }
    
    
// Vi du : Nguyen Van Tam 0908888888 se tra ve Nguyen Van Tam    
    public static String getStringFromText(String s) {
        String abc = "";
        String d = "";
        Pattern pattern = Pattern.compile("([A-Za-z_])+");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            abc += matcher.group() + " ";
        }
        d = abc.trim();
        return d;

    }
  
// Vi du : Nguyen Van Tam 0908888888 se tra ve 0908888888    
    public static String getNumberFromText(String s){
        String abc = "";
        String d = "";
        Pattern pattern = Pattern.compile("([0-9_])+");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            abc += matcher.group() + " ";
        }
        d = abc.trim();
        return d;
    }
    
//Vi du : 100000000 se tra ve 100.000.000
    public static String addDotForASeriesOfNumber(String s){
        String abc = "";
        Pattern pattern = Pattern.compile("([0-9][0-9][0-9])");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            abc += matcher.group() + ".";
        }
        abc = abc.substring(0, abc.length() - 1);
        abc = abc.trim();
        
        return abc;
    }
    
}


//    @([A-Za-z0-9_]{3,15})

//^[1-9][0-9]{0,2}$


//validate 
//username : ^[a-zA-Z0-9]{3,15}$
//password : ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$
//
//^                 # start-of-string
//(?=.*[0-9])       # a digit must occur at least once
//(?=.*[a-z])       # a lower case letter must occur at least once
//(?=.*[A-Z])       # an upper case letter must occur at least once
//(?=.*[@#$%^&+=])  # a special character must occur at least once
//(?=\S+$)          # no whitespace allowed in the entire string
//.{8,}             # anything, at least eight places though
//$                 # end-of-string
//
//https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
