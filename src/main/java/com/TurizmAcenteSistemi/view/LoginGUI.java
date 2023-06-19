package com.TurizmAcenteSistemi.view;

import javax.swing.*;
import com.TurizmAcenteSistemi.helper.Config;
import com.TurizmAcenteSistemi.helper.Helper;
import com.TurizmAcenteSistemi.model.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_uname;
    private JPasswordField fld_pass;
    private JButton btn_login;
    private JButton btn_register;

    public LoginGUI(){
        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.scCenter("x",getSize()),Helper.scCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        fld_pass.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    login();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        btn_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterGUI registerGui = new RegisterGUI();
                dispose();
            }
        });


    }

    private void login(){
        if (Helper.isFieldEmpty(fld_uname) || Helper.isFieldEmpty(fld_pass)){
            Helper.showMsg("fill");
        }else{
            User user = User.getFetch(fld_uname.getText(),fld_pass.getText());
            if (user == null) {
                Helper.showMsg("Kullanıcı Bulunamadı");
            }else{
                switch (user.getType()) {
                    case "operator" -> {
                        RezGUI rezListGUI = new RezGUI(user);
                        dispose();
                    }
                    case "admin" -> {
                        HotelGUI hotelGUI = new HotelGUI(user);
                        dispose();
                    }
                }

            }
        }
    }
}
