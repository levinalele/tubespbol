package com.lc.controller;

import com.lc.Main;
import com.lc.dao.jenisDaoImpl;
import com.lc.dao.kendaraanDaoImpl;
import com.lc.dao.roleDaoImpl;
import com.lc.dao.voucherDaoImpl;
import com.lc.entity.JenisKendaraan;
import com.lc.entity.Kendaraan;
import com.lc.entity.Role;
import com.lc.entity.Voucher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ManagerParkirFormController {
    public Button btnKendaraanMahasiswa;
    public Button btnKendaraanDosen;
    public Button btnAddStatusIjin;
    @FXML
    private TextField txtNRP;
    @FXML
    private TextField txtPlatMhs;
    @FXML
    private ComboBox cmbxJenisKendaraanMhs;
    @FXML
    private Button btnResetMhs;
    @FXML
    private Button btnSaveMhs;
    @FXML
    private TextField txtNIK;
    @FXML
    private TextField txtPlatDsn;
    @FXML
    private ComboBox cmbxJenisKendaraanDsn;
    @FXML
    private Button btnResetDsn;
    @FXML
    private Button btnSaveDsn;
    public ObservableList<JenisKendaraan> jenisKendaraans;
    public jenisDaoImpl jenisKendaraanDao;
    public roleDaoImpl roleDao;
    public ObservableList<Role> roles;
    public ObservableList<Voucher> vouchers;
    public voucherDaoImpl voucherDao;
    public KelolaJenisKendaraanController kelolaJenisKendaraanController;
    public KelolaVoucherController kelolaVoucherController;
    public ObservableList<Kendaraan> kendaraanMhs;
    public ObservableList<Kendaraan> kendaraanDosen;
    public kendaraanDaoImpl kendaraanDao;
    public KelolaKendaraanDosenController kelolaKendaraanDosenController;
    public KelolaKendaraanMahasiswaController kelolaKendaraanMahasiswaController;
    public KelolaStatusIjinController kelolaStatusIjinController;

    public void setKelolaStatusIjinController(KelolaStatusIjinController kelolaStatusIjinController) {
        this.kelolaStatusIjinController = kelolaStatusIjinController;
    }

    public void setKelolaKendaraanDosenController(KelolaKendaraanDosenController kelolaKendaraanDosenController) {
        this.kelolaKendaraanDosenController = kelolaKendaraanDosenController;
    }

    public void setKelolaKendaraanMahasiswaController(KelolaKendaraanMahasiswaController kelolaKendaraanMahasiswaController) {
        this.kelolaKendaraanMahasiswaController = kelolaKendaraanMahasiswaController;
    }

    public ObservableList<Kendaraan> getKendaraanDosen() {
        if(kendaraanDosen==null){
            kendaraanDosen = FXCollections.observableArrayList();
            kendaraanDosen.addAll(getKendaraanDao().showAllDosen());
        }
        return kendaraanDosen;
    }

    public ObservableList<Kendaraan> getKendaraanMhs() {
        if(kendaraanMhs==null){
            kendaraanMhs = FXCollections.observableArrayList();
            kendaraanMhs.addAll(getKendaraanDao().showAll());
        }
        return kendaraanMhs;
    }

    public kendaraanDaoImpl getKendaraanDao() {
        if(kendaraanDao==null){
            kendaraanDao = new kendaraanDaoImpl();
        }
        return kendaraanDao;
    }

    public ObservableList<Voucher> getVouchers() {
        if(vouchers==null){
            vouchers = FXCollections.observableArrayList();
            vouchers.addAll(getVoucherDao().showAll());
        }
        return vouchers;
    }

    public voucherDaoImpl getVoucherDao() {
        if(voucherDao==null){
            voucherDao = new voucherDaoImpl();
        }
        return voucherDao;
    }

    public void setKelolaVoucherController(KelolaVoucherController kelolaVoucherController) {
        this.kelolaVoucherController = kelolaVoucherController;
    }

    public void setKelolaJenisKendaraanController(KelolaJenisKendaraanController kelolaJenisKendaraanController) {
        this.kelolaJenisKendaraanController = kelolaJenisKendaraanController;
    }

    public ObservableList<JenisKendaraan> getJenisKendaraans() {
        if(jenisKendaraans==null){
            jenisKendaraans = FXCollections.observableArrayList();
            jenisKendaraans.addAll(getJenisKendaraanDao().showAll());
        }
        return jenisKendaraans;
    }

    public ObservableList<Role> getRoles() {
        if(roles==null){
            roles = FXCollections.observableArrayList();
            roles.addAll(getRoleDao().showRoleKecualiMahasiswa());
        }
        return roles;
    }

    public roleDaoImpl getRoleDao() {
        if(roleDao==null){
            roleDao = new roleDaoImpl();
        }
        return roleDao;
    }

    public jenisDaoImpl getJenisKendaraanDao() {
        if(jenisKendaraanDao==null){
            jenisKendaraanDao = new jenisDaoImpl();
        }
        return jenisKendaraanDao;
    }

    public void mnVoucherAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaVoucher.fxml"));
        AnchorPane anchorPane = loader.load();
        KelolaVoucherController controller = loader.getController();
        controller.setManagerParkirFormController(this);
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void mnKelolaJenisKendaraanAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaJenisKendaraan.fxml"));
        AnchorPane anchorPane = loader.load();
        KelolaJenisKendaraanController controller = loader.getController();
        controller.setManagerParkirFormController(this);
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void mnLogoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void actionbtnKendaraanMahasiswa(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaKendaraanMahasiswa.fxml"));
        VBox vBox = loader.load();
        KelolaKendaraanMahasiswaController controller = loader.getController();
        controller.setManagerParkirFormController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    public void actionbtnKendaraanDosen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaKendaraanDosen.fxml"));
        VBox vBox = loader.load();
        KelolaKendaraanDosenController controller = loader.getController();
        controller.setManagerParkirFormController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    public void actionbtnAddStatusIjin(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaStatusIjin.fxml"));
        AnchorPane anchorPane = loader.load();
        KelolaStatusIjinController controller = loader.getController();
        controller.setManagerParkirFormController(this);
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }
}
