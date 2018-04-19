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


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {

    private RecyclerView rvCategory;
    private ArrayList<Kegiatan> list;

    public TimelineFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        rvCategory = (RecyclerView) view.findViewById(R.id.rv_category);

        list = new ArrayList<>();
        list.addAll(KegiatanData.getListData());

        ListTimelineAdapter listTimelineAdapter = new ListTimelineAdapter(getContext());
        listTimelineAdapter.setListKegiatan(list);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setAdapter(listTimelineAdapter);

        return view;
    }
}