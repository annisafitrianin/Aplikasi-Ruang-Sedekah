//package ap.annisafitriani.ruangsedekah.Activity;
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.NotificationCompat;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ap.annisafitriani.ruangsedekah.Fragment.Maps;
//import ap.annisafitriani.ruangsedekah.Fragment.TimelineFragment;
//import ap.annisafitriani.ruangsedekah.R;
//
//
//public class HalamanUtama extends AppCompatActivity implements View.OnClickListener {
//
//    private static final String TAG = "HalamanUtama";
//
//    private Toolbar toolbar;
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
//    private ViewPagerAdapter adapter;
//    private int[] tabIcons = {
//            R.drawable.ic_maps,
//            R.drawable.ic_timeline,
//            R.drawable.ic_notification
//    };
//    private android.app.Notification myNotification;
//    private NotificationManager notificationManager;
//
// //   private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_halaman_utama);
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
//
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//        adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        setupTabIcons();
//        this.notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        setupFirebaseListener();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_item, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.menu_profil) {
//            Intent intent = new Intent(this, HalamanProfil.class);
//            startActivity(intent);
//            finish();
//        } else if (id == R.id.menu_logout) {
//            Toast.makeText(this, "menu_logout clicked", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "onClick: attempting to sign out the user.");
//            FirebaseAuth.getInstance().signOut();
//        } else if (id == R.id.home) {
//            onBackPressed();
//        }
//        return true;
//
//    }
//
//    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//    }
//
//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new Maps(), "MAPS");
//        adapter.addFragment(new TimelineFragment(), "TIMELINE");
//        viewPager.setAdapter(adapter);
//    }
//
//    @Override
//    public void onClick(View view) {
//        Intent intent = new Intent(this, HalamanUtama.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 77, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        this.myNotification = new NotificationCompat.Builder(this)
//                .setContentTitle("Judul Notifikasi")
//                .setContentText("ini isi dari notifikasinya")
//                .setSmallIcon(R.mipmap.ic_notifications_active)
//                .setContentIntent(pendingIntent)
//                .build();
//
//
//        this.notificationManager.notify(12, this.myNotification);
//    }
//
//
//    static class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }
//
//    private void setupFirebaseListener() {
//        Log.d(TAG, "setupFirebaseListener: setting up the auth state listener");
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    Log.d(TAG, "onAuthStateChanged: signed in: " + user.getUid());
//                } else {
//                    Log.d(TAG, "onAuthStateChanged: signed_out");
//                    Toast.makeText(HalamanUtama.this, "Signed out", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(HalamanUtama.this, LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                }
//            }
//        };
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
//        }
//    }
//    private void hideSoftKeyboard(){
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//    }
//}


package ap.annisafitriani.ruangsedekah.Activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import ap.annisafitriani.ruangsedekah.Fragment.Maps;
import ap.annisafitriani.ruangsedekah.Fragment.TimelineFragment;
import ap.annisafitriani.ruangsedekah.R;


public class HalamanUtama extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HalamanUtama";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int[] tabIcons = {
            R.drawable.ic_maps,
            R.drawable.ic_timeline,
            R.drawable.ic_notification
    };
    private android.app.Notification myNotification;
    private NotificationManager notificationManager;

       private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        setupTabIcons();
        this.notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        mAuth = FirebaseAuth.getInstance();




        setupFirebaseListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            getMenuInflater().inflate(R.menu.menu_item, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_profil) {
            Intent intent = new Intent(this, HalamanProfil.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_logout) {
            Toast.makeText(this, "menu_logout clicked", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onClick: attempting to sign out the user.");
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.home) {
            onBackPressed();
        }
        return true;

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Maps(), "MAPS");
        adapter.addFragment(new TimelineFragment(), "TIMELINE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, HalamanUtama.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 77, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        this.myNotification = new NotificationCompat.Builder(this)
                .setContentTitle("Judul Notifikasi")
                .setContentText("ini isi dari notifikasinya")
                .setSmallIcon(R.mipmap.ic_notifications_active)
                .setContentIntent(pendingIntent)
                .build();


        this.notificationManager.notify(12, this.myNotification);
    }


    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setupFirebaseListener() {
        Log.d(TAG, "setupFirebaseListener: setting up the auth state listener");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged: signed in: " + user.getUid());
                    Toast.makeText(HalamanUtama.this, user.getUid().toString() , Toast.LENGTH_LONG).show();
                } else {
//                    Log.d(TAG, "onAuthStateChanged: signed_out");
//                    Toast.makeText(HalamanUtama.this, "Signed out", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(HalamanUtama.this, LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}