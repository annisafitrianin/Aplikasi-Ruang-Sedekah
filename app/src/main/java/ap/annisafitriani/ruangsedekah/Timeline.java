package ap.annisafitriani.ruangsedekah;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Timeline extends AppCompatActivity {
    private RecyclerView rvCategory;
    private ArrayList<Marker>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_timeline);
        rvCategory = (RecyclerView)findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        list = new ArrayList<>();
        list.addAll(MarkerModel.getListData());

        showRecyclerList();
    }

    private void showRecyclerList(){
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        ListMarkAdapter listMarkAdapter= new ListMarkAdapter(this);
        listMarkAdapter.setListPresident(list);
        rvCategory.setAdapter(listMarkAdapter);
    }
}