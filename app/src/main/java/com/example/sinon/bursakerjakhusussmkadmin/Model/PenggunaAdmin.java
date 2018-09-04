package com.example.sinon.bursakerjakhusussmkadmin.Model;

public class PenggunaAdmin {

    private String NamaAdmin;
    private String Nama;
    private String Sandi;

    public PenggunaAdmin() {
    }

    public PenggunaAdmin(String nama, String sandi) {
        Nama = nama;
        Sandi = sandi;
    }

    public String getNamaAdmin() {
        return NamaAdmin;
    }

    public void setNamaAdmin(String namaAdmin) {
        NamaAdmin = namaAdmin;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getSandi() {
        return Sandi;
    }

    public void setSandi(String sandi) {
        Sandi = sandi;
    }
}
