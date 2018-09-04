package com.example.sinon.bursakerjakhusussmkadmin.Model;

import java.util.Date;

public class PenggunaKlien {
    private String Nama;
    private String Tempat_Lahir;
    private String Tanggal_lahir;
    private String Jenis_kelamin;
    private String Tinggi_badan;
    private String Berat_badan;
    private String Tahun_kelulusan;
    private String Asal_sekolah;
    private String Jurusan;
    private String Alamat;
    private String Kabupaten_atau_kota;
    private String Ponsel;
    private String Email;
    private String Waktu_pendaftaran;
    private String NamaPengguna;
    private String Sandi;
    private String Foto_Profil;

    public PenggunaKlien() {
    }

    public PenggunaKlien(String nama, String tempat_Lahir, String tanggal_lahir, String jenis_kelamin, String tinggi_badan, String berat_badan, String tahun_kelulusan, String asal_sekolah, String jurusan, String alamat, String kabupaten_atau_kota, String ponsel, String email, String namaPengguna, String sandi, String foto_Profil) {
        Nama = nama;
        Tempat_Lahir = tempat_Lahir;
        Tanggal_lahir = tanggal_lahir;
        Jenis_kelamin = jenis_kelamin;
        Tinggi_badan = tinggi_badan;
        Berat_badan = berat_badan;
        Tahun_kelulusan = tahun_kelulusan;
        Asal_sekolah = asal_sekolah;
        Jurusan = jurusan;
        Alamat = alamat;
        Kabupaten_atau_kota = kabupaten_atau_kota;
        Ponsel = ponsel;
        Email = email;
        NamaPengguna = namaPengguna;
        Sandi = sandi;
        Foto_Profil = foto_Profil;

        Waktu_pendaftaran = new Date().toString();
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getTempat_Lahir() {
        return Tempat_Lahir;
    }

    public void setTempat_Lahir(String tempat_Lahir) {
        Tempat_Lahir = tempat_Lahir;
    }

    public String getTanggal_lahir() {
        return Tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        Tanggal_lahir = tanggal_lahir;
    }

    public String getJenis_kelamin() {
        return Jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        Jenis_kelamin = jenis_kelamin;
    }

    public String getTinggi_badan() {
        return Tinggi_badan;
    }

    public void setTinggi_badan(String tinggi_badan) {
        Tinggi_badan = tinggi_badan;
    }

    public String getBerat_badan() {
        return Berat_badan;
    }

    public void setBerat_badan(String berat_badan) {
        Berat_badan = berat_badan;
    }

    public String getTahun_kelulusan() {
        return Tahun_kelulusan;
    }

    public void setTahun_kelulusan(String tahun_kelulusan) {
        Tahun_kelulusan = tahun_kelulusan;
    }

    public String getAsal_sekolah() {
        return Asal_sekolah;
    }

    public void setAsal_sekolah(String asal_sekolah) {
        Asal_sekolah = asal_sekolah;
    }

    public String getJurusan() {
        return Jurusan;
    }

    public void setJurusan(String jurusan) {
        Jurusan = jurusan;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getKabupaten_atau_kota() {
        return Kabupaten_atau_kota;
    }

    public void setKabupaten_atau_kota(String kabupaten_atau_kota) {
        Kabupaten_atau_kota = kabupaten_atau_kota;
    }

    public String getPonsel() {
        return Ponsel;
    }

    public void setPonsel(String ponsel) {
        Ponsel = ponsel;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNamaPengguna() {
        return NamaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        NamaPengguna = namaPengguna;
    }

    public String getSandi() {
        return Sandi;
    }

    public void setSandi(String sandi) {
        Sandi = sandi;
    }

    public String getFoto_Profil() {
        return Foto_Profil;
    }

    public void setFoto_Profil(String foto_Profil) {
        Foto_Profil = foto_Profil;
    }
}
