package ap.annisafitriani.ruangsedekah.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import ap.annisafitriani.ruangsedekah.Fragment.Maps;
import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

/**
 * Created by Hp on 4/18/2018.
 */

public class ListTimelineAdapter extends RecyclerView.Adapter<ListTimelineAdapter.CategoryViewHolder> {
    List<Kegiatan> listKegiatan;
    Context context;


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

        Kegiatan kegiatan = listKegiatan.get(position);

        holder.tvNama.setText(kegiatan.getNama());
        holder.tvTanggal.setText(kegiatan.getTanggal());
        holder.tvWaktu.setText(kegiatan.getWaktu());
        holder.tvDesc.setText(kegiatan.getDeskripsi());

        holder.tvNama.setText(kegiatan.nama);


        Glide.with(context)
                .load(kegiatan.getLokasi())
                .into(holder.locLokasi);

        holder.locLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Maps.class);

                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listKegiatan.size();
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