package com.lc.controller;

import com.lc.Main;
import com.lc.entity.JenisKendaraan;
import javafx.beans.property.SimpleStringProperty;
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

public class KelolaJenisKendaraanController implements Initializable {
    public Button btnSave;
    public Button btnReset;
    public Button btnDelete;
    public Button btnUpdate;
    public TableView<JenisKendaraan> tableJenisKendaraan;
    public TableColumn<JenisKendaraan,String> colID;
    public TableColumn<JenisKendaraan,String> colJenisKendaraan;
    public TableColumn<JenisKendaraan,String> colTarif;
    public TextField txtJenisKendaraan;
    public TextField txtTarif;
    public JenisKendaraan selectedItems;
    public ManagerParkirFormController managerParkirFormController;
    public KelolaVoucherController kelolaVoucherController;
    Alert error = new Alert(Alert.AlertType.ERROR);

    public void setKelolaVoucherController(KelolaVoucherController kelolaVoucherController) {
        tableJenisKendaraan.setItems(kelolaVoucherController.managerParkirFormController.getJenisKendaraans());
        this.kelolaVoucherController = kelolaVoucherController;
    }

    public void setManagerParkirFormController(ManagerParkirFormController managerParkirFormController) {
        tableJenisKendaraan.setItems(managerParkirFormController.getJenisKendaraans());
        this.managerParkirFormController = managerParkirFormController;
    }

    public void mnHomeAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ManagerParkirForm.fxml"));
        VBox vBox = loader.load();
        ManagerParkirFormController controller = loader.getController();
        controller.setKelolaJenisKendaraanController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    public void mnKelolaVoucherAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaVoucher.fxml"));
        AnchorPane anchorPane = loader.load();
        KelolaVoucherController controller = loader.getController();
        controller.setKelolaJenisKendaraanController(this);
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void mnLogoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void btnSaveAction(ActionEvent actionEvent) {
        JenisKendaraan jenisKendaraan = new JenisKendaraan();
        jenisKendaraan.setJenis(txtJenisKendaraan.getText());
        jenisKendaraan.setTarif(txtTarif.getText());
        boolean notDuplicate = managerParkirFormController.getJenisKendaraans().stream().filter(d -> d.getJenis() == jenisKendaraan.getJenis()).count() == 0;
        if(notDuplicate){
            managerParkirFormController.getJenisKendaraanDao().addData(jenisKendaraan);
            clearForm();
            refreshTable();
        }else if(txtJenisKendaraan.getText().isEmpty() || txtTarif.getText().isEmpty()){
            error.setContentText("Please Fill Jenis Kendaraan/Tarif");
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

    public void btnDeleteAction(ActionEvent actionEvent) {
        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setContentText("Apakah anda yakin mau menghapus data ? ");
        deleteConfirmation.showAndWait();
        if (deleteConfirmation.getResult() == ButtonType.OK) {
            managerParkirFormController.getJenisKendaraanDao().deleteData(selectedItems);
            clearForm();
            refreshTable();
        }
    }

    public void btnUpdateAction(ActionEvent actionEvent) {
        selectedItems.setJenis(txtJenisKendaraan.getText());
        selectedItems.setTarif(txtTarif.getText());
        boolean notDuplicate = true;
        int count = 0;
        for (JenisKendaraan jk : managerParkirFormController.getJenisKendaraans()){
            if(jk.getJenis().equals(selectedItems.getJenis())){
                notDuplicate = false;
                count += 1;
            }
        }
        if(notDuplicate || count==1){
            managerParkirFormController.getJenisKendaraanDao().updateData(selectedItems);
            refreshTable();
            clearForm();
        }else if(txtJenisKendaraan.getText().isEmpty() || txtTarif.getText().isEmpty()){
            error.setContentText("Please Fill Name");
            error.show();
        }else{
            error.setContentText("ID Duplicate");
            error.show();
        }
    }

    public void tableRoleClicked(MouseEvent mouseEvent) {
        selectedItems = tableJenisKendaraan.getSelectionModel().getSelectedItem();
        if(selectedItems != null){
            txtTarif.setText(selectedItems.getTarif());
            txtJenisKendaraan.setText(selectedItems.getJenis());
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void clearForm(){
        txtJenisKendaraan.clear();
        txtTarif.clear();
        selectedItems = null;
        tableJenisKendaraan.getSelectionModel().clearSelection();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
    }

    private void refreshTable(){
        managerParkirFormController.getJenisKendaraans().clear();
        managerParkirFormController.getJenisKendaraans().addAll(managerParkirFormController.getJenisKendaraanDao().showAll());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));
        colJenisKendaraan.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getJenis())));
        colTarif.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTarif()));
    }
}
