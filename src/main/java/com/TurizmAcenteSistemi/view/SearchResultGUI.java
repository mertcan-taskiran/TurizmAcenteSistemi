package com.TurizmAcenteSistemi.view;

import com.TurizmAcenteSistemi.helper.DBConnector;
import com.TurizmAcenteSistemi.model.Hotel;
import com.TurizmAcenteSistemi.model.User;
import com.TurizmAcenteSistemi.helper.Config;
import com.TurizmAcenteSistemi.helper.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;


public class SearchResultGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_city;
    private JTextField fld_address;
    private JTextField fld_phone;
    private JTextField fld_giris;
    private JTextField fld_cikis;
    private JLabel lbl_ultra;
    private JLabel lbl_all;
    private JLabel lbl_half;
    private JLabel lbl_bed;
    private JLabel lbl_kahvalti;
    private JLabel lbl_alkol;
    private JLabel lbl_full;
    private JButton btn_rez;
    private JComboBox cmb_pansiyon;
    private JLabel lbl_hotel_name;
    private JLabel lbl_star;
    private JButton btn_logout;
    private JLabel lbl_tv;
    private JLabel lbl_oyun;
    private JLabel lbl_minibar;
    private JLabel lbl_room_type;
    private ArrayList<String> pensionTypeList = new ArrayList<>();
    private ArrayList<String> pensionPriceList = new ArrayList<>();
    private int room_id;
    private final ArrayList<JLabel> labelList = new ArrayList<>(Arrays.asList(lbl_ultra,lbl_all,lbl_kahvalti,lbl_full,lbl_half,lbl_bed,lbl_alkol));
    public SearchResultGUI(User user, Hotel hotel, ArrayList<Integer> rooms, Date checkIn, Date checkOut, int numAdult, int numChild){
        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.scCenter("x",getSize()),Helper.scCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        fld_address.setEditable(false);
        fld_giris.setEditable(false);
        fld_cikis.setEditable(false);
        fld_city.setEditable(false);
        fld_phone.setEditable(false);


        int hotel_id = hotel.getId();
        int rezDate = calculateRezDate(checkIn, checkOut);
        String room_type = getRoomType(hotel.getId(),numAdult);

        lbl_hotel_name.setText(hotel.getName());
        lbl_star.setText(hotel.getStar());
        fld_address.setText(hotel.getAddress());
        fld_giris.setText(String.valueOf(checkIn));
        fld_cikis.setText(String.valueOf(checkOut));
        fld_city.setText(hotel.getCity());
        fld_phone.setText(hotel.getPhone());
        lbl_room_type.setText( room_type + " Oda");
        loadPensionList(hotel_id,rezDate);
        loadPensionCombo();
        getRoomInfo(room_id);


        btn_rez.addActionListener(e -> {
            System.out.println(calculatePayment(rezDate));
            RezCreatedGUI rezCreatedGUI = new RezCreatedGUI(user,hotel_id,room_id,room_type,numAdult,numChild,checkIn,checkOut);
        });

        btn_logout.addActionListener(e -> {
            SearchGUI searchGUI = new SearchGUI(user);
            dispose();
        });
    }

    private int calculatePayment(int rezDate){
        String selectedPension = cmb_pansiyon.getSelectedItem().toString();
        int pensionIndx = pensionTypeList.indexOf(selectedPension);
        int selectedPensionPrice = Integer.parseInt(pensionPriceList.get(pensionIndx));
        return selectedPensionPrice * rezDate;
    }

    private int calculateRezDate(Date checkIn, Date checkOut){
        String chekinDateStr = String.valueOf(checkIn);
        String chekouDateStr = String.valueOf(checkOut);
        String[] partsOfCheckin = chekinDateStr.split("-");
        String[] partsOfCheckOut = chekouDateStr.split("-");

        int yearToDay = (Integer.parseInt(partsOfCheckOut[0]) - Integer.parseInt(partsOfCheckin[0])) * 365;
        System.out.println(yearToDay);
        int monthToDay = (Integer.parseInt(partsOfCheckOut[1]) - Integer.parseInt(partsOfCheckin[1])) * 30;
        System.out.println(monthToDay);
        int day =  (Integer.parseInt(partsOfCheckOut[2]) - Integer.parseInt(partsOfCheckin[2]));
        System.out.println(day);
        return yearToDay + monthToDay + day;

    }

    private void getRoomInfo(int room_id){
        int bed_num =0;
        boolean tv = true,minibar = true, safe = true;
        String query = "SELECT bed_num,tv,minibar,safe FROM room WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,room_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                bed_num = rs.getInt("bed_num");
                tv = rs.getBoolean("tv");
                minibar = rs.getBoolean("minibar");
                safe = rs.getBoolean("safe");
            }
            loadRoomInfo(bed_num,tv,minibar,safe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadRoomInfo(int bed_num,boolean tv, boolean minibar,boolean safe){
        lbl_bed.setText("Yatak : " + bed_num +" Adet");
        if (tv) {
            lbl_tv.setText("Televizyon : Var");
        }else {
            lbl_tv.setText("Televizyon : Yok");
        }
        if (minibar) {
            lbl_minibar.setText("Minibar : Var");
        }else {
            lbl_minibar.setText("Minibar : Yok");
        }
        if (safe) {
            lbl_oyun.setText("Oyun : Var");
        }else {
            lbl_oyun.setText("Oyun : Yok");
        }
    }

    private void getPensionList(int hotel_id){
        String query = "SELECT pension_type,price FROM room_price WHERE hotel_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                pensionTypeList.add(rs.getString("pansiyon"));
                pensionPriceList.add(String.valueOf(rs.getInt("price")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadPensionList(int hotel_id,int rezDate){
        getPensionList(hotel_id);
        for (int i = 0; i < labelList.size(); i++) {
            labelList.get(i).setText(pensionTypeList.get(i) +" : " + Integer.parseInt(pensionPriceList.get(i)) * rezDate + "TL");
        }

    }
    private String getRoomType(int hotel_id,int guestNum){

        String query = "SELECT id,room_type,bed_num FROM room WHERE hotel_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                if (rs.getInt("bed_num") >= guestNum){
                    room_id = rs.getInt("id");
                    return rs.getString("room_type");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void loadPensionCombo(){
        cmb_pansiyon.removeAllItems();
        for (String s:pensionTypeList) {
            cmb_pansiyon.addItem(s);
        }
    }
}
