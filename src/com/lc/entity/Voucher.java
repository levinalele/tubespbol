package com.lc.entity;

public class Voucher {
    private Integer id;
    private String jenis;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    @Override
    public String toString() {
        return jenis;
    }
}
