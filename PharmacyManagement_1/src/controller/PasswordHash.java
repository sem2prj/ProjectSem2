/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
//import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import java.util.Scanner;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
//import sun.applet.Main;

/**
 *
 * @author admin
 */
public class PasswordHash {
    public static String encryptPass(String pass) {
        //MD5Encrypt
        String passEncrypt;

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            
        }
        md5.update(pass.getBytes());
        byte[] digest = md5.digest();
        passEncrypt = DatatypeConverter.printBase64Binary(digest).toUpperCase();
        
        return passEncrypt;

    }
    
    
}
