package ap.annisafitriani.ruangsedekah.Activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import ap.annisafitriani.ruangsedekah.Fragment.Maps;
import ap.annisafitriani.ruangsedekah.Fragment.Notification;
import ap.annisafitriani.ruangsedekah.R;
import ap.annisafitriani.ruangsedekah.Fragment.TimelineFragment;


public class HalamanUtama extends AppCompatActivity implements View.OnClickListener{


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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_profil) {
            Toast.makeText(this, "menu_profil clicked", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.menu_logout) {
            Toast.makeText(this, "menu_profil clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Maps(), "MAPS");
        adapter.addFragment(new TimelineFragment(), "TIMELINE");
        adapter.addFragment(new Notification(), "NOTIFICATION");
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
                //.setAutoCancel(true)
                .build();

        //if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        //    NotificationChannel channel = new NotificationChannel(
        //            "Ch01","Channel 01", NotificationManager.IMPORTANCE_HIGH);

        //this.notificationManager.createNotificationChannel(channel);;

        this.notificationManager.notify(12, this.myNotification);
        //this.notificationManager.cancel(12);
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

}