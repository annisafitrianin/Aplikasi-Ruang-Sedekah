package ap.annisafitriani.ruangsedekah.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ap.annisafitriani.ruangsedekah.Adapter.ListBerandaAdapter;
import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

public class HalamanProfil extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView tvNama;
    private TextView tvEmail;
    private String userId;

    RecyclerView rvCategory;
    ListBerandaAdapter adapter;
    List<Kegiatan> kegiatanItem;
    DatabaseReference mRef;
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    public SwipeRefreshLayout myswiperefreshlayout;


    private static final String TAG = "HalamanProfil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_profil);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Kegiatan");
        FirebaseUser user = mAuth.getCurrentUser();
        userId = mAuth.getCurrentUser().getUid();

        kegiatanItem = new ArrayList<>();

        rvCategory = (RecyclerView) findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvCategory.setLayoutManager(linearLayoutManager);

        adapter = new ListBerandaAdapter(kegiatanItem);
        rvCategory.setAdapter(adapter);

        updateList();
        hideSoftKeyboard();

        tvNama = (TextView) findViewById(R.id.tv_name);
        tvEmail = (TextView) findViewById(R.id.tv_email);

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

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
        };

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
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

        myswiperefreshlayout = findViewById(R.id.swiperefresh);
        myswiperefreshlayout.setOnRefreshListener(this);




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

        myswiperefreshlayout.setRefreshing(false);
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
        mRef.child(kegiatanItem.get(position).getId()).removeValue();
    }

    private void editKegiatan(int position) {
        Kegiatan kegiatan = kegiatanItem.get(position);
        Intent intent = new Intent(HalamanProfil.this, CreateActivity.class);
        startActivity(intent);
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


    @Override
    public void onRefresh() {
        updateList();
    }
}

