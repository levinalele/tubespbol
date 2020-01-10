package com.lc.controller;

import com.lc.Main;
import com.lc.dao.kendaraanDaoImpl;
import com.lc.entity.Kendaraan;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class KelolaStatusIjinController {
    public TextField txtPlatNomor;
    public Button btnSave;
    public CheckBox checkIjin;
    public kendaraanDaoImpl kendaraanDao;
    public ManagerParkirFormController managerParkirFormController;

    public void setManagerParkirFormController(ManagerParkirFormController managerParkirFormController) {
        this.managerParkirFormController = managerParkirFormController;
    }

    public kendaraanDaoImpl getKendaraanDao() {
        if(kendaraanDao==null){
            kendaraanDao = new kendaraanDaoImpl();
        }
        return kendaraanDao;
    }

    public void mnHomeAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ManagerParkirForm.fxml"));
        VBox vBox = loader.load();
        ManagerParkirFormController controller = loader.getController();
        controller.setKelolaStatusIjinController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    public void mnLogoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void btnSaveAction(ActionEvent actionEvent) {
        Kendaraan kendaraan = new Kendaraan();
        kendaraan.setPlatnomor(txtPlatNomor.getText());
        kendaraan.setStatus_ijin("1");
        getKendaraanDao().updateStatusIjin(kendaraan);
        txtPlatNomor.clear();
    }

}
