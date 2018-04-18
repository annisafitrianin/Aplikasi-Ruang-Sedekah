package ap.annisafitriani.ruangsedekah;

import java.util.ArrayList;

/**
 * Created by Hp on 4/18/2018.
 */

public class KegiatanData {

    public static String[][] dataString = new String[][]{
            {"Sedekah Berbagi", "untuk semua orang"}
    };
    public static int[][] dataInt = new int[][]{
            {}
    };

    //lupa logicnya haha
//    public static double[] dataDouble = new double[]{
 //           {}
 //   };

    public static ArrayList<Kegiatan> getListData() {
        Kegiatan kegiatan = null;
        ArrayList<Kegiatan> list = new ArrayList<>();
        for (int i = 0; i < dataString.length; i++) {
            kegiatan = new Kegiatan();
            kegiatan.setName(dataString[i][0]);
            kegiatan.setDeskripsi(dataString[i][1]);

            list.add(kegiatan);
        }
        for (int i = 0; i <dataInt.length; i++){
            kegiatan = new Kegiatan();
            kegiatan.setTanggal(dataInt[i][0]);
            kegiatan.setWaktu(dataInt[i][1]);
        }
 //       for (int i = 0; i <dataDouble.length; i++){
 //           kegiatan = new Kegiatan();
 //           kegiatan.setLokasi(dataDouble[i][0]);
 //       }

        return list;
    }

}
