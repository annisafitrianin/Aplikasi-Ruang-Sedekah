package ap.annisafitriani.ruangsedekah;


import android.os.Bundle;
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
    private ArrayList<Marker> list;

    public TimelineFragment() {
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
        View view = inflater.inflate(R.layout.fragment_timeline2, container, false);

        rvCategory = (RecyclerView) view.findViewById(R.id.rv_category);
        ListMarkerAdapter markerAdapter = new ListMarkerAdapter(getContext(),list);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setAdapter(markerAdapter);

        return view;

    }

}
