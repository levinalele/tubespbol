package com.lc.dao;

import com.lc.entity.*;
import com.lc.util.DaoService;
import com.lc.util.dbHelper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class parkirDaoImpl implements DaoService<Parkir> {
    @Override
    public List<Parkir> showAll() {
        List<Parkir> parkirs = new ArrayList<>();
        String query = "SELECT pa.*, v.id as 'idVoucher', v.jenis as 'jenisVoucher',po.id as 'idPos', po.nama as 'namaPos', k.jenis_kendaraan_id as 'jenisKendaraanId',k.user_nik as 'userNik' FROM parkir pa JOIN pos po ON pa.pos_id = po.id JOIN voucher v ON pa.voucher_id = v.id JOIN kendaraan k ON pa.kendaraan_id = k.id";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Parkir parkir = new Parkir();
                parkir.setId(rs.getInt("id"));
                parkir.setJam_keluar(rs.getTime("jam_keluar"));
                parkir.setJam_masuk(rs.getTime("jam_masuk"));
                parkir.setStatus(rs.getString("status"));
                LocalDate date = LocalDate.parse((CharSequence) rs.getDate("tanggal"));
                parkir.setTanggal(date);
                parkir.setTotal_bayar(rs.getString("total_bayar"));
                Voucher voucher = new Voucher();
                voucher.setId(rs.getInt("idVoucher"));
                voucher.setJenis(rs.getString("namaVoucher"));
                Pos pos = new Pos();
                pos.setId(rs.getInt("idPos"));
                pos.setNama(rs.getString("namaPos"));
                User user = new User();
                user.setNik(rs.getString("userNik"));
                JenisKendaraan jenisKendaraan = new JenisKendaraan();
                jenisKendaraan.setId(rs.getInt("jenisKendaraanId"));
                Kendaraan kendaraan = new Kendaraan();
                kendaraan.setUser_id(user);
                kendaraan.setJenisKendaraan_id(jenisKendaraan);
                parkir.setPos_id(pos);
                parkir.setVoucher_id(voucher);
                parkirs.add(parkir);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return parkirs;
    }


    public int getIdByKendaraan(String idk) {
        Integer id = 0;
        List<User> users = new ArrayList<>();
        String query = "SELECT id FROM parkir WHERE kendaraan_id=?";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,idk);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String getDateById(Integer id) {
        String date = null;
        String query = "SELECT tanggal FROM parkir WHERE id=?";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,String.valueOf(id));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                date = rs.getString("tanggal");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Time getTimeById(Integer id) {
        Time time = null;
        String query = "SELECT jam_masuk FROM parkir WHERE id=?";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,String.valueOf(id));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                time = rs.getTime("jam_masuk");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return time;
    }

    @Override
    public int addData(Parkir object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "INSERT INTO parkir(tanggal,jam_masuk,total_bayar,status,kendaraan_id,kendaraan_user_id,kendaraan_jenis_kendaraan_id) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(object.getTanggal()));
            preparedStatement.setTime(2, object.getJam_masuk());
            preparedStatement.setString(3,object.getTotal_bayar());
            preparedStatement.setString(4,object.getStatus());
            preparedStatement.setString(5,object.getKendaraan_id().getId());
            preparedStatement.setString(6,object.getKendaraan_id().getUser_id().getId());
            preparedStatement.setInt(7,object.getKendaraan_id().getJenisKendaraan_id().getId());
            if(preparedStatement.executeUpdate()!=0){
                connection.commit();
                result=1;
            }else{
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public int addDataUmum(Parkir object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "INSERT INTO parkir(tanggal,jam_masuk,total_bayar,status,plat_nomor_umum,jenis_kendaraan_umum) VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(object.getTanggal()));
            preparedStatement.setTime(2, object.getJam_masuk());
            preparedStatement.setString(3,object.getTotal_bayar());
            preparedStatement.setString(4,object.getStatus());
            preparedStatement.setString(5,object.getPlat_nomor_umum());
            preparedStatement.setString(6,object.getJenis_kendaraan_umum());
            if(preparedStatement.executeUpdate()!=0){
                connection.commit();
                result=1;
            }else{
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public int updateData(Parkir object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "UPDATE parkir SET jam_keluar=?,total_bayar=?,status=?,pos_id=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTime(1,object.getJam_keluar());
            preparedStatement.setString(2,object.getTotal_bayar());
            preparedStatement.setString(3,object.getStatus());
            preparedStatement.setInt(4,object.getPos_id().getId());
            preparedStatement.setInt(5,object.getId());
            if(preparedStatement.executeUpdate()!=0){
                connection.commit();
                result=1;
            }else{
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int updateDataWithVoucher(Parkir object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "UPDATE parkir SET jam_keluar=?,total_bayar=?,status=?,pos_id=?,voucher_id=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTime(1,object.getJam_keluar());
            preparedStatement.setString(2,object.getTotal_bayar());
            preparedStatement.setString(3,object.getStatus());
            preparedStatement.setInt(4,object.getPos_id().getId());
            preparedStatement.setInt(5,object.getVoucher_id().getId());
            preparedStatement.setInt(6,object.getId());
            if(preparedStatement.executeUpdate()!=0){
                connection.commit();
                result=1;
            }else{
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteData(Parkir object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "DELETE FROM parkir WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,object.getId());
            if(preparedStatement.executeUpdate()!=0){
                connection.commit();
                result=1;
            }else{
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getIdByPlatNomor(String plat) {
        Integer id = 0;
        String query = "SELECT id FROM parkir WHERE plat_nomor_umum=?";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,plat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
