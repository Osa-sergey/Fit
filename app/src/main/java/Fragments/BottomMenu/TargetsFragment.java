package Fragments.BottomMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.serg.fit.R;

import java.util.Arrays;

import Adapters.TargetAdapter;
import Pojo.TargetItem;
import Utils.LinearItemDecoration;

public class TargetsFragment extends Fragment {
    private RecyclerView recyclerView;
    private TargetAdapter adapter;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_targets,null);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_btn);
        fab.setColorFilter(Color.WHITE);
        recyclerView = (RecyclerView) view.findViewById(R.id.targets_recyclerView);
        adapter = new TargetAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new LinearItemDecoration(23));
        loadItems();
        return view;
    }

    private void loadItems() {
       adapter.addItems(Arrays.asList(new TargetItem(false,"Первая программа",4,50,22220,20,10)));
        adapter.addItems(Arrays.asList(new TargetItem(false,"Первая программа",4,50,22220,20,10)));
        adapter.addItems(Arrays.asList(new TargetItem(false,"Первая программа",4,50,22220,20,10)));
        adapter.addItems(Arrays.asList(new TargetItem(false,"Первая программа",4,50,22220,20,10)));
        adapter.addItems(Arrays.asList(new TargetItem(false,"Первая программа",4,50,22220,20,10)));
        adapter.addItems(Arrays.asList(new TargetItem(false,"Первая программа",4,50,22220,20,10)));
        adapter.addItems(Arrays.asList(new TargetItem(false,"Первая программа",4,50,22220,20,10)));
        adapter.addItems(Arrays.asList(new TargetItem(false,"Первая программа",4,50,22220,20,10)));
        adapter.addItems(Arrays.asList(new TargetItem(false,"Первая программа",4,50,22220,20,10)));
        adapter.addItems(Arrays.asList(new TargetItem(false,"Первая программа",4,50,22220,20,10)));

    }
}


