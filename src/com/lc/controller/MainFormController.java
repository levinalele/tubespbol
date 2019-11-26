package com.lc.controller;

import com.lc.Main;
import com.lc.dao.PegawaiDaoImpl;
import com.lc.entity.Pegawai;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    @FXML
    private TextField txtIdLogin;
    @FXML
    private TextField txtUsernameLogin;
    @FXML
    private Button btnMasuk;
    private Stage mainStage;


    private ObservableList<Pegawai> pegawais;
    private PegawaiDaoImpl pegawaiDao;

    public ObservableList<Pegawai> getPegawais() {
        if(pegawais == null){
            pegawais = FXCollections.observableArrayList();
            pegawais.addAll(getPegawaiDao().showAll());
        }
        return pegawais;
    }

    public PegawaiDaoImpl getPegawaiDao() {
        if(pegawaiDao == null){
            pegawaiDao = new PegawaiDaoImpl();
        }
        return pegawaiDao;
    }

    @FXML
    private void masukAction(ActionEvent actionEvent) throws IOException {
        Pegawai pegawai = new Pegawai();
        pegawai.setNik(txtIdLogin.getText());
        pegawai.setNama(txtUsernameLogin.getText());
        int result = getPegawaiDao().login(pegawai);
        if(result==1){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AdminForm.fxml"));
            mainStage = new Stage();
            mainStage.setTitle("Admin Form");
            mainStage.setScene(new Scene((Parent) loader.load()));
        }else if(result==2){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ManagerParkirForm.fxml"));
            mainStage = new Stage();
            mainStage.setTitle("Admin Form");
            mainStage.setScene(new Scene((Parent) loader.load()));

        }else if(result==3){

        }
    }
}
