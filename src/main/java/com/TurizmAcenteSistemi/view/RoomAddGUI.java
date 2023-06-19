package com.TurizmAcenteSistemi.view;

import com.TurizmAcenteSistemi.helper.Config;
import com.TurizmAcenteSistemi.helper.Helper;
import com.TurizmAcenteSistemi.model.Hotel;
import com.TurizmAcenteSistemi.model.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomAddGUI extends JFrame{
    private JPanel wrapper;
    private JLabel lbl_hotel;
    private JComboBox cmb_type;
    private JTextField fld_bed;
    private JTextField fld_stok;
    private JCheckBox chx_tv;
    private JCheckBox chx_minibar;
    private JCheckBox chx_oyun;
    private JButton btn_roomAdd;
    private Hotel hotel;
    private final String[] roomList = {"Single","Double","Suit"};
    public RoomAddGUI(String hotelName){//Sadece otel adı da alabalir.yada olmayablir.
        this.hotel = Hotel.getHotelByName(hotelName);
        add(wrapper);
        setSize(750,500);
        int x = Helper.scCenter("x",getSize());
        int y = Helper.scCenter("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        loadRoomTypeCombo();
        lbl_hotel.setText(hotelName);

        btn_roomAdd.addActionListener(e -> {
            String room_type = cmb_type.getSelectedItem().toString();
            int stock = Integer.parseInt(fld_stok.getText());
            int hotel_id = hotel.getId();
            int bed_num = Integer.parseInt(fld_bed.getText());
            boolean tv = chx_tv.isSelected();
            boolean minibar = chx_minibar.isSelected();
            boolean safe = chx_oyun.isSelected();

            if (Room.isRoomTypeAdded(room_type,hotel_id) == 0){
                if (Room.add(room_type,stock,hotel_id,bed_num,tv,minibar,safe)){
                    Helper.showMsg("done");
                }else{
                    Helper.showMsg("error");
                }
            }else {
                Helper.showMsg("Bu otele ait " + room_type + " tipinde oda daha önce eklenmiştir.");
            }
        });

    }
    public void loadRoomTypeCombo(){
        cmb_type.removeAllItems();
        for (String s : roomList){
            cmb_type.addItem(s);
        }
    }
}
