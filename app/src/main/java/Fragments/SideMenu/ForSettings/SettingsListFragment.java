package Fragments.SideMenu.ForSettings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serg.fit.R;

import java.util.Arrays;

import Adapters.SettingsAdapter;
import Pojo.SettingsUserItem;

public class SettingsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SettingsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_list,null);
        adapter = new SettingsAdapter();
        recyclerView = (RecyclerView) view.findViewById(R.id.users_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        loadItems();
        return view;
    }

    private void loadItems() {
        adapter.addItems(Arrays.asList(new SettingsUserItem("","Овчинников Сергей",true),
                new SettingsUserItem("","Кузнецова Ольга",false),
                new SettingsUserItem("","Кузнецова Ольга",false),
                new SettingsUserItem("","Кузнецова Ольга",false),
                new SettingsUserItem("","Кузнецова Ольга",false),
                new SettingsUserItem("","Кузнецова Ольга",false),
                new SettingsUserItem("","Кузнецова Ольга",false),
                new SettingsUserItem("","Кузнецова Ольга",false)));
    }
}
