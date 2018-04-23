package ap.annisafitriani.ruangsedekah.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ap.annisafitriani.ruangsedekah.R;

public class Maps extends Fragment implements OnMapReadyCallback {

        private GoogleMap mMap;
        MapView mMapView;
        View mView;

        public Maps(){

        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
                mView = inflater.inflate(R.layout.fragment_maps, container, false);
                return mView;
        }
        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);
                mMapView = (MapView) mView.findViewById(R.id.maps);
                if (mMapView != null){
                        mMapView.onCreate(null);
                        mMapView.onResume();
                        mMapView.getMapAsync(this);
                }
        }

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
                MapsInitializer.initialize(getContext());

                mMap = googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                CameraPosition Lib = CameraPosition.builder().target(sydney).zoom(16).bearing(0).tilt(45).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Lib));


                LatLng stasiun = new LatLng(-6.198517, 106.841363);
                mMap.addMarker(new MarkerOptions().position(stasiun).title("Stasiun Tamvan"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(stasiun));
        }
}
