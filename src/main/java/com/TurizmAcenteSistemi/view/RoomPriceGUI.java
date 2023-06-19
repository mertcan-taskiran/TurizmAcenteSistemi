package com.TurizmAcenteSistemi.view;

import com.TurizmAcenteSistemi.helper.DBConnector;
import com.TurizmAcenteSistemi.model.Hotel;

import javax.swing.*;
import com.TurizmAcenteSistemi.helper.Config;
import com.TurizmAcenteSistemi.helper.Helper;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class RoomPriceGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_ultra;
    private JTextField fld_all;
    private JTextField fld_kahvalti;
    private JTextField fld_full;
    private JTextField fld_half;
    private JTextField fld_bed;
    private JTextField fld_alkol;
    private JLabel lbl_ultra;
    private JLabel lbl_all;
    private JLabel lbl_kahvalti;
    private JLabel lbl_full;
    private JLabel lbl_half;
    private JLabel lbl_bed;
    private JLabel lbl_alkol;
    private JButton btn_save;
    private Hotel hotel;

    public RoomPriceGUI(String hotel_name){
        this.hotel = Hotel.getHotelByName(hotel_name);
        add(wrapper);
        setSize(750,500);
        int x = Helper.scCenter("x",getSize());
        int y = Helper.scCenter("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        loadLblText();

        btn_save.addActionListener(e -> {
            int j = 0;
            String pension_type;
            int price;
            int hotel_id = hotel.getId();
            int pension_price[] = new int[Config.PANSIYON_TIP.length];

            pension_price[j++] = Integer.parseInt(fld_ultra.getText());
            pension_price[j++] = Integer.parseInt(fld_all.getText());
            pension_price[j++] = Integer.parseInt(fld_kahvalti.getText());
            pension_price[j++] = Integer.parseInt(fld_full.getText());
            pension_price[j++] = Integer.parseInt(fld_half.getText());
            pension_price[j++] = Integer.parseInt(fld_bed.getText());
            pension_price[j++] = Integer.parseInt(fld_alkol.getText());
            if (setRoomPrice(hotel_id,pension_price)){
                Helper.showMsg("done");
                dispose();
            }else{
                Helper.showMsg("error");
            }
        });
    }

    private void loadLblText(){
        int i = 0;
        lbl_ultra.setText(Config.PANSIYON_TIP[i++] + ":");
        lbl_all.setText(Config.PANSIYON_TIP[i++] + ":");
        lbl_kahvalti.setText(Config.PANSIYON_TIP[i++] + ":");
        lbl_full.setText(Config.PANSIYON_TIP[i++] + ":");
        lbl_half.setText(Config.PANSIYON_TIP[i++] + ":");
        lbl_bed.setText(Config.PANSIYON_TIP[i++] + ":");
        lbl_alkol.setText(Config.PANSIYON_TIP[i++] + ":");
    }

    private boolean setRoomPrice(int hotel_id, int[] pension_price){
        String query = "INSERT INTO room_price (pansiyon,hotel_id,price) VALUES (?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            for (int i = 0; i < Config.PANSIYON_TIP.length; i++) {
                pr.setObject(1,Config.PANSIYON_TIP[i], Types.OTHER);
                pr.setInt(2,hotel_id);
                pr.setInt(3,pension_price[i]);
                pr.addBatch();
            }
            pr.executeBatch();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
