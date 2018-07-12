package ap.annisafitriani.ruangsedekah.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.LinkedList;
import java.util.List;

import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

/**
 * Created by Hp on 4/18/2018.
 */

public class ListTimelineAdapter extends RecyclerView.Adapter<ListTimelineAdapter.CategoryViewHolder> implements MapFragment.DataPassListener {
    List<Kegiatan> listKegiatan;
    Context context;
    ViewGroup parent;
    private LinkedList<Marker> markerList;

    double lat;
    double lang;
    GoogleMap mMap;

    public ListTimelineAdapter(List<Kegiatan> listKegiatan) {
        this.listKegiatan = listKegiatan;
    }

    ChangeViewPagerItemListener mCallback;

    public interface ChangeViewPagerItemListener{
        public void item(int x);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_timeline, parent, false);
        this.parent = parent;
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {

        final Kegiatan kegiatan = listKegiatan.get(position);

        holder.tvNama.setText(kegiatan.getNama());
        holder.tvTanggal.setText(kegiatan.getTanggal());
        holder.tvWaktu.setText(kegiatan.getWaktu());
        holder.tvDesc.setText(kegiatan.getDeskripsi());

        holder.tvNama.setText(kegiatan.getNama());

        holder.locLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < markerList.size(); i++)
                {
                    Log.d("LISTTIMELINEADAPTER", markerList.get(i).getTitle());
                    if (kegiatan.getNama().equals(markerList.get(i).getTitle()))
                    {
                        markerList.get(i).showInfoWindow();
                        break;
                    }
                }

                LatLng location = new LatLng(kegiatan.getLokasi().getLat(), kegiatan.getLokasi().getLang());
                CameraPosition INIT = new CameraPosition.Builder().target(location).zoom(15.5F).bearing(300F) // orientation
                        .build();

                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(INIT));
                mCallback = (ChangeViewPagerItemListener) parent.getContext();
                mCallback.item(0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listKegiatan.size();
    }

    @Override
    public void passData(double lat, double lang, GoogleMap mMap, LinkedList markerList) {
        this.lat = lat;
        this.lang = lang;
        this.mMap = mMap;
        this.markerList = markerList;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama;
        TextView tvTanggal;
        TextView tvWaktu;
        ImageView locLokasi;
        TextView tvDesc;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.tv_item_nama);
            tvTanggal = (TextView) itemView.findViewById(R.id.tv_item_tanggal);
            tvWaktu = (TextView) itemView.findViewById(R.id.tv_item_waktu);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_item_desc);
            locLokasi = (ImageView) itemView.findViewById(R.id.img_loc);

        }
    }


}