package ap.annisafitriani.ruangsedekah.Model;

/**
 * Created by Hp on 4/18/2018.
 */

public class Kegiatan {

    String nama, deskripsi, photo, tanggal, waktu, id, lokasi;
    Double lang, lat;


    public Kegiatan(String nama, String tanggal, String waktu, String deskripsi, String id, String lokasi) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.id = id;
        this.lokasi = lokasi;
//        this.lat = lat;
//        this.lang = lang;

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

//    public String getLat() {
//        return lat;
//    }
//
//    public void setLat(Double lat) {
//        this.lat =lat;
//    }
//
//    public String getLang() {
//        return lang;
//    }
//
//    public void setLang(String lang) {
//        this.lang = lang;
//    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
