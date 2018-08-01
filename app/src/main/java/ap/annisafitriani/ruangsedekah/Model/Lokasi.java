package ap.annisafitriani.ruangsedekah.Model;

import java.io.Serializable;

/**
 * Created by Hp on 7/12/2018.
 */

public class Lokasi implements Serializable {

    private String namaTempat;
    private String lokasiId;
    private Double lat, lang;

    public Lokasi(String namaTempat, String lokasiId, Double lat, Double lang) {
        this.namaTempat = namaTempat;
        this.lokasiId = lokasiId;
        this.lat = lat;
        this.lang = lang;
    }

    public Lokasi() {
    }

    public String getNamaTempat() {
        return namaTempat;
    }

    public void setNamaTempat(String namaTempat) {
        this.namaTempat = namaTempat;
    }

    public String getLokasiId() {
        return lokasiId;
    }

    public void setLokasiId(String lokasiId) {
        this.lokasiId = lokasiId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLang() {
        return lang;
    }

    public void setLang(Double lang) {
        this.lang = lang;
    }
}
