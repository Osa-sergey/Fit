package Fragments.SideMenu;

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

import Adapters.HelpAdapter;
import Pojo.HelpItem;
import Utils.LinearItemDecoration;

public class HelpFragment extends Fragment {

    private RecyclerView recyclerView;
    private HelpAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.help_recyclerView);
        adapter = new HelpAdapter();

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new LinearItemDecoration(16));
        loadItems();
        return view;
    }

    private void loadItems() {
        adapter.addItems(Arrays.asList(new HelpItem("Как создать план тренировок?","Зайдите во вкладку в нижнем меню и нажмите на кнопку “+”."),
                new HelpItem("Как создать план тренировок?","Зайдите во вкладку в нижнем меню и нажмите на кнопку “+”."),
                new HelpItem("Как создать план тренировок?","Зайдите во вкладку в нижнем меню и нажмите на кнопку “+”.")));
    }
}
