package com.lc.dao;

import com.lc.entity.Pegawai;
import com.lc.util.DaoService;
import com.lc.util.dbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PegawaiDaoImpl implements DaoService<Pegawai> {

    @Override
    public List showAll() {
        return null;
    }

    @Override
    public int addData(Pegawai object) {
        return 0;
    }

    @Override
    public int updateData(Pegawai object) {
        return 0;
    }

    public int login(Pegawai object){
        int result = 0;
        try {
            Connection connection = dbHelper.createMySQLConnection();
            String query = "SELECT * FROM Pegawai WHERE nik=? AND password=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,object.getNik());
            ps.setString(2,object.getPassword());
            if(ps.executeUpdate()!=0){
                connection.commit();
                if(object.getRole_Pegawai_id().getId()==1){
                    result = 1;
                }
                else if(object.getRole_Pegawai_id().getId()==2){
                    result = 2;
                }
                else if(object.getRole_Pegawai_id().getId()==3){
                    result = 3;
                }
            }else{
                connection.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
