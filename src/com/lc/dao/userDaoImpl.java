package com.lc.dao;

import com.lc.entity.JenisKendaraan;
import com.lc.entity.Kendaraan;
import com.lc.entity.Role;
import com.lc.entity.User;
import com.lc.util.DaoService;
import com.lc.util.MD5;
import com.lc.util.dbHelper;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class userDaoImpl implements DaoService<User> {
    public User login(User object){
        User user = null;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "SELECT * FROM user WHERE nik=? AND password=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,object.getNik());
            String pwd = MD5.PasswordMD5Func(object.getPassword());
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setNik(rs.getString("nik"));
                user.setPassword(rs.getString("password"));
                user.setNama(rs.getString("nama"));
                Role role = new Role();
                role.setId(rs.getInt("role_id"));
                user.setRole_id(role);
//                System.out.println(user.getNama()+"---"+user.getRole_id().getId());
            }

        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> showAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT u.*,r.id ,r.nama as 'namaRole' from user u JOIN role r ON u.role_id = r.id WHERE role_id = 1 OR role_id=2 OR role_id=3";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setNik(rs.getString("nik"));
                user.setNama(rs.getString("nama"));
                user.setPassword(rs.getString("password"));
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setNama(rs.getString("namaRole"));
                user.setRole_id(role);
                users.add(user);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public int addData(User object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "INSERT INTO user(nik,nama,password,role_id) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getNik());
            preparedStatement.setString(2,object.getNama());
            preparedStatement.setString(3,MD5.PasswordMD5Func(object.getPassword()));
            preparedStatement.setInt(4,object.getRole_id().getId());
            if(preparedStatement.executeUpdate()!=0){
                connection.commit();
                result=1;
            }else{
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getUserByNrp(String nrp) {
        Integer id = 0;
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user WHERE nik=?";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,nrp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int getIdUserByNrp(String nrp) {
        Integer id = 0;
        List<User> users = new ArrayList<>();
        String query = "SELECT id FROM user WHERE nik=?";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,nrp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int getMaxId() {
        Integer id = 0;
        String query = "SELECT MAX(id) as 'id' FROM user";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public int addDataUserKendaraan(User object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "INSERT INTO user(nik,role_id) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getNik());
            preparedStatement.setInt(2,object.getRole_id().getId());
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
    public int updateData(User object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement preparedStatement;
            if(object.getPassword()==null || object.getPassword().isEmpty() || object.getPassword()==""){
                String query = "UPDATE user SET nama=?,role_id=? WHERE nik=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,object.getNama());
                preparedStatement.setInt(2,object.getRole_id().getId());
                preparedStatement.setString(3,object.getNik());
            }else{
                String query = "UPDATE user SET nama=?, password=?, role_id=? WHERE nik=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,object.getNama());
                preparedStatement.setString(2,MD5.PasswordMD5Func(object.getPassword()));
                preparedStatement.setInt(3,object.getRole_id().getId());
                preparedStatement.setString(4,object.getNik());
            }
            if(preparedStatement.executeUpdate()!=0){
                connection.commit();
                result=1;
            }else{
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteData(User object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "DELETE FROM user WHERE nik=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getNik());
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
