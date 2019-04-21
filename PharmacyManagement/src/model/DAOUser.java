/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public interface DAOUser {
    ObservableList<User> getAllUser();
    void updateUser(String code,String password);
    void updateUserPass(String code,String password);
}
