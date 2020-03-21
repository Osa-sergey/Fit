package Fragments.SideMenu;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.serg.fit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapters.ExerciseAdapter;
import Pojo.ExerciseItem;
import Utils.GridItemDecoration;

public class ExercisesFragment extends Fragment {

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager manager;
    private ExerciseAdapter adapter;
    private Dialog dialogExercise;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            ExerciseItem item = adapter.getItem(position);
            dialogExercise = new Dialog(getContext());
            dialogExercise.setCancelable(false);
            dialogExercise.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogExercise.setContentView(R.layout.dialog_exercise);
            dialogExercise.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //TODO загрузить картинку
            TextView title = (TextView) dialogExercise.findViewById(R.id.exercise_title);
            TextView mainText = (TextView) dialogExercise.findViewById(R.id.main_text);
            ImageView star1,star2,star3,star4,star5;
            star1 = (ImageView) dialogExercise.findViewById(R.id.star1);
            star2 = (ImageView) dialogExercise.findViewById(R.id.star2);
            star3 = (ImageView) dialogExercise.findViewById(R.id.star3);
            star4 = (ImageView) dialogExercise.findViewById(R.id.star4);
            star5 = (ImageView) dialogExercise.findViewById(R.id.star5);
            List<ImageView> stars = new ArrayList<>();
            stars.addAll(Arrays.asList(star1,star2,star3,star4,star5));
            MaterialButton btn = (MaterialButton) dialogExercise.findViewById(R.id.btn_submit);
            title.setText(item.getTitle());
            mainText.setText(item.getDescription());
            for (int i = 0; i <5 ; i++) {
                if(i<item.getStarsCount()) stars.get(i).setImageResource(R.drawable.ic_star_accent);
                else stars.get(i).setImageResource(R.drawable.ic_star);
            }
            List<Chip> tags = new ArrayList<>();
            Chip chip1,chip2,chip3,chip4;
            chip1 = (Chip) dialogExercise.findViewById(R.id.chip1);
            chip2 = (Chip) dialogExercise.findViewById(R.id.chip2);
            chip3 = (Chip) dialogExercise.findViewById(R.id.chip3);
            chip4 = (Chip) dialogExercise.findViewById(R.id.chip4);
            tags.addAll(Arrays.asList(chip1,chip2,chip3,chip4));
            for (int i = 0; i <4 ; i++) {
                if(i<item.getTags().size()){
                    tags.get(i).setVisibility(View.VISIBLE);
                    tags.get(i).setText(item.getTags().get(i));
                }else tags.get(i).setVisibility(View.GONE);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogExercise.dismiss();
                }
            });
            dialogExercise.show();
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.exercise_recyclerView);
        recyclerView.hasFixedSize();
        adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridItemDecoration(10));
        loadItems();
        return view;
    }

    private void loadItems() {
        List<String> tags = new ArrayList<>();
        tags.addAll(Arrays.asList("корпус","пресс"));
        adapter.addItems(Arrays.asList(new ExerciseItem(3,"","Пресc","Поднятие корпуса без отрыва ног от пола",tags),
                new ExerciseItem(3,"","Продольное скручивание","Выполняйте упражнение, как показано на картинке",tags),
                new ExerciseItem(3,"","Пресc","Поднятие корпуса без отрыва ног от пола",tags),
                new ExerciseItem(3,"","Пресc","Поднятие корпуса без отрыва ног от пола",tags),
                new ExerciseItem(3,"","Пресc","Поднятие корпуса без отрыва ног от пола",tags)));
    }
}
