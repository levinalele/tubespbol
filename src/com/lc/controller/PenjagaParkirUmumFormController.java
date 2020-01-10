package com.lc.controller;

import com.lc.Main;
import com.lc.dao.jenisDaoImpl;
import com.lc.dao.kendaraanDaoImpl;
import com.lc.dao.parkirDaoImpl;
import com.lc.dao.voucherDaoImpl;
import com.lc.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class PenjagaParkirUmumFormController implements Initializable {
    public ComboBox<Voucher> cmbxVoucher;
    public TextArea txtPos;
    @FXML
    private Button btnMahasiswaDosen;
    @FXML
    private Button btnJamMasukUmum;
    @FXML
    private TextField txtPlatUmum;
    @FXML
    private ComboBox<JenisKendaraan> cmbxJenisKendaraanUmum;
    @FXML
    private Button btnJamKeluarUmum;
    @FXML
    private TextArea txtAKetUmum;
    @FXML
    private TextArea txtAKetBayarUmum;
    @FXML
    private Button btnPayUmum;
    private ObservableList<Voucher> vouchers;
    private voucherDaoImpl voucherDao;
    private ObservableList<JenisKendaraan> jenisKendaraans;
    private jenisDaoImpl jenisDao;
    public PenjagaParkirKeluarMDController penjagaParkirKeluarMDController;
    private kendaraanDaoImpl kendaraanDao;
    private parkirDaoImpl parkirDao;
    Alert error = new Alert(Alert.AlertType.ERROR);

    public parkirDaoImpl getParkirDao() {
        if(parkirDao==null){
            parkirDao = new parkirDaoImpl();
        }
        return parkirDao;
    }

    public kendaraanDaoImpl getKendaraanDao() {
        if(kendaraanDao==null){
            kendaraanDao =new kendaraanDaoImpl();
        }
        return kendaraanDao;
    }

    private ObservableList<Voucher> getVouchers() {
        if(vouchers==null){
            vouchers = FXCollections.observableArrayList();
            vouchers.addAll(getVoucherDao().showAll());
        }
        return vouchers;
    }

    private voucherDaoImpl getVoucherDao() {
        if(voucherDao==null){
            voucherDao = new voucherDaoImpl();
        }
        return voucherDao;
    }

    public void setPenjagaParkirKeluarMDController(PenjagaParkirKeluarMDController penjagaParkirKeluarMDController) {
        this.penjagaParkirKeluarMDController = penjagaParkirKeluarMDController;
    }

    private jenisDaoImpl getJenisDao() {
        if(jenisDao==null){
            jenisDao=new jenisDaoImpl();
        }
        return jenisDao;
    }

    private ObservableList<JenisKendaraan> getJenisKendaraans() {
        if(jenisKendaraans==null){
            jenisKendaraans = FXCollections.observableArrayList();
            jenisKendaraans.addAll(getJenisDao().showAll());
        }
        return jenisKendaraans;
    }

    @FXML
    private void MahasiswaDosenAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/PenjagaParkirKeluarMD.fxml"));
        VBox vBox = loader.load();
        PenjagaParkirKeluarMDController controller = loader.getController();
        controller.setPenjagaParkirUmumFormController(this);
        Main.getPrimaryStage().setScene(new Scene(vBox));
    }

    @FXML
    private void JamMasukUmumAction(ActionEvent actionEvent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String date2 = dateFormat.format(date);
        LocalDate tgl = LocalDate.parse(date2);

        DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Time time = Time.valueOf(dateFormat2.format(cal.getTime()));

        String jenis = cmbxJenisKendaraanUmum.getValue().getJenis();
        System.out.println(jenis);
        Parkir parkir = new Parkir();
        parkir.setPlat_nomor_umum(txtPlatUmum.getText());
        parkir.setStatus("0");
        parkir.setTotal_bayar("0");
        parkir.setJenis_kendaraan_umum(cmbxJenisKendaraanUmum.getValue().getJenis());
        parkir.setTanggal(tgl);
        parkir.setJam_masuk(time);
        getParkirDao().addDataUmum(parkir);
    }

    @FXML
    private void JamKeluarUmumAction(ActionEvent actionEvent) {
        if (txtPos.getText().isEmpty()) {
            error.setContentText("Isikan Nomor Pos !");
            error.show();
        } else {
            Pos pos = new Pos();
            pos.setId(Integer.valueOf(txtPos.getText()));

            Kendaraan kendaraan = new Kendaraan();
            kendaraan.setPlatnomor(txtPlatUmum.getText());
            kendaraan.setStatus("1");
            Integer idkendaraan = getKendaraanDao().getIdByPlatNomor(kendaraan);
            Integer idjenis = getJenisDao().getHargaById(cmbxJenisKendaraanUmum.getValue().getId());
            System.out.println("idkendaraan : " + idjenis);
//            Integer tarif = getJenisDao().getHargaById(idjenis);

            Parkir parkir = new Parkir();
            parkir.setPlat_nomor_umum(txtPlatUmum.getText());
            Integer idparkir = getParkirDao().getIdByPlatNomor(parkir.getPlat_nomor_umum());
            parkir.setId(idparkir);

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
            Integer totaldenda  = 0;
            if (cmbxVoucher.getValue() == null) {
                Long totaljam = (((kurang / 1000) / 60) / 60) + 1;
                if(totaljam >= 24){
                    Integer totalhari = Math.toIntExact(totaljam % 24);
                    totaldenda = 15000*totalhari;
                }
                totalbayar = (idjenis * Math.toIntExact(totaljam))+totaldenda;
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
                    parkir.setTotal_bayar(String.valueOf(idjenis));
                }
                parkir.setStatus("1");
                parkir.setJam_keluar(time);
                parkir.setPos_id(pos);
                parkir.setId(idparkir);
                getParkirDao().updateDataWithVoucher(parkir);
            }
            txtAKetBayarUmum.setText(String.valueOf(totalbayar));
        }
    }

    @FXML
    private void PayUmumAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbxJenisKendaraanUmum.setItems(getJenisKendaraans());
        cmbxVoucher.setItems(getVouchers());
    }

    public void mnLogoutAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/test.fxml"));
        AnchorPane anchorPane = loader.load();
        Main.getPrimaryStage().setScene(new Scene(anchorPane));
    }
}
