package com.lc.entity;

public class User {
    private String id;
    private  String nama;
    private  String nik;
    private Parkir parkir_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public Parkir getParkir_id() {
        return parkir_id;
    }

    public void setParkir_id(Parkir parkir_id) {
        this.parkir_id = parkir_id;
    }
}
