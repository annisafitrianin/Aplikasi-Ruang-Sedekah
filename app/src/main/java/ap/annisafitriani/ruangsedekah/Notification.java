package ap.annisafitriani.ruangsedekah;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Notification extends Fragment{


    private RecyclerView rvCategory;
    private ArrayList<Marker> list;
    public Notification() {
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);


        //di fragment notification belum ada recyclerview

        //TODO: buat recyclerview di xml nya dulu baru bikin codingan javanya
//        rvCategory = (RecyclerView) view.findViewById(R.id.rv_category);
//        ListMarkerAdapter markerAdapter = new ListMarkerAdapter(getContext(),list);
//        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rvCategory.setAdapter(markerAdapter);

        return view;

    }

}