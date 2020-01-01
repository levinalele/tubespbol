package com.lc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class JenisKendaraanController {
    @FXML
    private Label lblJenisKendaraan;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtJenisKendaraan;
    @FXML
    private Label lblTarif;
    @FXML
    private TextField txtTarif;
    @FXML
    private TableView tableTarif;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colTarif;

    @FXML
    private void saveAct(ActionEvent actionEvent) {
    }

    @FXML
    private void updateAct(MouseEvent mouseEvent) {
    }

    @FXML
    private void deleteAct(ActionEvent actionEvent) {
    }

    @FXML
    private void tableclickedAct(MouseEvent mouseEvent) {
    }
}
