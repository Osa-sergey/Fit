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

import Adapters.TrainerAdapter;
import Pojo.TrainerItem;

public class TrainersFragment extends Fragment {

    private RecyclerView recyclerView;
    private TrainerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainers, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.trainer_recyclerView);
        recyclerView.hasFixedSize();
        adapter = new TrainerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        loadItems();
        return view;
    }

    private void loadItems() {
        adapter.addItems(Arrays.asList(new TrainerItem("","Николай Кукин","Сделаю из вас спортсмена",4,Float.parseFloat("3.1"),20)));
    }
}
