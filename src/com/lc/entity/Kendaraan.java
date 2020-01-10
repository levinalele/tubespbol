package com.lc.entity;

public class Kendaraan {
    private String id;
    private JenisKendaraan jenisKendaraan_id;
    private User user_id;
    private String status;
    private String platnomor;
    private String status_ijin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JenisKendaraan getJenisKendaraan_id() {
        return jenisKendaraan_id;
    }

    public void setJenisKendaraan_id(JenisKendaraan jenisKendaraan_id) {
        this.jenisKendaraan_id = jenisKendaraan_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlatnomor() {
        return platnomor;
    }

    public void setPlatnomor(String platnomor) {
        this.platnomor = platnomor;
    }

    public String getStatus_ijin() {
        return status_ijin;
    }

    public void setStatus_ijin(String status_ijin) {
        this.status_ijin = status_ijin;
    }
}
