package com.lc.controller;

import com.lc.Main;
import com.lc.dao.userDaoImpl;
import com.lc.entity.JenisKendaraan;
import com.lc.entity.Kendaraan;
import com.lc.entity.Role;
import com.lc.entity.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class KelolaKendaraanDosenController implements Initializable {
    public TextField txtNIK;
    public TextField txtPlatNomor;
    public ComboBox<JenisKendaraan> comboJenisKendaran;
    public Button btnAdd;
    public TableView<Kendaraan> tableDosen;
    public TableColumn<Kendaraan,String> colNIK;
    public TableColumn<Kendaraan,String> colPlatNomor;
    public TableColumn<Kendaraan,String> colJenisKendaraan;
    public TableColumn<Kendaraan,String> colStatus;
    public TableColumn<Kendaraan,String> colRole;
    public ManagerParkirFormController managerParkirFormController;
    public userDaoImpl userDao;
    public ComboBox<Role> comboRole;

    Alert error = new Alert(Alert.AlertType.ERROR);

    public userDaoImpl getUserDao() {
        if(userDao==null){
            userDao = new userDaoImpl();
        }
        return userDao;
    }

    public void setManagerParkirFormController(ManagerParkirFormController managerParkirFormController) {
        tableDosen.setItems(managerParkirFormController.getKendaraanDosen());
        comboJenisKendaran.setItems(managerParkirFormController.getJenisKendaraans());
        comboRole.setItems(managerParkirFormController.getRoles());
        this.managerParkirFormController = managerParkirFormController;
    }

    public void actionbtnAdd(ActionEvent actionEvent) {
        Kendaraan kendaraan = new Kendaraan();
        kendaraan.setPlatnomor(txtPlatNomor.getText());

        boolean notDuplicate = managerParkirFormController.getKendaraanMhs().stream().filter(d -> d.getPlatnomor().equals(kendaraan.getPlatnomor())).count() == 0;
        if(notDuplicate){
            //kalo ga ada duplikat plat nomor masuk
            kendaraan.setJenisKendaraan_id(comboJenisKendaran.getValue());
            kendaraan.setStatus("1");
            kendaraan.setStatus_ijin("0");
            User us = new User();
            us.setRole_id(comboRole.getValue());
            //cek ada ga usernya
            Integer cekuser = getUserDao().getUserByNrp(txtNIK.getText());
            if(cekuser!=0){
                //kalo ada masuk
                us.setId(String.valueOf(cekuser));
                us.setNik(txtNIK.getText());
                kendaraan.setUser_id(us);
            }else{
                // kalo ga ada add dulu user nya buat dapet id barunya
                us.setNik(txtNIK.getText());
                us.setRole_id(comboRole.getValue());
                //add data user barunya
                getUserDao().addDataUserKendaraan(us);
                //cari id barunya
                Integer iduser = getUserDao().getMaxId();
                //masukin id barunya ke object user yang baru
                User userr = new User();
                userr.setId(String.valueOf(iduser));
                //add ke object kendaraan tadi
                kendaraan.setUser_id(userr);
            }
            //cek ada ga dosen yg uda punya kendaraan berdasarkan nik nya
            boolean notDuplicateUser = true;
            for (Kendaraan r : managerParkirFormController.getKendaraanDosen()){
                if(r.getUser_id().getNik().equals(kendaraan.getUser_id().getNik())){
                    if(r.getJenisKendaraan_id().getJenis().equals(kendaraan.getJenisKendaraan_id().getJenis())){
                        notDuplicateUser = false;
                    }
                }
            }
            if(!notDuplicateUser){
                //kalo ada masuk trus di jadiin statusnya 0
                for(Kendaraan k : managerParkirFormController.getKendaraanDosen()){
                    if(k.getUser_id().getNik().equals(kendaraan.getUser_id().getNik())){
                        if(k.getJenisKendaraan_id().getJenis().equals(kendaraan.getJenisKendaraan_id().getJenis())) {
                            User ubaru = new User();
                            ubaru.setId(k.getUser_id().getId());
                            Kendaraan kbaru = new Kendaraan();
                            kbaru.setStatus("0");
                            kbaru.setUser_id(ubaru);
                            kbaru.setJenisKendaraan_id(comboJenisKendaran.getValue());
                            managerParkirFormController.kendaraanDao.updateStatusDosen(kbaru);
                        }
                    }
                }
            }
            managerParkirFormController.getKendaraanDao().addData(kendaraan);
            clearForm();
            refreshTable();
        }
        else if(txtNIK.getText().isEmpty() || txtPlatNomor.getText().isEmpty() || comboJenisKendaran.getValue()==null){
            error.setContentText("Please Fill NRP/Nama/Jenis Kendaraan");
            error.show();
        }else{
            error.setContentText("Plat Nomor is Duplicate");
            error.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colNIK.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getUser_id().getNik()));
        colPlatNomor.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getPlatnomor()));
        colJenisKendaraan.setCellValueFactory(data->new SimpleObjectProperty(String.valueOf(data.getValue().getJenisKendaraan_id().getJenis())));
        colStatus.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getStatus()));
//        colRole.setCellValueFactory(data->new SimpleObjectProperty(String.valueOf(data.getValue().getUser_id().getRole_id().getNama())));
    }

    private void clearForm(){
        txtNIK.clear();
        txtPlatNomor.clear();
        tableDosen.getSelectionModel().clearSelection();
    }

    private void refreshTable(){
        managerParkirFormController.getKendaraanDosen().clear();
        managerParkirFormController.getKendaraanDosen().addAll(managerParkirFormController.getKendaraanDao().showAllDosen());
    }

    public void mnHomeAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ManagerParkirForm.fxml"));
        VBox vBox = loader.load();
        ManagerParkirFormController controller = loader.getController();
        controller.setKelolaKendaraanDosenController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    public void mnLogoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }
}
