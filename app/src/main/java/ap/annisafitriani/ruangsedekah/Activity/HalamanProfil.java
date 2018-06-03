package ap.annisafitriani.ruangsedekah.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ap.annisafitriani.ruangsedekah.Adapter.ListBerandaAdapter;
import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

public class HalamanProfil extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private TextView tvEmail;
    private TextView tvNama;
    public String userId;
    private Button btnHapus;
    private Button btnEdit;

    RecyclerView rvCategory;
    ListBerandaAdapter adapter;
    List<Kegiatan> kegiatanItem;

    DatabaseReference mRef;
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;


    public SwipeRefreshLayout myswiperefreshlayout;


    private static final String TAG = "HalamanProfil";
    private EditText nama;
    private EditText tanggal;
    private EditText waktu;
    private EditText lokasi;
    private EditText deskripsi;
    private SwipeRefreshLayout mySwipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_profil);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Kegiatan");

        kegiatanItem = new ArrayList<>();
        rvCategory = (RecyclerView) findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCategory.setLayoutManager(linearLayoutManager);

        mySwipeRefreshLayout = (SwipeRefreshLayout) mySwipeRefreshLayout.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "on refresh", Toast.LENGTH_SHORT).show();
            }
        });
        mySwipeRefreshLayout.setRefreshing(false);


        adapter = new ListBerandaAdapter(kegiatanItem, mRef, nama, tanggal, waktu, lokasi, deskripsi);
        rvCategory.setAdapter(adapter);

        tvEmail = (TextView) findViewById(R.id.tv_email);
        
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userId = mAuth.getCurrentUser().getUid();


//        userId = user.getUid();
//        user = FirebaseAuth.getInstance().getCurrentUser();

        profilUser();
        updateList();
        hideSoftKeyboard();



//        mRef = FirebaseDatabase.getInstance().getReference();
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String username = dataSnapshot.child("Users").child(userId).child("username").getValue(String.class);
//                tvNama.setText(username);
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
            }
        };}


    private void profilUser(){

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            if (user.getEmail() != null){
                tvEmail.setText(user.getEmail());
            }
<<<<<<< HEAD
        });

        myswiperefreshlayout = findViewById(R.id.swiperefresh);
        myswiperefreshlayout.setOnRefreshListener(this);




=======
        }
>>>>>>> ee8467bce163e0898f10a49ea6d6300543990f0f
    }


    private void updateList() {
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                kegiatanItem.add(dataSnapshot.getValue(Kegiatan.class));
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

//                Kegiatan kegiatan = dataSnapshot.getValue(Kegiatan.class);
//                int index = getItemIndex(kegiatan);
//
//                kegiatanItem.remove(index);
//                adapter.notifyItemRemoved(index);


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myswiperefreshlayout.setRefreshing(false);
    }

    private int getItemIndex(Kegiatan kegiatan) {

        int index = -1;
        for (int i = 0; i < kegiatanItem.size(); i++) {
            if (kegiatanItem.get(i).id.equals(kegiatan.id)) {
                index = i;
                break;
            }

        }
        return index;
    }

    private void hideSoftKeyboard() {
        HalamanProfil.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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


    private void removeKegiatan(int position) {
        mRef.child(kegiatanItem.get(position).id).removeValue();
    }

    private void editKegiatan(int position) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


<<<<<<< HEAD
    @Override
    public void onRefresh() {
        updateList();
    }
=======

>>>>>>> ee8467bce163e0898f10a49ea6d6300543990f0f
}

