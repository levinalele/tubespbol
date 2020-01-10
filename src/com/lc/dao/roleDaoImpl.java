package com.lc.dao;

import com.lc.entity.Role;
import com.lc.util.DaoService;
import com.lc.util.dbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class roleDaoImpl implements DaoService<Role> {

    public List<Role> showRoleKecualiMahasiswa() {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM role WHERE id!=4";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setNama(resultSet.getString("nama"));
                roles.add(role);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public List<Role> showRolePetugas() {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM role WHERE id=1 OR id=2 OR id=3";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setNama(resultSet.getString("nama"));
                roles.add(role);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public List<Role> showAll() {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM role";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setNama(resultSet.getString("nama"));
                roles.add(role);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public int addData(Role object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "INSERT INTO role(nama) VALUES(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getNama());
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
    public int updateData(Role object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "UPDATE role SET nama=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getNama());
            preparedStatement.setInt(2,object.getId());
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
    public int deleteData(Role object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "DELETE FROM role WHERE nama=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getNama());
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
