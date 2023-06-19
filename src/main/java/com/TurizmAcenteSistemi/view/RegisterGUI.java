package com.TurizmAcenteSistemi.view;

import javax.swing.*;
import com.TurizmAcenteSistemi.helper.Config;
import com.TurizmAcenteSistemi.helper.DBConnector;
import com.TurizmAcenteSistemi.helper.Helper;
import com.TurizmAcenteSistemi.model.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class RegisterGUI extends JFrame{

    private JPanel wrapper;
    private JTextField fld_email;
    private JTextField fld_uname;
    private JTextField fld_name;
    private JTextField fld_lastName;
    private JPasswordField fld_pass;
    private JButton btn_register;

    public RegisterGUI(){
        add(wrapper);
        setSize(600,500);
        setLocation(Helper.scCenter("x",getSize()),Helper.scCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = fld_email.getText();
                String uname = fld_uname.getText();
                String name = fld_name.getText();
                String lastName = fld_lastName.getText();
                String pass = fld_pass.getText();
                String type = "operator";

                if (Helper.isFieldEmpty(fld_email) || Helper.isFieldEmpty(fld_uname) || Helper.isFieldEmpty(fld_name) || Helper.isFieldEmpty(fld_lastName) || Helper.isFieldEmpty(fld_pass)){
                    Helper.showMsg("fill");
                }else{
                    if (isEmailValid(email)){
                        if (addNewUser(email, uname, name,lastName,pass,type)){
                            Helper.showMsg("done");
                            LoginGUI loginGUI = new LoginGUI();
                            dispose();
                        }else {
                            Helper.showMsg("error");
                        }
                    }
                }
            }
        });
    }

    private boolean isEmailValid(String mail){
        if (mail.contains("@gmail.com") || mail.contains("@hotmail.com")){
            return true;
        }else {
            Helper.showMsg("Lütfen geçerli bir email girin.");
            return false;
        }
    }

    private boolean addNewUser(String first_name, String last_name, String uname, String email, String pass,String type){
        String query = "INSERT INTO user (first_name,last_name,uname,email,pass,type) VALUES (?,?,?,?,?,?)";

        User findUser = User.getFetch(uname, pass);
        if (findUser != null){
            Helper.showMsg("Bu kullancı adı alınmış. Lütfen farklı bir kullanıcı giriniz.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,first_name);
            pr.setString(2,last_name);
            pr.setString(3,uname);
            pr.setString(4,email);
            pr.setString(5,pass);
            pr.setObject(6,type, Types.OTHER);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;

    }

}
