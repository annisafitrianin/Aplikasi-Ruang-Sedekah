package ap.annisafitriani.ruangsedekah.Model;

import java.io.Serializable;

/**
 * Created by Hp on 4/18/2018.
 */

public class Kegiatan implements Serializable {

    public String nama, deskripsi, photo, tanggal, waktu, lokasi, id;
    public double lat,lang;


    public Kegiatan() { }

    public Kegiatan(String nama, String tanggal, String waktu, String deskripsi, String id, String lokasi, String userId, Double lat, Double lang) {

        this.nama = nama;
        this.deskripsi = deskripsi;
        this.photo = photo;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.lokasi = lokasi;
        this.lat = lat;
        this.lang = lang;


    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lang;
    }

    public void setLng(double lng) {
        this.lang = lng;

    }
}
