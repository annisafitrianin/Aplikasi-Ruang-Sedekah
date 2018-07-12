package ap.annisafitriani.ruangsedekah.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

/**
 * Created by Hp on 4/18/2018.
 */

public class ListTimelineAdapter extends RecyclerView.Adapter<ListTimelineAdapter.CategoryViewHolder> implements MapFragment.DataPassListener {
    List<Kegiatan> listKegiatan;
    Context context;

    double lat;
    double lang;
    GoogleMap mMap;

    public ListTimelineAdapter(List<Kegiatan> listKegiatan) {
        this.listKegiatan = listKegiatan;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_timeline, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {

        final Kegiatan kegiatan = listKegiatan.get(position);

        holder.tvNama.setText(kegiatan.getNama());
        holder.tvTanggal.setText(kegiatan.getTanggal());
        holder.tvWaktu.setText(kegiatan.getWaktu());
        holder.tvDesc.setText(kegiatan.getDeskripsi());

        holder.tvNama.setText(kegiatan.nama);

        holder.locLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, HalamanUtamaActivity.class);
//
//                context.startActivity(intent);
//                ((Activity)context).finish();

                LatLng location = new LatLng(kegiatan.lat, kegiatan.lang);
                CameraPosition INIT = new CameraPosition.Builder().target(location).zoom(15.5F).bearing(300F) // orientation
                        .tilt(50F) // viewing angle
                        .build();

                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(INIT));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listKegiatan.size();
    }

    @Override
    public void passData(double lat, double lang, GoogleMap mMap) {
        this.lat = lat;
        this.lang = lang;
        this.mMap = mMap;
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