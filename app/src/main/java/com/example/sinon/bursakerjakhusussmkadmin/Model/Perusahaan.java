package com.example.sinon.bursakerjakhusussmkadmin.Model;

public class Perusahaan {

    private String Nama;
    private String Gambar;

    public Perusahaan() {
    }

    public Perusahaan(String nama, String gambar) {
        Nama = nama;
        Gambar = gambar;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }
}
