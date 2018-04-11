package ap.annisafitriani.ruangsedekah;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hp on 4/9/2018.
 */

public class ListMarkerAdapter extends RecyclerView.Adapter<ListMarkerAdapter.CategoryViewHolder>{
    private Context context;

    public ArrayList<Marker> getListPresident() {
        return listMarker;
    }
    public void setListPresident(ArrayList<Marker> listPresident) {
        this.listMarker = listPresident;
    }

    private ArrayList<Marker>listMarker;

    public ListMarkerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_marker, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

        holder.tvName.setText(getListPresident().get(position).getName());
        holder.tvRemarks.setText(getListPresident().get(position).getRemarks());

        Glide.with(context)
                .load(getListPresident().get(position).getPhoto())
                .override(55, 55)
                .crossFade()
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return getListPresident().size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvRemarks;
        ImageView imgPhoto;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tv_item_name);
            tvRemarks = (TextView)itemView.findViewById(R.id.tv_item_remarks);
            imgPhoto = (ImageView)itemView.findViewById(R.id.img_item_photo);
        }
    }
}