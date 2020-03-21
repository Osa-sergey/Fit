package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serg.fit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import Pojo.ExerciseItem;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {

    private List<ExerciseItem> exerciseItemList = new ArrayList<>();
    private View.OnClickListener onItemClickListener;

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_exercise_simple_item,parent,false);
        return new ExerciseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        holder.bind(exerciseItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return exerciseItemList.size();
    }

    public void addItems(Collection<ExerciseItem> items){
        exerciseItemList.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }
    public ExerciseItem getItem(int pos){
        return exerciseItemList.get(pos);
    }

    public class ExerciseHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView title;
        private List<ImageView> stars;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
            img = (ImageView) itemView.findViewById(R.id.exercise_image);
            title = (TextView) itemView.findViewById(R.id.exercise_title);
            ImageView star1,star2,star3,star4,star5;
            star1 = (ImageView) itemView.findViewById(R.id.star1);
            star2 = (ImageView) itemView.findViewById(R.id.star2);
            star3 = (ImageView) itemView.findViewById(R.id.star3);
            star4 = (ImageView) itemView.findViewById(R.id.star4);
            star5 = (ImageView) itemView.findViewById(R.id.star5);
            stars = new ArrayList<>();
            stars.addAll(Arrays.asList(star1,star2,star3,star4,star5));
        }

        public void bind(ExerciseItem exerciseItem) {
            //TODO загрузить картинку
            title.setText(exerciseItem.getTitle());
            for (int i = 0; i <5 ; i++) {
                if (i<exerciseItem.getStarsCount()) stars.get(i).setImageResource(R.drawable.ic_star_accent);
                else stars.get(i).setImageResource(R.drawable.ic_star);
            }
        }
    }
}
