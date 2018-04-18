package ap.annisafitriani.ruangsedekah;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 4/9/2018.
 */

public class ListMarkerAdapter extends RecyclerView.Adapter<ListMarkerAdapter.CategoryViewHolder> {
    private Context context;
    //private List<Marker> mMarker;

    private ArrayList<Marker> getListPresident() {
        return listMarker;
    }

    public void setListPresident(ArrayList<Marker> listPresident) {
        this.listMarker = listPresident;
    }

    private ArrayList<Marker> listMarker;

    ListMarkerAdapter(Context context, ArrayList<Marker> mMarker)
    {
        this.context = context;
        listMarker = mMarker;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_row_marker, parent, false);
        return new CategoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

        holder.tvName.setText(getListPresident().get(position).getName());
        holder.tvRemarks.setText(getListPresident().get(position).getRemarks());

        Glide.with(context)
                .load(getListPresident().get(position).getPhoto())
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return getListPresident().size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvRemarks;
        ImageView imgPhoto;

        CategoryViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
            tvRemarks = (TextView) itemView.findViewById(R.id.tv_item_remarks);
            imgPhoto = (ImageView) itemView.findViewById(R.id.img_item_photo);
        }
    }
}