package com.lc.dao;

import com.lc.entity.JenisKendaraan;
import com.lc.entity.Kendaraan;
import com.lc.entity.User;
import com.lc.util.DaoService;
import com.lc.util.dbHelper;
import com.mysql.jdbc.JDBC4PreparedStatementHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class kendaraanDaoImpl implements DaoService<Kendaraan> {
    @Override
    public List<Kendaraan> showAll() {
        List<Kendaraan> kendaraans = new ArrayList<>();
        String query = "SELECT k.*,u.id as 'iduser', u.nik, u.nama, j.id as 'idJenis', j.jenis as 'namaJenis',j.tarif as 'tarifJenis' from kendaraan k JOIN user u ON k.user_id = u.id JOIN jenis_kendaraan j ON k.jenis_kendaraan_id = j.id WHERE u.role_id=4";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Kendaraan kendaraan = new Kendaraan();
                kendaraan.setStatus(rs.getString("status"));
                kendaraan.setPlatnomor(rs.getString("plat_nomor"));
                kendaraan.setStatus_ijin(rs.getString("status_ijin"));
                User user = new User();
                user.setId(rs.getString("iduser"));
                user.setNik(rs.getString("nik"));
                user.setNama(rs.getString("nama"));
                JenisKendaraan jenisKendaraan = new JenisKendaraan();
                jenisKendaraan.setId(rs.getInt("idJenis"));
                jenisKendaraan.setJenis(rs.getString("namaJenis"));
                jenisKendaraan.setTarif(rs.getString("tarifJenis"));
                kendaraan.setUser_id(user);
                kendaraan.setJenisKendaraan_id(jenisKendaraan);
                kendaraans.add(kendaraan);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return kendaraans;
    }

    public List<Kendaraan> showAllDosen() {
        List<Kendaraan> kendaraans = new ArrayList<>();
        String query = "SELECT k.*,u.id as 'iduser', u.nik, u.nama, j.id as 'idJenis', j.jenis as 'namaJenis',j.tarif as 'tarifJenis' from kendaraan k JOIN user u ON k.user_id = u.id JOIN jenis_kendaraan j ON k.jenis_kendaraan_id = j.id WHERE u.role_id=5 OR u.role_id=6";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Kendaraan kendaraan = new Kendaraan();
                kendaraan.setStatus(rs.getString("status"));
                kendaraan.setPlatnomor(rs.getString("plat_nomor"));
                kendaraan.setStatus_ijin(rs.getString("status_ijin"));
                User user = new User();
                user.setId(rs.getString("iduser"));
                user.setNik(rs.getString("nik"));
                user.setNama(rs.getString("nama"));
                JenisKendaraan jenisKendaraan = new JenisKendaraan();
                jenisKendaraan.setId(rs.getInt("idJenis"));
                jenisKendaraan.setJenis(rs.getString("namaJenis"));
                jenisKendaraan.setTarif(rs.getString("tarifJenis"));
                kendaraan.setUser_id(user);
                kendaraan.setJenisKendaraan_id(jenisKendaraan);
                kendaraans.add(kendaraan);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return kendaraans;
    }

    @Override
    public int addData(Kendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "INSERT INTO kendaraan(status,plat_nomor,status_ijin,jenis_kendaraan_id,user_id) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getStatus());
            preparedStatement.setString(2,object.getPlatnomor());
            preparedStatement.setString(3,object.getStatus_ijin());
            preparedStatement.setInt(4,object.getJenisKendaraan_id().getId());
            preparedStatement.setString(5,object.getUser_id().getId());
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


    public int cariId(Kendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "SELECT id FROM kendaraan WHERE plat_nomor=? AND status=? AND user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getPlatnomor());
            preparedStatement.setString(2,object.getStatus());
            preparedStatement.setString(3,object.getUser_id().getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                result = rs.getInt("id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public int getIdByPlatNomor(Kendaraan object) {
        Integer id = 0;
        List<User> users = new ArrayList<>();
        String query = "SELECT id FROM kendaraan WHERE status=? AND plat_nomor=?";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,object.getStatus());
            ps.setString(2,object.getPlatnomor());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public int getJenisById(Integer idk) {
        Integer id = 0;
        List<User> users = new ArrayList<>();
        String query = "SELECT jenis_kendaraan_id FROM kendaraan WHERE id=?";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,idk);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("jenis_kendaraan_id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int cekPlatNomor(Kendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "SELECT jenis_kendaraan_id FROM kendaraan WHERE plat_nomor=? AND status=? AND user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getPlatnomor());
            preparedStatement.setString(2,object.getStatus());
            preparedStatement.setString(3,object.getUser_id().getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                result = rs.getInt("jenis_kendaraan_id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getStatusIjin(Kendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "SELECT status_ijin FROM kendaraan WHERE plat_nomor=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getPlatnomor());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                result = rs.getInt("status_ijin");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateData(Kendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "UPDATE kendaraan SET status=?, plat_nomor=?, status_ijin=?, jenis_kendaraan_id=? WHERE  AND user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getStatus());
            preparedStatement.setString(2,object.getPlatnomor());
            preparedStatement.setString(3,object.getStatus_ijin());
            preparedStatement.setInt(4,object.getJenisKendaraan_id().getId());
            preparedStatement.setString(5,object.getUser_id().getNik());
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

    public int updateStatusIjin(Kendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "UPDATE kendaraan SET status_ijin=? WHERE plat_nomor=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getStatus_ijin());
            preparedStatement.setString(2,object.getPlatnomor());
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

    public int updateStatus(Kendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "UPDATE kendaraan SET status=? WHERE user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getStatus());
            preparedStatement.setString(2,object.getUser_id().getId());
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

    public int updateStatusDosen(Kendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "UPDATE kendaraan SET status=? WHERE user_id=? AND jenis_kendaraan_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getStatus());
            preparedStatement.setString(2,object.getUser_id().getId());
            preparedStatement.setInt(3,object.getJenisKendaraan_id().getId());
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
    public int deleteData(Kendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "DELETE FROM kendaraan WHERE jenis_kendaraan_id=? AND user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,object.getJenisKendaraan_id().getId());
            preparedStatement.setString(2,object.getUser_id().getNik());
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
}
