package com.lc.controller;

import com.lc.Main;
import com.lc.dao.roleDaoImpl;
import com.lc.entity.Role;
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

public class KelolaRoleController implements Initializable {
    public Button btnSave;
    public Button btnReset;
    public Button btnUpdate;
    public Button btnDelete;
    public TextField txtNama;
    public TableView<Role> tableRole;
    public TableColumn<Role,String> colID;
    public TableColumn<Role,String> colNamaRole;
    public Role selectedItems;
    public Role r;
    public AdminFormController adminFormController;
    public KelolaUserController kelolaUserController;
    Alert error = new Alert(Alert.AlertType.ERROR);

    public void setKelolaUserController(KelolaUserController kelolaUserController) {
        tableRole.setItems(kelolaUserController.getRoles());
        this.kelolaUserController = kelolaUserController;
    }

    public void setAdminFormController(AdminFormController adminFormController) {
        tableRole.setItems(adminFormController.getRoles());
        this.adminFormController = adminFormController;
    }


    public void btnSaveAction(ActionEvent actionEvent) {
        Role role = new Role();
        role.setNama(txtNama.getText());
        boolean notDuplicate = true;
        for (Role r : adminFormController.getRoles()){
            if(r.getNama().equals(role.getNama())){
                notDuplicate = false;
            }
        }
        if(notDuplicate){
            adminFormController.getRoleDao().addData(role);
            clearForm();
            refreshTable();
        }else if(txtNama.getText().isEmpty()){
            error.setContentText("Please Fill Name");
            error.show();
        }else{
            error.setContentText("ID Duplicate");
            error.show();
        }
    }

    public void btnResetAction(ActionEvent actionEvent) {
        clearForm();
        refreshTable();
    }

    public void btnUpdateAction(ActionEvent actionEvent) {
        selectedItems.setNama(txtNama.getText());
//        boolean notDuplicate = getRoles().stream().filter(d -> d.getNama().equals(txtNama.getText())).count() == 0;
        boolean notDuplicate = true;
        int count = 0;
        for (Role r : adminFormController.getRoles()){
            System.out.println(r.getNama()+"===="+selectedItems.getNama()+notDuplicate);
            if(r.getNama().equals(selectedItems.getNama())){
                notDuplicate = false;
                count += 1;
            }
        }
        if(notDuplicate || count==1){
            adminFormController.getRoleDao().updateData(selectedItems);
            refreshTable();
            clearForm();
        }else if(txtNama.getText().isEmpty()){
            error.setContentText("Please Fill Name");
            error.show();
        }else{
            error.setContentText("ID Duplicate");
            error.show();
        }
    }

    public void btnDeleteAction(ActionEvent actionEvent) {
        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setContentText("Apakah anda yakin mau menghapus data ? ");
        deleteConfirmation.showAndWait();
        if (deleteConfirmation.getResult() == ButtonType.OK) {
            adminFormController.getRoleDao().deleteData(selectedItems);
            clearForm();
            refreshTable();
        }
    }

    public void tableRoleClicked(MouseEvent mouseEvent) {
        selectedItems = tableRole.getSelectionModel().getSelectedItem();
        if(selectedItems != null){
            txtNama.setText(selectedItems.getNama());
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void clearForm(){
        txtNama.clear();
        selectedItems = null;
        tableRole.getSelectionModel().clearSelection();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
    }

    private void refreshTable(){
        adminFormController.getRoles().clear();
        adminFormController.getRoles().addAll(adminFormController.getRoleDao().showAll());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));
        colNamaRole.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNama()));
    }

    public void mnHomeAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/AdminForm.fxml"));
        VBox vBox = loader.load();
        AdminFormController controller = loader.getController();
        controller.setKelolaRoleController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    public void mnKelolaUserAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaUser.fxml"));
        AnchorPane anchorPane = loader.load();
        KelolaUserController controller = loader.getController();
        controller.setKelolaRoleController(this);
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void mnLogoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }
}
