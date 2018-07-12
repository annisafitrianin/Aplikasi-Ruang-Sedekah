package ap.annisafitriani.ruangsedekah.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

public class HalamanProfilActivity extends AppCompatActivity {

    private TextView tvEmail;
    private TextView tvNama;
    public String userId;
    private Button btnHapus;
    private Button btnEdit;

    RecyclerView rvCategory;
    ListBerandaAdapter adapter;
    LinkedList<Kegiatan> kegiatanItem;

    DatabaseReference mRef;
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;

    private static final String TAG = "HalamanProfilActivity";
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

        kegiatanItem = new LinkedList<>();
        rvCategory = (RecyclerView) findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCategory.setLayoutManager(linearLayoutManager);

        adapter = new ListBerandaAdapter(kegiatanItem, mRef, nama, tanggal, waktu, lokasi, deskripsi);
        rvCategory.setAdapter(adapter);

        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvNama = (TextView) findViewById(R.id.tv_name);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userId = mAuth.getCurrentUser().getUid();


        profilUser();
        updateList();
        hideSoftKeyboard();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_i" + user.getUid());
                    //toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //toastMessage("Successfully signed out.");
                }
            }
        };

    }


    private void profilUser(){
        DatabaseReference userRef = mDatabase.getReference("Users");
        Query query = userRef.child(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvEmail.setText(dataSnapshot.child("email").getValue().toString());
                tvNama.setText(dataSnapshot.child("username").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void updateList() {
        final Query query = mRef.orderByChild("userId").equalTo(userId);
        if(query != null) {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        kegiatanItem.addFirst(data.getValue(Kegiatan.class));
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

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
                adapter.notifyDataSetChanged();
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
            if (kegiatanItem.get(i).id.equals(kegiatan.id)) {
                index = i;
                break;
            }

        }
        return index;
    }

    private void hideSoftKeyboard() {
        HalamanProfilActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
}