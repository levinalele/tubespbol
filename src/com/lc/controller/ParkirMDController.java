package com.lc.controller;

import com.lc.Main;
import com.lc.dao.kendaraanDaoImpl;
import com.lc.dao.parkirDaoImpl;
import com.lc.dao.userDaoImpl;
import com.lc.entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class ParkirMDController {
    public TextField txtPlatNomor;
    public TextField txtNRPNIK;
    public Button btnParkir;
    public userDaoImpl userDao;
    public kendaraanDaoImpl kendaraanDao;
    public parkirDaoImpl parkirDao;
    public Button btnBack;
    Alert error = new Alert(Alert.AlertType.ERROR);

    public parkirDaoImpl getParkirDao() {
        if (parkirDao == null) {
            parkirDao = new parkirDaoImpl();
        }
        return parkirDao;
    }

    public userDaoImpl getUserDao() {
        if (userDao == null) {
            userDao = new userDaoImpl();
        }
        return userDao;
    }

    public kendaraanDaoImpl getKendaraanDao() {
        if (kendaraanDao == null) {
            kendaraanDao = new kendaraanDaoImpl();
        }
        return kendaraanDao;
    }

    public void actionbtnParkir(ActionEvent actionEvent) throws ParseException {
        Integer iduser = getUserDao().getIdUserByNrp(txtNRPNIK.getText());
        System.out.println("==iduser== : "+iduser);
        if (iduser != 0) {
            User user = new User();
            user.setId(String.valueOf(iduser));
            Kendaraan kendaraan = new Kendaraan();
            kendaraan.setUser_id(user);
            kendaraan.setPlatnomor(txtPlatNomor.getText());
            kendaraan.setStatus("1");
            Integer cekplat = getKendaraanDao().cekPlatNomor(kendaraan);
            System.out.println(cekplat);
            if (cekplat != 0) {
                JenisKendaraan jns = new JenisKendaraan();
                jns.setId(cekplat);
                kendaraan.setJenisKendaraan_id(jns);
                Integer idkendaraan = getKendaraanDao().cariId(kendaraan);
                kendaraan.setId(String.valueOf(idkendaraan));

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String date2 = dateFormat.format(date);
                LocalDate tgl = LocalDate.parse(date2);

                DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                Time time = Time.valueOf(dateFormat2.format(cal.getTime()));
//                Date contoh = dateFormat2.parse("23:00:00");

                Parkir parkir = new Parkir();
                parkir.setTanggal(tgl);
                parkir.setJam_masuk(time);
                parkir.setStatus("0");
                parkir.setKendaraan_id(kendaraan);
                parkir.setTotal_bayar("0");
                System.out.println(parkir.getTanggal()+"=jam: "+parkir.getJam_masuk()+"=plat : "+ parkir.getKendaraan_id().getPlatnomor()+"=idjeniskendaraan : "+ parkir.getKendaraan_id().getJenisKendaraan_id().getId()+"=iduser :"+ parkir.getKendaraan_id().getUser_id().getId() + "=idkendaraan : "+ parkir.getKendaraan_id().getId());
                getParkirDao().addData(parkir);
                txtNRPNIK.clear();
                txtPlatNomor.clear();
            } else {
                error.setContentText("plat nomor belum tercantum. mohon hubungi manager parkir !");
                error.show();
            }
        } else {
            error.setContentText("user tidak ditemukan mohon hubungi manager parkir !");
            error.show();
        }

        //pengurangan jam dan convert ke jam nya
//        long kurang = contoh.getTime() - time.getTime();
//        System.out.println(((kurang/1000)/60)/60);
    }

    public void actionbtnBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }
}
