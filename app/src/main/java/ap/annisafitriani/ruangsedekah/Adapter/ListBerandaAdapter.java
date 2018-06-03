package ap.annisafitriani.ruangsedekah.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import ap.annisafitriani.ruangsedekah.Activity.CreateActivity;
import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

/**
 * Created by Hp on 4/18/2018.
 */

public class ListBerandaAdapter extends RecyclerView.Adapter<ListBerandaAdapter.CategoryViewHolder> {
    List<Kegiatan> listKegiatan;
    private Activity activity;
    Context context;
    DatabaseReference mRef;
    EditText etNama;
    EditText etDateResult;
    EditText etWaktu;
    EditText etLokasi;
    EditText etDesc;



    public ListBerandaAdapter(List<Kegiatan> listKegiatan, DatabaseReference mRef, EditText etNama,
                              EditText etDateResult,EditText etWaktu, EditText etLokasi,EditText etDesc   ) {
        this.listKegiatan = listKegiatan;
        this.mRef = mRef;
        this.etNama = etNama;
        this.etDateResult = etDateResult;
        this.etWaktu = etWaktu;
        this.etLokasi = etLokasi;
        this.etDesc = etDesc;

    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_beranda, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {

        final Kegiatan kegiatan = listKegiatan.get(position);

        holder.tvNama.setText(kegiatan.nama);
        holder.tvTanggal.setText(kegiatan.tanggal);
        holder.tvWaktu.setText(kegiatan.waktu);
        holder.tvDesc.setText(kegiatan.deskripsi);

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.child(kegiatan.getId()).removeValue();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.startActivity(new Intent(activity, CreateActivity.class));
                etNama.setText(kegiatan.getNama());
                etDateResult.setText(kegiatan.getTanggal());
                etWaktu.setText(kegiatan.getWaktu());
                etLokasi.setText(kegiatan.getLokasi());
                etDesc.setText(kegiatan.getDeskripsi());
                CreateActivity.nama = kegiatan.getId();
                listKegiatan.remove(holder.getAdapterPosition());
                notifyItemChanged(position);

            }
        });

//        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
//                menu.add(holder.getAdapterPosition(), 0, 0, "Hapus");
//                menu.add(holder.getAdapterPosition(), 1, 0, "Edit");
//            }
//        });





//        Glide.with(context)
//                .load(kegiatan.getLokasi())
//                .into(holder.locLokasi);

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
        Button btnHapus;
        Button btnEdit;



        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.tv_item_nama);
            tvTanggal = (TextView) itemView.findViewById(R.id.tv_item_tanggal);
            tvWaktu = (TextView) itemView.findViewById(R.id.tv_item_waktu);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_item_desc);
            locLokasi = (ImageView) itemView.findViewById(R.id.img_loc);
            btnHapus = (Button) itemView.findViewById(R.id.btn_hapus);
            btnEdit = (Button) itemView.findViewById(R.id.btn_edit);






        }
    }
}