package com.TurizmAcenteSistemi.view;

import com.TurizmAcenteSistemi.model.Hotel;
import com.TurizmAcenteSistemi.model.User;

import javax.swing.*;
import com.TurizmAcenteSistemi.helper.Config;
import com.TurizmAcenteSistemi.helper.Helper;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.sql.SQLException;

public class HotelGUI extends JFrame{
    private JPanel wrapper;
    private JButton btn_hotel_add;
    private JButton btn_room_add;
    private JButton btn_room_price;
    private JButton btn_geri;
    private JTable tbl_hotel_list;
    private JLabel lbl_welcome;
    private DefaultTableModel mdl_hotel_list;
    private Object[] row_hotel_list;
    private User user;
    private String hotel_name;

    public HotelGUI(User user){
        this.user = user;
        add(wrapper);
        setSize(1000,500);
        int x = Helper.scCenter("x",getSize());
        int y = Helper.scCenter("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hosgeldiniz " + user.getFirst_name() + " " + user.getLast_name());

        mdl_hotel_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_user_list = {"Otel Adı" , "Adres", "E-Posta", "Telefon", "Pansiyon Tipi","Şehir","Yıldız","Tesis Özellikleri"};
        mdl_hotel_list.setColumnIdentifiers(col_user_list);
        row_hotel_list = new Object[col_user_list.length];
        loadHotelModel();
        tbl_hotel_list.setModel(mdl_hotel_list);
        tbl_hotel_list.getTableHeader().setReorderingAllowed(false);

        btn_hotel_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HotelAddGUI hotelAddGUI = new HotelAddGUI(user);
                dispose();
            }
        });
        btn_geri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI loginGUI = new LoginGUI();
                dispose();
            }
        });

        tbl_hotel_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_hotel_list.getSelectedRow();
                tbl_hotel_list.setRowSelectionInterval(0,selected_row);
                hotel_name = tbl_hotel_list.getValueAt(selected_row,0).toString();
                System.out.println(hotel_name);

            }
        });
        btn_room_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hotel_name != null) {
                    RoomAddGUI roomAddGUI = new RoomAddGUI(hotel_name);
                }else {
                    Helper.showMsg("Lütfen bir otel seçiniz.");
                }

            }
        });
        btn_room_price.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hotel_name != null) {
                    RoomPriceGUI rooomPricingGUI = new RoomPriceGUI(hotel_name);
                }else {
                    Helper.showMsg("Lütfen bir otel seçiniz.");
                }
            }
        });

    }

    private void loadHotelModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for(Hotel obj: Hotel.getList()){
            i=0;
            row_hotel_list[i++] = obj.getName();
            row_hotel_list[i++] = obj.getAddress();
            row_hotel_list[i++] = obj.getEmail();
            row_hotel_list[i++] = obj.getPhone();
            row_hotel_list[i++] = obj.getTesis_ozellikleri();
            row_hotel_list[i++] = obj.getCity();
            row_hotel_list[i++] = obj.getStar();
            row_hotel_list[i++] = parseArray(obj.getPansiyon_tipi());

            mdl_hotel_list.addRow(row_hotel_list);

        }
    }
    private String parseArray(Array pansiyonTipi){
        StringBuilder stringBuilder = new StringBuilder();
        String[] arr;
        try {
            arr = (String[]) pansiyonTipi.getArray();
            for (String s:arr) {
                stringBuilder.append(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }
}
