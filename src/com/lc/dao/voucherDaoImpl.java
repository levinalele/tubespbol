package com.lc.dao;

import com.lc.entity.Role;
import com.lc.entity.Voucher;
import com.lc.util.DaoService;
import com.lc.util.dbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class voucherDaoImpl implements DaoService<Voucher> {
    @Override
    public List<Voucher> showAll() {
        List<Voucher> vouchers = new ArrayList<>();
        String query = "SELECT * FROM voucher";
        try {
            Connection connection = dbHelper.createMySQLConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Voucher voucher = new Voucher();
                voucher.setId(resultSet.getInt("id"));
                voucher.setJenis(resultSet.getString("jenis"));
                vouchers.add(voucher);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return vouchers;
    }

    @Override
    public int addData(Voucher object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "INSERT INTO voucher(jenis) VALUES(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getJenis());
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
    public int updateData(Voucher object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "UPDATE voucher SET jenis=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,object.getJenis());
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
    public int deleteData(Voucher object) {
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "DELETE FROM voucher WHERE id=?";
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
