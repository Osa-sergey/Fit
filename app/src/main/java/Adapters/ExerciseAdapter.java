package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.chip.Chip;
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
        private RatingBar stars;
        private List<Chip> tags;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
            img = (ImageView) itemView.findViewById(R.id.exercise_image);
            title = (TextView) itemView.findViewById(R.id.exercise_title);
            stars = (RatingBar) itemView.findViewById(R.id.stars);

            //теги
            tags = new ArrayList<>();
            Chip chip1, chip2, chip3, chip4;
            chip1 = (Chip) itemView.findViewById(R.id.chip1);
            chip2 = (Chip) itemView.findViewById(R.id.chip2);
            chip3 = (Chip) itemView.findViewById(R.id.chip3);
            chip4 = (Chip) itemView.findViewById(R.id.chip4);
            tags.addAll(Arrays.asList(chip1, chip2, chip3, chip4));
        }

        public void bind(ExerciseItem exerciseItem) {
            //Загружаем картинку
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop());
            Glide.with(itemView.getContext())
                    .load(exerciseItem.getSrc())
                    .apply(requestOptions)
                    .into(img);

            title.setText(exerciseItem.getTitle());
            stars.setRating(exerciseItem.getStarsCount());

            for (int i = 0; i < 4; i++) {
                if (i < exerciseItem.getTags().size()) {
                    tags.get(i).setVisibility(View.VISIBLE);
                    tags.get(i).setText(exerciseItem.getTags().get(i));
                } else tags.get(i).setVisibility(View.GONE);
            }
        }
    }
}
