package com.lc.dao;

import com.lc.entity.JenisKendaraan;
import com.lc.entity.Kendaraan;
import com.lc.entity.Role;
import com.lc.entity.User;
import com.lc.util.DaoService;
import com.lc.util.dbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class jenisDaoImpl implements DaoService<JenisKendaraan> {
    @Override
    public List<JenisKendaraan> showAll() {
        List<JenisKendaraan> jenisKendaraans = new ArrayList<>();
        String query = "SELECT * FROM jenis_kendaraan";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                JenisKendaraan jenisKendaraan = new JenisKendaraan();
                jenisKendaraan.setId(resultSet.getInt("id"));
                jenisKendaraan.setJenis(resultSet.getString("jenis"));
                jenisKendaraan.setTarif(resultSet.getString("tarif"));
                jenisKendaraans.add(jenisKendaraan);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return jenisKendaraans;
    }

    @Override
    public int addData(JenisKendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "INSERT INTO jenis_kendaraan(jenis,tarif) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, object.getJenis());
            preparedStatement.setString(2, object.getTarif());
            if (preparedStatement.executeUpdate() != 0) {
                connection.commit();
                result = 1;
            } else {
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateData(JenisKendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "UPDATE jenis_kendaraan SET jenis=?,tarif=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, object.getJenis());
            preparedStatement.setString(2, object.getTarif());
            preparedStatement.setInt(3, object.getId());
            if (preparedStatement.executeUpdate() != 0) {
                connection.commit();
                result = 1;
            } else {
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteData(JenisKendaraan object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "DELETE FROM jenis_kendaraan WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getId());
            if (preparedStatement.executeUpdate() != 0) {
                connection.commit();
                result = 1;
            } else {
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public int getHargaById(Integer id) {
        Integer harga = 0;
        List<User> users = new ArrayList<>();
        String query = "SELECT tarif FROM jenis_kendaraan WHERE id=?";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                harga = rs.getInt("tarif");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return harga;
    }
}
