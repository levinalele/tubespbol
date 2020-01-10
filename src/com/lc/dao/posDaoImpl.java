package com.lc.dao;

import com.lc.entity.Pos;
import com.lc.entity.Role;
import com.lc.util.DaoService;
import com.lc.util.dbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class posDaoImpl implements DaoService<Pos> {
    @Override
    public List<Pos> showAll() {
        List<Pos> poss= new ArrayList<>();
        String query = "SELECT * FROM pos";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Pos pos = new Pos();
                pos.setId(resultSet.getInt("id"));
                pos.setNama(resultSet.getString("nama"));
                poss.add(pos);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return poss;
    }

    @Override
    public int addData(Pos object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "INSERT INTO pos(nama) VALUES(?)";
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
    public int updateData(Pos object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "UPDATE pos SET nama=? WHERE id=?";
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
    public int deleteData(Pos object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "DELETE FROM pos WHERE id=?";
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
}
