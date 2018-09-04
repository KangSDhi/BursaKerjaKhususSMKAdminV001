package com.example.sinon.bursakerjakhusussmkadmin.Model;

import java.util.Date;

public class Lowongan {

    private String Nama;
    private String Perusahaan;
    private String PerusahaanId;
    private String Persyaratan;
    private String Berlaku;
    private String LogoPerusahaan;
    private long Waktu;

    public Lowongan() {
    }

    public Lowongan(String nama, String perusahaan, String perusahaanId, String persyaratan, String berlaku, String logoPerusahaan) {
        Nama = nama;
        Perusahaan = perusahaan;
        PerusahaanId = perusahaanId;
        Persyaratan = persyaratan;
        Berlaku = berlaku;
        LogoPerusahaan = logoPerusahaan;
        Waktu = new Date().getTime();
    }

    public String getLogoPerusahaan() {
        return LogoPerusahaan;
    }

    public void setLogoPerusahaan(String logoPerusahaan) {
        LogoPerusahaan = logoPerusahaan;
    }

    public String getPersyaratan() {
        return Persyaratan;
    }

    public void setPersyaratan(String persyaratan) {
        Persyaratan = persyaratan;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getPerusahaan() {
        return Perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        Perusahaan = perusahaan;
    }

    public String getPerusahaanId() {
        return PerusahaanId;
    }

    public void setPerusahaanId(String perusahaanId) {
        PerusahaanId = perusahaanId;
    }

    public String getBerlaku() {
        return Berlaku;
    }

    public void setBerlaku(String berlaku) {
        Berlaku = berlaku;
    }

    public long getWaktu() {
        return Waktu;
    }

    public void setWaktu(long waktu) {
        Waktu = waktu;
    }

}
