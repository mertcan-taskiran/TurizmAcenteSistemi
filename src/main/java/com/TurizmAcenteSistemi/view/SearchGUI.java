package com.TurizmAcenteSistemi.view;

import com.TurizmAcenteSistemi.helper.DBConnector;
import com.TurizmAcenteSistemi.model.Hotel;
import com.TurizmAcenteSistemi.model.User;
import com.TurizmAcenteSistemi.helper.Config;
import com.TurizmAcenteSistemi.helper.Helper;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_location;
    private JTextField fld_entry;
    private JTextField fld_exit;
    private JComboBox cmb_yetiskin;
    private JComboBox cmb_cocuk;
    private JButton btn_search;
    private JButton btn_logout;
    private JSpinner spn_yetiskin;
    private JSpinner spn_cocuk;
    private String checkInText = "";
    private String checkOutText = "";
    private boolean statusIn = true;
    private boolean statusOut = true;
    private User user;
    private ArrayList<Integer> hotelIdList;
    private ArrayList<Integer> roomIdList;
    public SearchGUI(User user){
        this.user = user;
        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.scCenter("x",getSize()),Helper.scCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        int min = 0;
        int max = 100;
        int step = 1;
        int initValue = 0;

        SpinnerModel spinnerModelAdult = new SpinnerNumberModel(initValue, min, max, step);
        SpinnerModel spinnerModelChild = new SpinnerNumberModel(initValue, min, max, step);

        spn_yetiskin.setModel(spinnerModelAdult);
        spn_cocuk.setModel(spinnerModelChild);

        guestNumController();

        fld_entry.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkInText = String.valueOf(checkInText);
                if (statusIn){
                    try {
                        checkInText = checkInText.substring(0,checkInText.length() -1);
                    }catch (StringIndexOutOfBoundsException exception){
                        exception.getStackTrace();
                    }

                    statusIn = false;
                }else{
                    int length = fld_entry.getText().length();
                    if (length == 7 || length == 4){
                        checkInText = fld_entry.getText();
                        checkInText += "-";
                        fld_entry.setText(checkInText);

                    }
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    statusIn = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        fld_exit.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (statusOut){
                    try {
                        checkOutText = checkOutText.substring(0,checkOutText.length() -1);
                    }catch (StringIndexOutOfBoundsException exception){
                        exception.getStackTrace();
                    }
                    statusOut = false;
                }else{
                    int length = fld_exit.getText().length();
                    if (length == 7 || length == 4){
                        checkOutText = fld_exit.getText();
                        checkOutText += "-";
                        fld_exit.setText(checkOutText);
                    }
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    statusOut = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        btn_search.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_location) || Helper.isFieldEmpty(fld_entry) || Helper.isFieldEmpty(fld_exit)){
                Helper.showMsg("fill");
            }else {
                String location = fld_location.getText();
                Date checkIn = Date.valueOf(fld_entry.getText());
                Date checkOut = Date.valueOf(fld_exit.getText());
                int numAdult = Integer.parseInt(spn_yetiskin.getValue().toString());
                int numChild = Integer.parseInt(spn_cocuk.getValue().toString());
                search(location,checkIn,checkOut,numAdult,numChild);
            }
        });

        btn_logout.addActionListener(e -> {
            RezGUI rezListGUI = new RezGUI(user);
            dispose();
        });

    }

    private void search(String location,Date checkIn,Date checkOut, int numAdult,int numChild){
        ArrayList<Integer> hotels = searchHotel(searchQuery(location,checkIn,checkOut,numAdult,numChild));
        for (int hotelId:hotels) {
            if (!isRoomAvailable(hotelId)){
                Helper.showMsg("İstenilen tipte uygun oda bulunamadı.");
            }else{
                ArrayList<Date> dates = getCheckInOutDate(hotelId);
                if (!isDateAvailable(dates,checkIn,checkOut)){
                    Helper.showMsg("Girilen tarihlerde uygun otel bulunamadı.");
                }else{
                    ArrayList<Integer> rooms = getRoomIdList();
                    Hotel hotel = Hotel.getFetch(hotelId);
                    dispose();
                    SearchResultGUI searchResultGUI = new SearchResultGUI(user,hotel,rooms,checkIn,checkOut,numAdult,numChild);
                }
            }
        }
    }

    private ArrayList<Integer> searchHotel(String query){
        ArrayList<Integer> searchHotelList = new ArrayList<>();
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                searchHotelList.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searchHotelList;
    }

    private boolean isRoomAvailable(int hotel_id){
        int stock;
        boolean status = false;
        String query = "SELECT id,stock FROM room WHERE hotel_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                stock = rs.getInt("stock");
                if (stock>0){
                    status = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    private ArrayList<Date> getCheckInOutDate(int hotel_id){
        String query = "SELECT check_entry,check_exit FROM rez WHERE hotel_id = ?";
        ArrayList<Date> dates = new ArrayList<>();
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,hotel_id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                Date check_entry = rs.getDate("check_entry");
                Date check_exit = rs.getDate("check_exit");
                dates.add(check_entry);
                dates.add(check_exit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dates;
    }

    private boolean isDateAvailable(ArrayList<Date> dates,Date checkEntry,Date checkExit){
        try {
            Date check_entry = dates.get(0);
            Date check_exit = dates.get(1);
            return check_exit.before(checkEntry);
        }catch (IndexOutOfBoundsException e){
            return true;
        }
    }

    private String searchQuery(String location,Date checkIn,Date checkOut,int numAdult,int numChild){
        String query = "SELECT id,name FROM hotel WHERE name ILIKE '%{{name}}%' OR city ILIKE '%{{city}}%'";
        query = query.replace("{{name}}",location);
        query = query.replace("{{city}}",location);
        return query;
    }

    private void guestNumController(){
        int child;
        int adult;
        try {
            adult = (int) spn_yetiskin.getValue();
            child = (int) spn_cocuk.getValue();
            if (adult < 0){
                spn_yetiskin.setValue(adult * -1);
            }
            if (child < 0){
                spn_cocuk.setValue(child * -1);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private ArrayList<Integer> getRoomIdList(){
        return roomIdList;
    }
}
