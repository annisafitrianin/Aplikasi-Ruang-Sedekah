package ap.annisafitriani.ruangsedekah.Model;

/**
 * Created by Hp on 4/18/2018.
 */

public class Kegiatan {

    private String name, deskripsi, photo;
    private int tanggal, waktu;
    private double lokasi;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
}
