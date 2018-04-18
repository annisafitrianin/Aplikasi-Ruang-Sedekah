package ap.annisafitriani.ruangsedekah;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Notification extends Fragment {


    private RecyclerView rvCategory;
    private ArrayList<Kegiatan> list;

    public Notification() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        list.addAll(KegiatanData.getListData());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        rvCategory = (RecyclerView) view.findViewById(R.id.rv_category);
        ListNotifAdapter listNotifAdapter= new ListNotifAdapter(getContext());
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setAdapter(listNotifAdapter);
        return view;
    }
}