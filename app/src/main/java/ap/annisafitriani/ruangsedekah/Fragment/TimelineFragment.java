package ap.annisafitriani.ruangsedekah.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.Model.KegiatanData;
import ap.annisafitriani.ruangsedekah.Adapter.ListTimelineAdapter;
import ap.annisafitriani.ruangsedekah.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {

    private RecyclerView rvCategory;
    private ArrayList<Kegiatan> list;
    FirebaseDatabase FDB;
    DatabaseReference DBR;




    public TimelineFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        rvCategory = (RecyclerView) view.findViewById(R.id.rv_category);

        list = new ArrayList<>();
        list.addAll(KegiatanData.getListData());




         FDB = FirebaseDatabase.getInstance();
        GetDataFirebase();

        return view;
    }
    void GetDataFirebase(){

        DBR = FDB.getReference("kegiatan");

        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Kegiatan data = dataSnapshot.getValue(Kegiatan.class);
                //Now add to ArrayList
                list.add(data);
                //Now Add List into Adapter/Recyclerview
                ListTimelineAdapter listTimelineAdapter = new ListTimelineAdapter(getContext());
                listTimelineAdapter.setListKegiatan(list);
                rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvCategory.setAdapter(listTimelineAdapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}