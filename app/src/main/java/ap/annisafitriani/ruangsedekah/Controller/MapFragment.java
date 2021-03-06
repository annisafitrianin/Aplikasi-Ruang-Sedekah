package ap.annisafitriani.ruangsedekah.Controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private Kegiatan mKegiatan;
    private DatabaseReference mDatabase;
    private DatabaseReference refDatabase;
    private Context mContext;

    private GoogleMap mMap;
    MapView mMapView;
    View mView;
    private LinkedList<Marker> markerList;
    private SupportMapFragment supportMapFragment;

    ImageView mylocation;
    ImageView createEvent;
    private static final String TAG = "MapFragment";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean mLocationPermissionsGranted = false;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleApiClient mGoogleApiClient;
    private Marker mMarker;


    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    DataPassListener mCallback;

    private ArrayList<Kegiatan> mListKegiatan = new ArrayList<>();

    public interface DataPassListener{
        public void passData(double lat, double lang, GoogleMap mMap, LinkedList markerList);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mCallback = (DataPassListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement DataPassListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);


        mylocation = mView.findViewById(R.id.current_location);
        createEvent = mView.findViewById(R.id.create_event);

        mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

        initMap();
        getLocationPermission();
        hideSoftKeyboard();

        //untuk fetch semua data dari firebase
        //fetchKegiatanDataFromFirebase(mMap);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Kegiatan");

        getLocationPermission();
        hideSoftKeyboard();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // Member is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            //                  toastMessage("Successfully signed in with: " + user.getEmail());
        } else {
            mylocation.setVisibility(View.GONE);
            createEvent.setVisibility(View.GONE);

        }

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();
                Intent intent = new Intent(getContext(), BuatKegiatanActivity.class);
                intent.putExtra("userId", user.getUid());
                startActivity(intent);
            }
        });

        markerList = new LinkedList<Marker>();

        return mView;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        fetchKegiatanDataFromFirebase(mMap);
        if (mLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.maps);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) mGoogleApiClient.connect();
    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);

        try {
            if (mLocationPermissionsGranted) {
                mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            moveCamera(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM, "my Marker");
                            mCallback.passData(location.getLatitude(), location.getLongitude(), mMap, markerList);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, Kegiatan kegiatan) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));

        if (kegiatan != null) {
            try {
                String snippet = "Nama Kegiatan: " + kegiatan.getNama() + "\n" +
                        "Deskripsi: " + kegiatan.getDeskripsi() + "\n" +
                        "Tanggal: " + kegiatan.getTanggal() + "\n" +
                        "Waktu: " + kegiatan.getWaktu() + "\n" +
                        "Lokasi: " + kegiatan.getLokasi();

                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(kegiatan.getNama())
                        .snippet(snippet);
                mMarker = mMap.addMarker(options);

            } catch (NullPointerException e) {
                Log.e(TAG, "moveCamera: NullPointerException: " + e.getMessage());
            }
        } else {
            mMap.addMarker(new MarkerOptions().position(latLng));

        }
    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");


        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName("", 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
        }

        hideSoftKeyboard();
    }

    private void initMap() {
        mContext = getActivity();

        FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.maps);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.maps, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);

    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


    private void hideSoftKeyboard() {
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }

    }

    private void fetchKegiatanDataFromFirebase(final GoogleMap googleMap) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Kegiatan");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    mKegiatan = ds.getValue(Kegiatan.class);
//                    Query query = FirebaseDatabase.getInstance().getReference("Lokasi")
//                            .orderByChild("kegiatanId").equalTo(mKegiatan.getId());
//                    query.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            mKegiatan.getLokasi().setKegiatanId(dataSnapshot.child("kegiatanId").getValue().toString());
//                            mKegiatan.getLokasi().setLang(Double.parseDouble(dataSnapshot.child("lang").getValue().toString()));
//                            mKegiatan.getLokasi().setLat(Double.parseDouble(dataSnapshot.child("lat").getValue().toString()));
//                            mKegiatan.getLokasi().setLokasiId(dataSnapshot.child("lokasiId").getValue().toString());
//                            mKegiatan.getLokasi().setNamaTempat(dataSnapshot.child("namaTempat").getValue().toString());
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });

                    if (mKegiatan != null) {
                        mListKegiatan.add(mKegiatan);
                    }
                    addMarkerToMap(mListKegiatan,googleMap);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addMarkerToMap(ArrayList<Kegiatan> mListKegiatan, GoogleMap googleMap) {
        for (int i = 0; i < mListKegiatan.size(); i++) {
            final Kegiatan kgtn = mListKegiatan.get(i);
            LatLng position = new LatLng(mListKegiatan.get(i).getLokasi().getLat(), mListKegiatan.get(i).getLokasi().getLang());
            Marker mMark = googleMap.addMarker(new MarkerOptions().position(position)
                    .title(kgtn.getNama())
                    .snippet(kgtn.toString()));

            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                private View view = getLayoutInflater().inflate(R.layout.marker_view, null);

                @Override
                public View getInfoWindow(Marker marker) {
                    final TextView titleUi = ((TextView) view.findViewById(R.id.worldmap_infowindow_username));
                    String title = marker.getTitle();

                    if (title != null) titleUi.setText(title);
                    else titleUi.setText("-");
                    String[] strings = marker.getSnippet().split("[|]");

                    final TextView snippetUi = ((TextView) view.findViewById(R.id.worldmap_infowindow_name));
                    if (strings[0] != null) snippetUi.setText(strings[0]);
                    else snippetUi.setText("-");

                    final TextView decUi = ((TextView) view.findViewById(R.id.worldmap_infowindow_details));
                    if (strings[1] != null) decUi.setText(strings[1]);
                    else snippetUi.setText("-");

                    markerList.add(marker);

                    return view;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    if (marker != null && marker.isInfoWindowShown()) {
                        marker.hideInfoWindow();
                        marker.showInfoWindow();
                    }
                    return null;
                }
            });


        }
    }
}
