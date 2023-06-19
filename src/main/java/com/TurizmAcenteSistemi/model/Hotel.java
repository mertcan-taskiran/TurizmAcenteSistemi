package com.TurizmAcenteSistemi.model;

import com.TurizmAcenteSistemi.helper.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private String city;
    private String email;
    private String phone;
    private String star;
    private String tesis_ozellikleri;
    private Array pansiyon_tipi;
    public Hotel() {
    }
    public Hotel(int id, String name, String address, String city, String email, String phone, String star, String tesis_ozellikleri, Array pansiyon_tipi) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.star = star;
        this.tesis_ozellikleri = tesis_ozellikleri;
        this.pansiyon_tipi = pansiyon_tipi;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getStar() {
        return star;
    }
    public String getTesis_ozellikleri() {
        return tesis_ozellikleri;
    }
    public Array getPansiyon_tipi() {
        return pansiyon_tipi;
    }

    public static Hotel getHotelByName(String hotelName){
        Hotel obj = null;
        String query = "SELECT * FROM hotel WHERE name = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,hotelName);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj =  new Hotel(rs.getInt("id"),rs.getString("name"),rs.getString("address"), rs.getString("email"),rs.getString("phone"),rs.getString("tesis_ozellikleri"), rs.getString("city"), rs.getString("star"), rs.getArray("pansiyon_tipi"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static Hotel getFetch(int id){
        Hotel obj = null;
        String query = "SELECT * FROM hotel WHERE id = "+id;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()){
                obj =  new Hotel(rs.getInt("id"),rs.getString("name"),rs.getString("address"), rs.getString("email"),rs.getString("phone"),rs.getString("tesis_ozellikleri"), rs.getString("city"), rs.getString("star"), rs.getArray("pansiyon_tipi"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static boolean add(String name, String address, String email, String phone, String tesis_ozellikleri, ArrayList<String> pansiyon_tipi, String city, String star) {
        String sql = "INSERT INTO hotel ( name,  address,  email,  phone,  tesis_ozellikleri, city ,  star,  pansiyon_tipi) VALUES (?,?,?,?,?,?,?,?)";
        try {
            Array array = DBConnector.getInstance().createArrayOf("VARCHAR", pansiyon_tipi.toArray());
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setString(1, name);
            pr.setString(2, address);
            pr.setString(3, email);
            pr.setString(4, phone);
            pr.setString(5, tesis_ozellikleri);
            pr.setString(6, city);
            pr.setString(7, star);
            pr.setArray(8, array);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static ArrayList<Hotel> getList() {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        Hotel obj;
        String sql = "SELECT * FROM hotel";
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                obj = new Hotel(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("tesis_ozellikleri"), rs.getString("city"), rs.getString("star"), rs.getArray("pansiyon_tipi"));
                hotelList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelList;
    }
}
