package com.lc.entity;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class Parkir {
    private Integer id;
    private LocalDate tanggal;
    private Time jam_masuk;
    private Time jam_keluar;
    private String total_bayar;
    private String status;
    private Voucher voucher_id;
    private Pos pos_id;
    private Kendaraan kendaraan_id;
    private String plat_nomor_umum;
    private String jenis_kendaraan_umum;

    public Kendaraan getKendaraan_id() {
        return kendaraan_id;
    }

    public void setKendaraan_id(Kendaraan kendaraan_id) {
        this.kendaraan_id = kendaraan_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public Time getJam_masuk() {
        return jam_masuk;
    }

    public void setJam_masuk(Time jam_masuk) {
        this.jam_masuk = jam_masuk;
    }

    public Time getJam_keluar() {
        return jam_keluar;
    }

    public void setJam_keluar(Time jam_keluar) {
        this.jam_keluar = jam_keluar;
    }

    public String getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(String total_bayar) {
        this.total_bayar = total_bayar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Voucher getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(Voucher voucher_id) {
        this.voucher_id = voucher_id;
    }

    public Pos getPos_id() {
        return pos_id;
    }

    public void setPos_id(Pos pos_id) {
        this.pos_id = pos_id;
    }

    public String getPlat_nomor_umum() {
        return plat_nomor_umum;
    }

    public void setPlat_nomor_umum(String plat_nomor_umum) {
        this.plat_nomor_umum = plat_nomor_umum;
    }

    public String getJenis_kendaraan_umum() {
        return jenis_kendaraan_umum;
    }

    public void setJenis_kendaraan_umum(String jenis_kendaraan_umum) {
        this.jenis_kendaraan_umum = jenis_kendaraan_umum;
    }
}
