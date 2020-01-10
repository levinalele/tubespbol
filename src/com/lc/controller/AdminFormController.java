package com.lc.controller;

import com.lc.Main;
import com.lc.dao.roleDaoImpl;
import com.lc.entity.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AdminFormController {
    public MenuItem mnLogout;
    @FXML
    private DatePicker datepickerMingguan;
    @FXML
    private Button btnMingguan;
    @FXML
    private TextField txtBulanBulanan;
    @FXML
    private TextField txtTahunBulanan;
    @FXML
    private Button btnBulanan;
    @FXML
    private TextField txtTahunTahunan;
    @FXML
    private Button btnTahunan;
    public roleDaoImpl roleDao;
    public ObservableList<Role> roles;
    public KelolaUserController kelolaUserController;
    public KelolaRoleController kelolaRoleController;
    Alert error = new Alert(Alert.AlertType.ERROR);

    public void setKelolaRoleController(KelolaRoleController kelolaRoleController) {
        this.kelolaRoleController = kelolaRoleController;
    }

    public void setKelolaUserController(KelolaUserController kelolaUserController) {
        this.kelolaUserController = kelolaUserController;
    }

    public roleDaoImpl getRoleDao() {
        if(roleDao == null){
            roleDao = new roleDaoImpl();
        }
        return roleDao;
    }

    public ObservableList<Role> getRoles() {
        if(roles==null){
            roles = FXCollections.observableArrayList();
            roles.addAll(getRoleDao().showRolePetugas());
        }
        return roles;
    }

    @FXML
    private void MingguanAction(ActionEvent actionEvent) {
    }

    @FXML
    private void BulananAction(ActionEvent actionEvent) {
    }

    @FXML
    private void TahunanAction(ActionEvent actionEvent) {
    }


    public void mnLogoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void mnKelolaUserAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaUser.fxml"));
        AnchorPane anchorPane = loader.load();
        KelolaUserController controller = loader.getController();
        controller.setAdminFormController(this);
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void mnKelolaRoleAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaRole.fxml"));
        AnchorPane anchorPane = loader.load();
        KelolaRoleController controller = loader.getController();
        controller.setAdminFormController(this);
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }
}
