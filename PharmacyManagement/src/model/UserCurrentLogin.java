/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
public class UserCurrentLogin {
    private static String currentLogin;

    public UserCurrentLogin() {
    }

    public static String getCurrentLogin() {
        return currentLogin;
    }

    public static void setCurrentLogin(String currentLogin) {
        UserCurrentLogin.currentLogin = currentLogin;
    }
    
    
}
