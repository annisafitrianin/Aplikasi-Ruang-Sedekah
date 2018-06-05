package ap.annisafitriani.ruangsedekah.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ap.annisafitriani.ruangsedekah.R;

/**
 * Created by Hp on 3/20/2018.
 */

public class HalamanAwal extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegis;
    private TextView textSkipToHome;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_awal);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
        btnRegis = findViewById(R.id.btn_regis);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        textSkipToHome = findViewById(R.id.halaman_utama);
        textSkipToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), HalamanUtama.class);
                startActivity(myIntent);
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseUser != null)
        {
            Intent myIntent = new Intent(getApplicationContext(), HalamanUtama.class);
            startActivity(myIntent);
            finish();
        }
    }
}