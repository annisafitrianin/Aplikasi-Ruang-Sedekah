package ap.annisafitriani.ruangsedekah.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.LinkedList;

import ap.annisafitriani.ruangsedekah.Model.Kegiatan;
import ap.annisafitriani.ruangsedekah.R;

/**
 * Created by Hp on 4/18/2018.
 */

public class ListBerandaAdapter extends RecyclerView.Adapter<ListBerandaAdapter.CategoryViewHolder> {
    LinkedList<Kegiatan> listKegiatan;
    private Activity activity;
    Context context;
    DatabaseReference mRef;
    EditText etNama;
    EditText etDateResult;
    EditText etWaktu;
    EditText etLokasi;
    EditText etDesc;


    public ListBerandaAdapter(LinkedList<Kegiatan> listKegiatan, DatabaseReference mRef, EditText etNama,
                              EditText etDateResult, EditText etWaktu, EditText etLokasi, EditText etDesc) {
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
        context = parent.getContext();
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {

        final Kegiatan kegiatan = listKegiatan.get(position);

        holder.tvNama.setText(kegiatan.getNama());
        holder.tvTanggal.setText(kegiatan.getTanggal());
        holder.tvWaktu.setText(kegiatan.getWaktu());
        holder.tvDesc.setText(kegiatan.getDeskripsi());

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("delete data");
                alert.setMessage("Apakah anda yakin akan menghapus informasi?");
                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mRef.child(kegiatan.getId()).removeValue();
                        Toast.makeText(context, "Informasi Kegiatan Dihapus", Toast.LENGTH_SHORT).show();

                    }
                });
                alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.create().show();
            }


        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BuatKegiatanActivity.class);
                intent.putExtra("kegiatan", kegiatan);
                context.startActivity(intent);
                ((Activity) context).finish();
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