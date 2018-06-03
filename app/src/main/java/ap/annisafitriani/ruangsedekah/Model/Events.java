package ap.annisafitriani.ruangsedekah.Model;

/**
 * Created by Hp on 6/3/2018.
 */

public class Events {

        public String nama, deskripsi, tanggal, waktu, eventId, lokasi;
        public Double lang, lat;


        public Events (String nama, String tanggal, String waktu, String deskripsi, String eventId, String lokasi, Double lat, Double lang) {
            this.nama = nama;
            this.deskripsi = deskripsi;
            this.tanggal = tanggal;
            this.waktu = waktu;
            this.eventId = eventId;
            this.lokasi = lokasi;
            this.lat = lat;
            this.lang = lang;

        }

        public Events() {

        }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getEventId() {
        return eventId;
    }

    public String getLokasi() {
        return lokasi;
    }

    public Double getLang() {
        return lang;
    }

    public Double getLat() {
        return lat;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public void setLang(Double lang) {
        this.lang = lang;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}

