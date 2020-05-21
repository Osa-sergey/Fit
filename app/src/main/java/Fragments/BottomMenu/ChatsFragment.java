package Fragments.BottomMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serg.fit.Activities.ConversationActivity;
import com.serg.fit.R;

import java.util.Arrays;

import Adapters.ChatAdapter;
import Pojo.ChatItem;

public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.chats_recyclerView);
        adapter = new ChatAdapter();
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        loadItems();

        return view;
    }

    private void loadItems() {
        adapter.addItems(Arrays.asList(new ChatItem(null,"Овчинников Сергей",true,"Привет друг",System.currentTimeMillis() ),
                new ChatItem(null,"Кирилл Петров",false,"Новая тренировка в стиле 70-ых",2344)));

        adapter.setOnItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getContext(), ConversationActivity.class));

            }
        });
    }
}
