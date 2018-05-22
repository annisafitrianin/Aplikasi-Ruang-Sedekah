package ap.annisafitriani.ruangsedekah.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ap.annisafitriani.ruangsedekah.Model.User;
import ap.annisafitriani.ruangsedekah.R;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";

    private EditText inputEmail,
            inputPassword,
            inputNama, inputNoHp;
    private Button btnRegistrasi;
    private ProgressBar progressBar;
    private String userID;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnRegistrasi = (Button) findViewById(R.id.email_sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email_register);
        inputPassword = (EditText) findViewById(R.id.password_register);
        inputNama = (EditText) findViewById(R.id.nama);
        inputNoHp = (EditText) findViewById(R.id.no_hp);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Added information to database: \n" +
                        dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }


    private void registerUser() {

        /*
        baris kode ini berlaku kalau user udah login

        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        */

        //getting email and password from edit texts
        final String email = inputEmail.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();
        final String nama = inputNama.getText().toString().trim();
        final String no_hp = inputNoHp.getText().toString().trim();


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nama)) {
            Toast.makeText(getApplicationContext(), "Enter nama", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(no_hp)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String userId = myRef.push().getKey();
        User user = new User(email, password, nama, no_hp, userId);
        myRef.child(userId).setValue(user);

        progressBar.setVisibility(View.VISIBLE);


        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (!email.equals("") && !password.equals("") && !nama.equals("") && !no_hp.equals("")) {
                            Toast.makeText(RegistrationActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            inputEmail.setText("");
                            inputPassword.setText("");
                            inputNama.setText("");
                            inputNoHp.setText("");

                            if (task.isSuccessful()) {
                                User user = new User(email, password, nama, no_hp, userId);
                                myRef.child("Users").child(myRef.push().getKey()).setValue(user);
                            }
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            //display some message here
                            Toast.makeText(RegistrationActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}