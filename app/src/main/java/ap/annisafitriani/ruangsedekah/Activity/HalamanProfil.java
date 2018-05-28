package ap.annisafitriani.ruangsedekah.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ap.annisafitriani.ruangsedekah.Adapter.ListTimelineAdapter;
import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

public class HalamanProfil extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvNama;
    private TextView tvEmail;


    RecyclerView rvCategory;
    ListTimelineAdapter adapter;
    List<Kegiatan> kegiatanItem;
    DatabaseReference mRef;
    FirebaseDatabase mDatabase;
    FirebaseAuth firebaseAuth;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_profil);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        mDatabase = FirebaseDatabase.getInstance();
//        mRef = mDatabase.getReference("User").child(user.getUid().toString());

        kegiatanItem = new ArrayList<>();

        rvCategory = (RecyclerView) findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        tvNama = (TextView) findViewById(R.id.tv_name);
        tvEmail = (TextView) findViewById(R.id.tv_email);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvCategory.setLayoutManager(linearLayoutManager);

        adapter = new ListTimelineAdapter(kegiatanItem);
        rvCategory.setAdapter(adapter);

        updateList();

        hideSoftKeyboard();


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String username = dataSnapshot.child("username").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();

                    tvNama.setText(username);
                    tvEmail.setText(email);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                removeKegiatan(item.getGroupId());
                break;
            case 1:
                editKegiatan(item.getGroupId());
                break;
        }
        return super.onContextItemSelected(item);
    }



    private void updateList() {
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



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



    @Override
    public void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, HalamanUtama.class));
        }
//        auth.addAuthStateListener(mAuthListener);
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
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void removeKegiatan(int position){
        mRef.child(kegiatanItem.get(position).id).removeValue();
    }
    private void editKegiatan(int position){
        Kegiatan kegiatan = kegiatanItem.get(position);
        Intent intent = new Intent(HalamanProfil.this, CreateActivity.class);
        startActivity(intent);
    }
}

