package com.TurizmAcenteSistemi.view;

import com.TurizmAcenteSistemi.model.Hotel;

import javax.swing.*;
import com.TurizmAcenteSistemi.helper.Config;
import com.TurizmAcenteSistemi.helper.Helper;
import com.TurizmAcenteSistemi.model.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class HotelAddGUI extends JFrame{
    private JPanel wrapper;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JTextField fld_name;
    private JComboBox cmb_star;
    private JTextField fld_city;
    private JTextField fld_email;
    private JTextField fld_phone;
    private JTextArea fld_address;
    private JComboBox cmb_pansiyon;
    private JCheckBox chckbx_wifi;
    private JCheckBox chckbx_otopark;
    private JCheckBox chckbx_havuz;
    private JCheckBox chckbx_spor;
    private JCheckBox chckbx_gorevli;
    private JCheckBox chckbx_spa;
    private JCheckBox chckbx_service;
    private JButton btn_addHotel;
    private Hotel hotel;
    private final ArrayList<String> serviceSpecList = new ArrayList<>();

    public HotelAddGUI(User user){
        add(wrapper);
        setSize(700,600);
        setLocation(Helper.scCenter("x",getSize()),Helper.scCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        fld_address.setWrapStyleWord(true);
        fld_address.setLineWrap(true);
        loadComboStar();
        loadComboPansiyon();
        lbl_welcome.setText("Hoşgeldiniz, " + user.getFirst_name() + " " + user.getLast_name());

        chckbx_otopark.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                serviceSpecList.add(chckbx_otopark.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                serviceSpecList.remove(chckbx_otopark.getText());
            }
        });

        chckbx_wifi.addItemListener((ItemListener) e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                serviceSpecList.add(chckbx_wifi.getText());
            }else if (e.getStateChange() == ItemEvent.DESELECTED) {
                serviceSpecList.remove(chckbx_wifi.getText());
            }
        });

        chckbx_havuz.addItemListener((ItemListener) e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                serviceSpecList.add(chckbx_havuz.getText());
            }else if (e.getStateChange() == ItemEvent.DESELECTED) {
                serviceSpecList.remove(chckbx_havuz.getText());
            }
        });

        chckbx_gorevli.addItemListener((ItemListener) e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                serviceSpecList.add(chckbx_gorevli.getText());
            }else if (e.getStateChange() == ItemEvent.DESELECTED) {
                serviceSpecList.remove(chckbx_gorevli.getText());
            }
        });

        chckbx_spa.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                serviceSpecList.add(chckbx_spa.getText());
            }else if (e.getStateChange() == ItemEvent.DESELECTED) {
                serviceSpecList.remove(chckbx_spa.getText());
            }
        });

        chckbx_spor.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                serviceSpecList.add(chckbx_spor.getText());
            }else if (e.getStateChange() == ItemEvent.DESELECTED) {
                serviceSpecList.remove(chckbx_spor.getText());
            }
        });

        chckbx_service.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                serviceSpecList.add(chckbx_service.getText());
            }else if (e.getStateChange() == ItemEvent.DESELECTED) {
                serviceSpecList.remove(chckbx_service.getText());
            }
        });

        btn_addHotel.addActionListener(e -> {
            String name = fld_name.getText();
            String star = cmb_star.getSelectedItem().toString();
            String address = fld_address.getText();
            String city = fld_city.getText();
            String email = fld_email.getText();
            String phone = fld_phone.getText();

            String pansiyon = cmb_pansiyon.getSelectedItem().toString();
            System.out.println(serviceSpecList);

            if (Helper.isFieldEmpty(fld_name) || Helper.isFieldEmpty(fld_email) || Helper.isFieldEmpty(fld_city) || Helper.isFieldEmpty(fld_phone)){
                Helper.showMsg("fill");
            }else {
                if (Hotel.add(name,address,email,phone,pansiyon,serviceSpecList,city,star)){
                    Helper.showMsg("Hotel Başarıyla Eklendi");
                }else {
                    Helper.showMsg("error");
                }
            }
        });

        btn_logout.addActionListener(e -> {
            HotelGUI hotelGUI = new HotelGUI(user);
            dispose();
        });

    }

    private void loadComboStar(){
        cmb_star.removeAllItems();
        for (String s: Config.START_LIST) {
            cmb_star.addItem(s);
        }
    }
    private void loadComboPansiyon(){
        cmb_pansiyon.removeAllItems();
        for (String s: Config.PANSIYON_TIP) {
            cmb_pansiyon.addItem(s);
        }
    }
}
