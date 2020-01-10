package com.lc.controller;

import com.lc.Main;
import com.lc.dao.userDaoImpl;
import com.lc.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainFormController {
    public Button btnMasuk;
    public Button btnParkir;
    @FXML
    private TextField txtNik;
    @FXML
    private TextField txtPassword;

    private userDaoImpl userDao;

    public userDaoImpl getUserDao() {
        if(userDao == null){
            userDao = new userDaoImpl();
        }
        return userDao;
    }

    @FXML
    private void masukAction(ActionEvent actionEvent) throws IOException {
        User user = new User();
        user.setNik(txtNik.getText());
        user.setPassword(txtPassword.getText());
        if(user.getNik().trim().isEmpty() || user.getPassword().trim().isEmpty())
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Harap isi ID dan Password terlebih dahulu");
            alert.showAndWait();
        }
        else
        {
            User result = getUserDao().login(user);
            if(result != null){
                FXMLLoader loader = new FXMLLoader();
                if(result.getRole_id().getId() == 1){
                    loader.setLocation(Main.class.getResource("view/AdminForm.fxml"));
                }else if (result.getRole_id().getId()==2){
                    loader.setLocation(Main.class.getResource("view/ManagerParkirForm.fxml"));
                }else if (result.getRole_id().getId()==3){
                    loader.setLocation(Main.class.getResource("view/PenjagaParkirUmumForm.fxml"));
                }
                VBox vBox = loader.load();
                Main.getPrimaryStage().setScene(new Scene(vBox));
            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User tidak ditemukan");
                alert.showAndWait();
            }
        }
    }

    public void actionbtnParkir(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ParkirMD.fxml"));
        VBox vBox = loader.load();
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }
}
