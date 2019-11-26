package com.lc.entity;

public class Kendaraan {
    private Integer id;
    private String platnomor;
    private JenisKendaraan jenisKendaraan_id;
    private User user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatnomor() {
        return platnomor;
    }

    public void setPlatnomor(String platnomor) {
        this.platnomor = platnomor;
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
}
