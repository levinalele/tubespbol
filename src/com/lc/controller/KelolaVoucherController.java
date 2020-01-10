package com.lc.controller;

import com.lc.Main;
import com.lc.entity.JenisKendaraan;
import com.lc.entity.Voucher;
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

public class KelolaVoucherController implements Initializable {
    public TextField txtNama;
    public Button btnSave;
    public Button btnReset;
    public Button btnDelete;
    public Button btnUpdate;
    public TableView<Voucher> tableVoucher;
    public TableColumn<Voucher,String> colID;
    public TableColumn<Voucher,String> colVoucher;
    public ManagerParkirFormController managerParkirFormController;
    public Voucher selectedItems;
    public KelolaJenisKendaraanController kelolaJenisKendaraanController;
    Alert error = new Alert(Alert.AlertType.ERROR);

    public void setKelolaJenisKendaraanController(KelolaJenisKendaraanController kelolaJenisKendaraanController) {
        tableVoucher.setItems(kelolaJenisKendaraanController.managerParkirFormController.getVouchers());
        this.kelolaJenisKendaraanController = kelolaJenisKendaraanController;
    }

    public void setManagerParkirFormController(ManagerParkirFormController managerParkirFormController) {
        tableVoucher.setItems(managerParkirFormController.getVouchers());
        this.managerParkirFormController = managerParkirFormController;
    }

    public void mnHomeAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ManagerParkirForm.fxml"));
        VBox vBox = loader.load();
        ManagerParkirFormController controller = loader.getController();
        controller.setKelolaVoucherController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    public void mnKelolaJenisKendaraanAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/KelolaJenisKendaraan.fxml"));
        AnchorPane anchorPane = loader.load();
        KelolaJenisKendaraanController controller = loader.getController();
        controller.setKelolaVoucherController(this);
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void mnLogoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }

    public void btnSaveAction(ActionEvent actionEvent) {
        Voucher voucher = new Voucher();
        voucher.setJenis(txtNama.getText());
        boolean notDuplicate = managerParkirFormController.getVouchers().stream().filter(d -> d.getJenis() == voucher.getJenis()).count() == 0;
        if(notDuplicate){
            managerParkirFormController.getVoucherDao().addData(voucher);
            clearForm();
            refreshTable();
        }else if(txtNama.getText().isEmpty()){
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
            managerParkirFormController.getVoucherDao().deleteData(selectedItems);
            clearForm();
            refreshTable();
        }
    }

    public void btnUpdateAction(ActionEvent actionEvent) {
        selectedItems.setJenis(txtNama.getText());
        boolean notDuplicate = true;
        int count = 0;
        for (Voucher v : managerParkirFormController.getVouchers()){
            if(v.getJenis().equals(selectedItems.getJenis())){
                notDuplicate = false;
                count += 1;
            }
        }
        if(notDuplicate || count==1){
            managerParkirFormController.getVoucherDao().updateData(selectedItems);
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

    public void tableVoucherClicked(MouseEvent mouseEvent) {
        selectedItems = tableVoucher.getSelectionModel().getSelectedItem();
        if(selectedItems != null){
            txtNama.setText(selectedItems.getJenis());
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));
        colVoucher.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getJenis())));
    }

    private void clearForm(){
        txtNama.clear();
        selectedItems = null;
        tableVoucher.getSelectionModel().clearSelection();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
    }

    private void refreshTable(){
        managerParkirFormController.getVouchers().clear();
        managerParkirFormController.getVouchers().addAll(managerParkirFormController.getVoucherDao().showAll());
    }
}
