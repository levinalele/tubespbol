package com.lc.entity;

import java.sql.Time;
import java.util.Date;

public class Parkir {
    private Integer id;
    private Date tanggalmasuk;
    private  Date tanggalkeluar;
    private Time jammasuk;
    private Time jamkeluar;
    private String platnomor;
    private String status;
    private Voucher voucher_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTanggalmasuk() {
        return tanggalmasuk;
    }

    public void setTanggalmasuk(Date tanggalmasuk) {
        this.tanggalmasuk = tanggalmasuk;
    }

    public Date getTanggalkeluar() {
        return tanggalkeluar;
    }

    public void setTanggalkeluar(Date tanggalkeluar) {
        this.tanggalkeluar = tanggalkeluar;
    }

    public Time getJammasuk() {
        return jammasuk;
    }

    public void setJammasuk(Time jammasuk) {
        this.jammasuk = jammasuk;
    }

    public Time getJamkeluar() {
        return jamkeluar;
    }

    public void setJamkeluar(Time jamkeluar) {
        this.jamkeluar = jamkeluar;
    }

    public String getPlatnomor() {
        return platnomor;
    }

    public void setPlatnomor(String platnomor) {
        this.platnomor = platnomor;
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
}
