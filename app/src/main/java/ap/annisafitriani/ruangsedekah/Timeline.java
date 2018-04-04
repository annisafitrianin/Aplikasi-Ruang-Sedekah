package ap.annisafitriani.ruangsedekah;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Timeline extends Fragment{

    public Timeline() {
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
        /*TODO: buat item-row dan data model(pojo) dulu. kalau bisa langsung implementasikan.
           sumbernya dicodingg
        */
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        return view;
    }

}