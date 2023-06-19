package com.TurizmAcenteSistemi.model;

import com.TurizmAcenteSistemi.helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String uname;
    private String email;
    private String pass;
    private String type;
    public User(int id, String first_name, String last_name, String uname, String email, String pass, String type) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.uname = uname;
        this.email = email;
        this.pass = pass;
        this.type = type;
    }
    public int getId() {
        return id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getUname() {
        return uname;
    }
    public String getEmail() {
        return email;
    }
    public String getPass() {
        return pass;
    }
    public String getType() {
        return type;
    }

    public static User getFetch(String uname_email, String pass){
        User obj = null;
        String query = "SELECT * FROM user WHERE uname=? AND pass=?";
        if (uname_email.contains("@")){
            query = "SELECT * FROM user WHERE email=? AND pass=?";
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,uname_email);
            pr.setString(2,pass);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new User(rs.getInt("id"),rs.getString("first_name"), rs.getString("last_name"),rs.getString("uname"), rs.getString("email"),rs.getString("pass"),rs.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }
}
