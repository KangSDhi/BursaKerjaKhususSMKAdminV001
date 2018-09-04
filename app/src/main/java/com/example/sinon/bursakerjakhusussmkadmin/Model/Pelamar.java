package com.example.sinon.bursakerjakhusussmkadmin.Model;

public class Pelamar {
    private String Nama;
    private String Tahun_kelulusan;
    private String Asal_Sekolah;
    private String Jurusan;
    private String Nomor_ponsel;
    private String Email;
    private String Perusahaan;
    private String Nama_lowongan;
    private String Berlaku;
    private String Status;
    private String Pesan;

    public Pelamar() {
    }

    public Pelamar(String nama, String tahun_kelulusan, String asal_Sekolah, String jurusan, String nomor_ponsel, String email, String perusahaan, String nama_lowongan, String berlaku, String status, String pesan) {
        Nama = nama;
        Tahun_kelulusan = tahun_kelulusan;
        Asal_Sekolah = asal_Sekolah;
        Jurusan = jurusan;
        Nomor_ponsel = nomor_ponsel;
        Email = email;
        Perusahaan = perusahaan;
        Nama_lowongan = nama_lowongan;
        Berlaku = berlaku;
        Status = status;
        Pesan = pesan;
    }

    public String getPesan() {
        return Pesan;
    }

    public void setPesan(String pesan) {
        Pesan = pesan;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getTahun_kelulusan() {
        return Tahun_kelulusan;
    }

    public void setTahun_kelulusan(String tahun_kelulusan) {
        Tahun_kelulusan = tahun_kelulusan;
    }

    public String getAsal_Sekolah() {
        return Asal_Sekolah;
    }

    public void setAsal_Sekolah(String asal_Sekolah) {
        Asal_Sekolah = asal_Sekolah;
    }

    public String getJurusan() {
        return Jurusan;
    }

    public void setJurusan(String jurusan) {
        Jurusan = jurusan;
    }

    public String getNomor_ponsel() {
        return Nomor_ponsel;
    }

    public void setNomor_ponsel(String nomor_ponsel) {
        Nomor_ponsel = nomor_ponsel;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPerusahaan() {
        return Perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        Perusahaan = perusahaan;
    }

    public String getNama_lowongan() {
        return Nama_lowongan;
    }

    public void setNama_lowongan(String nama_lowongan) {
        Nama_lowongan = nama_lowongan;
    }

    public String getBerlaku() {
        return Berlaku;
    }

    public void setBerlaku(String berlaku) {
        Berlaku = berlaku;
    }
}
