package ap.annisafitriani.ruangsedekah.Model;

/**
 * Created by Hp on 4/18/2018.
 */

public class Kegiatan {

    private String nama, deskripsi, photo, id;
    private int tanggal, waktu;
    private double lokasi;

    public Kegiatan(String nama, String deskripsi, String id, int tanggal, int waktu, double lokasi){
        this.nama = nama;
        this.id = id;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.lokasi = lokasi;
    }
    public Kegiatan(){

    }

    public String getName() {
        return nama;
    }
    public void setName(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getTanggal() {
        return tanggal;
    }
    public void setTanggal(int tanggal) {
        this.tanggal = tanggal;
    }

    public int getWaktu() {
        return waktu;
    }
    public void setWaktu(int waktu) {

        this.waktu = waktu;
    }

    public double getLokasi() {
        return lokasi;
    }
    public void setLokasi(int lokasi) {
        this.lokasi = lokasi;
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

    @Override
    public String toString() {
        return "Detail{" +
                "nama kegiatan='" + nama + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", tanggal='" + tanggal + '\'' +
                ", waktu='" + waktu + '\'' +
                '}';
    }
}
