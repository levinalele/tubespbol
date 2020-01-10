package com.lc.controller;

import com.lc.Main;
import com.lc.dao.roleDaoImpl;
import com.lc.dao.userDaoImpl;
import com.lc.entity.Role;
import com.lc.entity.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class KelolaUserController implements Initializable {
    public TextField txtNik;
    public TextField txtNama;
    public TextField txtPassword;
    public ComboBox<Role> comboRole;
    public Button btnSave;
    public Button btnReset;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView<User> tableUser;
    public TableColumn<User, String> colNik;
    public TableColumn<User,String> colNama;
    public TableColumn<User, String> colRole;
    public User selectedItems;
    public AdminFormController adminFormController;
    public KelolaRoleController kelolaRoleController;
    public ObservableList<User> users;
    public userDaoImpl userDao;
    public roleDaoImpl roleDao;
    public ObservableList<Role> roles;

    Alert error = new Alert(Alert.AlertType.ERROR);

    public void setKelolaRoleController(KelolaRoleController kelolaRoleController) {
        this.kelolaRoleController = kelolaRoleController;
        comboRole.setItems(kelolaRoleController.adminFormController.getRoles());
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

    public void setAdminFormController(AdminFormController adminFormController) {
        this.adminFormController = adminFormController;
        comboRole.setItems(adminFormController.getRoles());
    }

    public ObservableList<User> getUsers() {
        if(users==null){
            users = FXCollections.observableArrayList();
            users.addAll(getUserDao().showAll());
        }
        return users;
    }

    public userDaoImpl getUserDao() {
        if(userDao==null){
            userDao = new userDaoImpl();
        }
        return userDao;
    }

    public void btnSaveAction(ActionEvent actionEvent) {
        User user = new User();
        user.setNik(txtNik.getText());
        user.setNama(txtNama.getText());
        user.setPassword(txtPassword.getText());
        user.setRole_id(comboRole.getValue());
        boolean notDuplicate = getUsers().stream().filter(d -> d.getNik().equals(user.getNik())).count() == 0;
        if(notDuplicate){
            getUserDao().addData(user);
            clearForm();
            refreshTable();
        }else if(txtNik.getText().isEmpty() || txtNama.getText().isEmpty() || txtPassword.getText().isEmpty() || comboRole.getValue()==null){
            error.setContentText("Please Fill NIK/Nama/Password/role");
            error.show();
        }else{
            error.setContentText("NIK is Duplicate");
            error.show();
        }
    }

    public void btnResetAction(ActionEvent actionEvent) {
        clearForm();
        refreshTable();
    }

    public void btnUpdateAction(ActionEvent actionEvent) {
        boolean notDuplicate = getUsers().stream().filter(d -> d.getNik().equals(txtNik.getText())).count() > 0;
        if(notDuplicate){
            selectedItems.setNik(txtNik.getText());
            selectedItems.setNama(txtNama.getText());
            selectedItems.setPassword(txtPassword.getText());
            selectedItems.setRole_id(comboRole.getValue());
            getUserDao().updateData(selectedItems);
            refreshTable();
            clearForm();
        }else if(txtNik.getText().isEmpty() || txtNama.getText().isEmpty() || comboRole.getValue()==null){
            error.setContentText("Please Fill Nik/Nama/Role");
            error.show();
        }else{
            error.setContentText("Nik is Duplicate");
            error.show();
        }
    }

    public void btnDeleteAction(ActionEvent actionEvent) {
        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setContentText("Apakah anda yakin mau menghapus data ? ");
        deleteConfirmation.showAndWait();
        if(deleteConfirmation.getResult() == ButtonType.OK){
            getUserDao().deleteData(selectedItems);
            clearForm();
            refreshTable();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableUser.setItems(getUsers());
        colNama.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNama()));
        colNik.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getNik()));
        colRole.setCellValueFactory(data->new SimpleObjectProperty(String.valueOf(data.getValue().getRole_id().getNama())));
    }

    public void tableUserClicked(MouseEvent mouseEvent) {
        selectedItems = tableUser.getSelectionModel().getSelectedItem();
        if(selectedItems != null){
            txtNik.setText(selectedItems.getNik());
            txtNama.setText(selectedItems.getNama());
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void clearForm(){
        txtNik.clear();
        txtNama.clear();
        txtPassword.clear();
        selectedItems = null;
        tableUser.getSelectionModel().clearSelection();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
    }

    private void refreshTable(){
        getUsers().clear();
        getUsers().addAll(getUserDao().showAll());
    }

    public void mnKelolaRoleAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaRole.fxml"));
        AnchorPane anchorPane = loader.load();
        KelolaRoleController controller = loader.getController();
        controller.setKelolaUserController(this);
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void mnHomeAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/AdminForm.fxml"));
        VBox vBox = loader.load();
        AdminFormController controller = loader.getController();
        controller.setKelolaUserController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    public void mnLogoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }
}
