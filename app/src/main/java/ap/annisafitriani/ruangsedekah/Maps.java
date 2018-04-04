package ap.annisafitriani.ruangsedekah;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Maps extends Fragment{

    public Maps() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //TODO: masukin map kedalam xml dan buat basic logic untuk dapetin marker.pake tutorial awal
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        return view;
    }

}