package ap.annisafitriani.ruangsedekah.Model;

import java.io.Serializable;

/**
 * Created by Hp on 4/18/2018.
 */

public class Kegiatan implements Serializable {

    private String nama, deskripsi, tanggal, waktu, id, userId;
    private Lokasi lokasi;


    public Kegiatan(String nama, String tanggal, String waktu, String deskripsi, String id, Lokasi lokasi, String userId) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.id = id;
        this.lokasi = lokasi;
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

    public Lokasi getLokasi() {
        return lokasi;
    }

    public void setLokasi(Lokasi lokasi) {
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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Waktu: " + waktu + "\n" +
                "Tanggal : " + tanggal + "\n" +
                "Lokasi : " + lokasi.getNamaTempat() + "|" + deskripsi;
    }
}
