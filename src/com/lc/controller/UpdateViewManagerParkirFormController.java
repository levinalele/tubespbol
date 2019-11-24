package com.lc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class UpdateViewManagerParkirFormController {
    @FXML
    private TextField txtNRPNIKUpdate;
    @FXML
    private TextField txtPlatNoUpdate;
    @FXML
    private ComboBox cmbxJenisKendaraanUpdate;
    @FXML
    private Button btnUpdateMhs;
    @FXML
    private Button btnUpdateDosen;
    @FXML
    private TableView tableviewMahasiswa;
    @FXML
    private TableColumn colNRP;
    @FXML
    private TableColumn colPlatMhs;
    @FXML
    private TableColumn colJenisKendaraanMhs;
    @FXML
    private TableView tableviewDosen;
    @FXML
    private TableColumn colNIK;
    @FXML
    private TableColumn colPlatDsn;
    @FXML
    private TableColumn colJenisKendaraanDsn;

    @FXML
    private void UpdateMahasiswaAction(ActionEvent actionEvent) {
    }

    @FXML
    private void UpdateDosenAction(ActionEvent actionEvent) {
    }

    @FXML
    private void tableclickMahasiswa(MouseEvent mouseEvent) {
    }

    @FXML
    private void tableclickDosen(MouseEvent mouseEvent) {
    }
}
