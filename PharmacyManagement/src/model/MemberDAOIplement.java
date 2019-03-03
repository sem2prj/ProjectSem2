 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public class MemberDAOIplement implements DAOMember{

    @Override
    public ObservableList<Member> getAllMember() {
        ObservableList<Member> listMember = FXCollections.observableArrayList();
        String sql = "select * from users";
        try (Connection connection = controller.ConnectDB.connectSQLServer();
                Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Member member = new Member();
                member.setuserName(rs.getString("name_user"));
                member.setpassword(rs.getString("password_user"));
                member.setrole(rs.getString("role_user"));
                listMember.add(member);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MemberDAOIplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listMember;
    }
    
}
