package ap.annisafitriani.ruangsedekah.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ap.annisafitriani.ruangsedekah.Adapter.ListTimelineAdapter;
import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment implements Maps.DataPassListener {

    RecyclerView rvCategory;
    ListTimelineAdapter adapter;
    LinkedList<Kegiatan> kegiatanItem;
    DatabaseReference mRef;
    FirebaseDatabase mDatabase;
    SwipeRefreshLayout mySwipeRefreshLayout;

    public double lat;
    public double lang;

    Spinner spinner;
    ArrayAdapter<String>dataAdapter;
  //  List<Date> dates;
    String[] categories = {"Semua", "Lokasi", "Waktu"};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Kegiatan");

        kegiatanItem = new LinkedList<>();

        rvCategory = (RecyclerView) view.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvCategory.setLayoutManager(linearLayoutManager);

        adapter = new ListTimelineAdapter(kegiatanItem);
        rvCategory.setAdapter(adapter);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_activated_1, categories);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        kegiatanItem.clear();
                        updateList();
                        break;

                    case 1:
                        kegiatanItem.clear();
                        mRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                // euclidean distance
                                double distance = Math.sqrt(
                                        Math.pow(dataSnapshot.getValue(Kegiatan.class).lang - lang, 2) +
                                                Math.pow(dataSnapshot.getValue(Kegiatan.class).lat - lat, 2)
                                );

                                if (distance < 0.050) {
                                    kegiatanItem.addFirst(dataSnapshot.getValue(Kegiatan.class));
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                Kegiatan kegiatan = dataSnapshot.getValue(Kegiatan.class);
                                int index = getItemIndex(kegiatan);

                                kegiatanItem.set(index, kegiatan);
                                adapter.notifyItemChanged(index);
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                Kegiatan kegiatan = dataSnapshot.getValue(Kegiatan.class);
                                int index = getItemIndex(kegiatan);

                                kegiatanItem.remove(index);
                                adapter.notifyItemRemoved(index);
                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        break;

                    case 2:
                        kegiatanItem.clear();
                        mRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                Calendar currentDate = Calendar.getInstance();
                                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                                String dateString = dateFormatter.format(currentDate.getTime());

                                try {
                                    Date current = dateFormatter.parse(dateString);
                                    Date eventDate = dateFormatter.parse(dataSnapshot.child("tanggal").getValue().toString());
                                    long diff = eventDate.getTime() - current.getTime();
                                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                                    if (days <= 30) {
                                        kegiatanItem.addFirst(dataSnapshot.getValue(Kegiatan.class));
                                        adapter.notifyDataSetChanged();
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                Kegiatan kegiatan = dataSnapshot.getValue(Kegiatan.class);
                                int index = getItemIndex(kegiatan);

                                kegiatanItem.set(index, kegiatan);
                                adapter.notifyItemChanged(index);
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                Kegiatan kegiatan = dataSnapshot.getValue(Kegiatan.class);
                                int index = getItemIndex(kegiatan);

                                kegiatanItem.remove(index);
                                adapter.notifyItemRemoved(index);
                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        updateList();
        hideSoftKeyboard();
        return view;
    }




    private void updateList() {
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    kegiatanItem.addFirst(dataSnapshot.getValue(Kegiatan.class));
                    adapter.notifyDataSetChanged();
                }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Kegiatan kegiatan = dataSnapshot.getValue(Kegiatan.class);
                int index = getItemIndex(kegiatan);

                kegiatanItem.set(index, kegiatan);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Kegiatan kegiatan = dataSnapshot.getValue(Kegiatan.class);
                int index = getItemIndex(kegiatan);

                kegiatanItem.remove(index);
                adapter.notifyItemRemoved(index);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getItemIndex(Kegiatan kegiatan) {

        int index = -1;
        for (int i = 0; i < kegiatanItem.size(); i++) {
            if (kegiatanItem.get(i).getId().equals(kegiatan.getId())) {
                index = i;
                break;
            }

        }
        return index;
    }
    private void hideSoftKeyboard() {
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void passData(double lat, double lang) {
        Log.d("TL fragment", "" + lat + lang );
        this.lat = lat;
        this.lang = lang;
    }
}