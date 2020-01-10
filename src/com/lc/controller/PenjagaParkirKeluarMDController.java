package com.lc.controller;

import com.lc.Main;
import com.lc.dao.jenisDaoImpl;
import com.lc.dao.kendaraanDaoImpl;
import com.lc.dao.parkirDaoImpl;
import com.lc.dao.voucherDaoImpl;
import com.lc.entity.*;
import com.lc.util.dbHelper;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources_zh_TW;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PenjagaParkirKeluarMDController implements Initializable {
    public TextField txtPlatNomor;
    public Button btnKeluar;
    public ComboBox<Voucher> cmbxVoucher;
    public TextArea txtHarga;
    public Button btnBayar;
    public Button btnUmum;
    public parkirDaoImpl parkirDao;
    public kendaraanDaoImpl kendaraanDao;
    public jenisDaoImpl jenisDao;
    public PenjagaParkirUmumFormController penjagaParkirUmumFormController;
    public TextArea txtPos;
    public ManagerParkirFormController managerParkirFormController;
    public ObservableList<Voucher> vouchers;
    public voucherDaoImpl voucherDao;
    Alert error = new Alert(Alert.AlertType.ERROR);
    public Integer idparkir;

    public voucherDaoImpl getVoucherDao() {
        if (voucherDao == null) {
            voucherDao = new voucherDaoImpl();
        }
        return voucherDao;
    }

    public ObservableList<Voucher> getVouchers() {
        if (vouchers == null) {
            vouchers = FXCollections.observableArrayList();
            vouchers.addAll(getVoucherDao().showAll());
        }
        return vouchers;
    }


    public void setPenjagaParkirUmumFormController(PenjagaParkirUmumFormController penjagaParkirUmumFormController) {
        this.penjagaParkirUmumFormController = penjagaParkirUmumFormController;
    }

    public jenisDaoImpl getJenisDao() {
        if (jenisDao == null) {
            jenisDao = new jenisDaoImpl();
        }
        return jenisDao;
    }

    public kendaraanDaoImpl getKendaraanDao() {
        if (kendaraanDao == null) {
            kendaraanDao = new kendaraanDaoImpl();
        }
        return kendaraanDao;
    }

    public parkirDaoImpl getParkirDao() {
        if (parkirDao == null) {
            parkirDao = new parkirDaoImpl();
        }
        return parkirDao;
    }

    public void actionbtnUmum(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/PenjagaParkirUmumForm.fxml"));
        VBox vBox = loader.load();
        PenjagaParkirUmumFormController controller = loader.getController();
        controller.setPenjagaParkirKeluarMDController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    public void actionbtnKeluar(ActionEvent actionEvent) {
        if (txtPos.getText().isEmpty()) {
            error.setContentText("Isikan Nomor Pos !");
            error.show();
        } else {
            Pos pos = new Pos();
            pos.setId(Integer.valueOf(txtPos.getText()));

            Kendaraan kendaraan = new Kendaraan();
            kendaraan.setPlatnomor(txtPlatNomor.getText());
            kendaraan.setStatus("1");
            Integer status_ijin = getKendaraanDao().getStatusIjin(kendaraan);
            Integer idkendaraan = getKendaraanDao().getIdByPlatNomor(kendaraan);
            Integer idjenis = getKendaraanDao().getJenisById(idkendaraan);
            System.out.println("idkendaraan : " + idjenis);
            Integer tarif = getJenisDao().getHargaById(idjenis);

            Parkir parkir = new Parkir();
            parkir.setKendaraan_id(kendaraan);
            idparkir = getParkirDao().getIdByKendaraan(String.valueOf(idkendaraan));

            String hehe = getParkirDao().getDateById(idparkir);
            LocalDate tglmasuk = LocalDate.parse(hehe);
            Time jammasuk = getParkirDao().getTimeById(idparkir);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String date2 = dateFormat.format(date);
            LocalDate tgl = LocalDate.parse(date2);

            DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            Time time = Time.valueOf(dateFormat2.format(cal.getTime()));
            long kurang = time.getTime() - jammasuk.getTime();
            Integer totalbayar = 0;
            Integer totaldenda = 0;
            if (cmbxVoucher.getValue() == null) {
                Long totaljam = (((kurang / 1000) / 60) / 60) + 1;
                if(totaljam >= 24 && status_ijin==0){
                    Integer totalhari = Math.toIntExact(totaljam % 24);
                    totaldenda = 15000*totalhari;
                }
                Kendaraan k = new Kendaraan();
                k.setStatus_ijin("0");
                k.setPlatnomor(txtPlatNomor.getText());
                getKendaraanDao().updateStatusIjin(k);
                totalbayar = tarif + totaldenda;
                parkir.setTotal_bayar(String.valueOf(totalbayar));
                parkir.setStatus("1");
                parkir.setJam_keluar(time);
                parkir.setPos_id(pos);
                parkir.setId(idparkir);
                getParkirDao().updateData(parkir);
            } else {
                parkir.setVoucher_id(cmbxVoucher.getValue());
                if (cmbxVoucher.getValue().getId() == 1) {
                    parkir.setTotal_bayar("0");

                } else {
                    parkir.setTotal_bayar(String.valueOf(tarif));
                }
                parkir.setStatus("1");
                parkir.setJam_keluar(time);
                parkir.setPos_id(pos);
                parkir.setId(idparkir);
                getParkirDao().updateDataWithVoucher(parkir);
            }
            txtHarga.setText(String.valueOf(totalbayar));
        }
    }

    public void actionbtnBayar(ActionEvent actionEvent) {
        try {
            HashMap<String, Object> para = new HashMap<>();
            para.put("idparkir",idparkir);
            JasperPrint jp = JasperFillManager.fillReport("report/report1.jasper",para,dbHelper.createMySQLConnection());
            JasperViewer viewer = new JasperViewer(jp,false);
            viewer.setTitle("Struk Parkir");
            viewer.setVisible(true);


        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        //print ke IReport
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbxVoucher.setItems(getVouchers());
    }

    public void mnHomeAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/PenjagaParkirUmumForm.fxml"));
        VBox vBox = loader.load();
        PenjagaParkirUmumFormController controller = loader.getController();
        controller.setPenjagaParkirKeluarMDController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    public void mnLogoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }
}
