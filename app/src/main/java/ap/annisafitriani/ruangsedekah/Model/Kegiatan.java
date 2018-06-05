package ap.annisafitriani.ruangsedekah.Model;

import java.io.Serializable;

/**
 * Created by Hp on 4/18/2018.
 */

public class Kegiatan implements Serializable {

    public String nama, deskripsi, tanggal, waktu, id, lokasi, userId;
    public Double lang, lat;


    public Kegiatan(String nama, String tanggal, String waktu, String deskripsi, String id, String lokasi, String userId, Double lat, Double lang) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.id = id;
        this.lokasi = lokasi;
        this.lat = lat;
        this.lang = lang;
        this.userId = userId;

    }

    public Kegiatan() {

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {

        this.waktu = waktu;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat =lat;
    }

    public Double getLang() {
        return lang;
    }

    public void setLang(Double lang) {
        this.lang = lang;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
