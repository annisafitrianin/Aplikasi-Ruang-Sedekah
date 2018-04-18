package ap.annisafitriani.ruangsedekah;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Hp on 4/18/2018.
 */

public class ListTimelineAdapter extends RecyclerView.Adapter<ListTimelineAdapter.CategoryViewHolder>{
    private Context context;

    public ArrayList<Kegiatan> getListKegiatan() {
        return listKegiatan;
    }
    public void setListKegiatan(ArrayList<Kegiatan> listKegiatan) {
        this.listKegiatan = listKegiatan;
    }

    private ArrayList<Kegiatan>listKegiatan;

    public ListTimelineAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_timeline, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {


        holder.tvName.setText(getListKegiatan().get(position).getName());
        holder.tvTanggal.setText(getListKegiatan().get(position).getTanggal());
        holder.tvWaktu.setText(getListKegiatan().get(position).getWaktu());
        holder.tvDesc.setText(getListKegiatan().get(position).getDeskripsi());

        Glide.with(context)
                .load(getListKegiatan().get(position).getLokasi())


                .into(holder.locLokasi);
        Glide.with(context)
                .load(getListKegiatan().get(position).getPhoto())


                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return getListKegiatan().size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvTanggal;
        TextView tvWaktu;
        ImageView locLokasi;
        TextView tvDesc;
        ImageView imgPhoto;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tv_item_name);
            tvTanggal = (TextView)itemView.findViewById(R.id.tv_item_tanggal);
            tvWaktu = (TextView)itemView.findViewById(R.id.tv_item_waktu);
            tvDesc = (TextView)itemView.findViewById(R.id.tv_item_desc);
            locLokasi = (ImageView)itemView.findViewById(R.id.img_loc);
            imgPhoto = (ImageView)itemView.findViewById(R.id.img_item_photo);
        }
    }
}