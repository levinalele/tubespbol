package com.lc.entity;

public class Pegawai {
    private String id;
    private String nik;
    private String nama;
    private String password;
    private RolePegawai Role_Pegawai_id;

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

    public void setNik(String username) {
        this.nik = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolePegawai getRole_Pegawai_id() {
        return Role_Pegawai_id;
    }

    public void setRole_Pegawai_id(RolePegawai role_Pegawai_id) {
        this.Role_Pegawai_id = role_Pegawai_id;
    }
}
