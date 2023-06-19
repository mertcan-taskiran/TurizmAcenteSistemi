package com.TurizmAcenteSistemi.view;

import com.TurizmAcenteSistemi.model.User;
import com.TurizmAcenteSistemi.helper.Config;
import com.TurizmAcenteSistemi.helper.DBConnector;
import com.TurizmAcenteSistemi.helper.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RezGUI extends JFrame{
    private JPanel wrapper;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JButton btn_rez;
    private JTable tbl_rezList;
    private final User user;

    private DefaultTableModel mdl_rez_list;
    private Object[] row_rez_list;

    public RezGUI(User user){
        this.user = user;
        add(wrapper);
        setSize(1000,500);
        int x = Helper.scCenter("x",getSize());
        int y = Helper.scCenter("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldiniz, " + user.getFirst_name() + " " + user.getLast_name());

        mdl_rez_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row,column);
            }
        };

        Object[] col_rez_list = {"Misafir-Ad Soyad","Oda No","İletişim-Ad Soyad","İletişim-Telefon Numarası","Rezervasyon Notu"};
        mdl_rez_list.setColumnIdentifiers(col_rez_list);
        row_rez_list = new Object[col_rez_list.length];
        loadRezListModel();
        tbl_rezList.setModel(mdl_rez_list);
        tbl_rezList.getTableHeader().setReorderingAllowed(false);

        btn_rez.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchGUI searchRoomGUI = new SearchGUI(user);
                dispose();

            }
        });

        btn_logout.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            dispose();
        });
    }

    private void loadRezListModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_rezList.getModel();
        clearModel.setRowCount(0);

        String query = "SELECT * FROM public.rez";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                int i = 0;
                row_rez_list[i++] = rs.getString("guest_name");
                row_rez_list[i++] = rs.getInt("room_id");
                row_rez_list[i++] = rs.getString("contact_name");
                row_rez_list[i++] = rs.getString("contact_phone");
                row_rez_list[i++] = rs.getString("rez_note");
                mdl_rez_list.addRow(row_rez_list);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
