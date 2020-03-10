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

import Pojo.TrainerItem;
import de.hdodenhof.circleimageview.CircleImageView;

public class TrainerAdapter extends RecyclerView.Adapter<TrainerAdapter.TrainerHolder> {

    private List<TrainerItem> trainerItemList = new ArrayList<>();

    @NonNull
    @Override
    public TrainerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_trainer_simple_item,parent,false);
        return new TrainerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerHolder holder, int position) {
        holder.bind(trainerItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return trainerItemList.size();
    }

    public void addItems(Collection<TrainerItem> items){
        trainerItemList.addAll(items);
        notifyDataSetChanged();
    }

    public class TrainerHolder extends RecyclerView.ViewHolder {

        private CircleImageView avatar;
        private TextView trainerName;
        private TextView ratingNumber;
        private TextView comment;
        private TextView reviewsCount;
        private List<ImageView> stars;

        public TrainerHolder(@NonNull View itemView) {
            super(itemView);
            ImageView star1,star2,star3,star4,star5;
            star1 = (ImageView) itemView.findViewById(R.id.star1);
            star2 = (ImageView) itemView.findViewById(R.id.star2);
            star3 = (ImageView) itemView.findViewById(R.id.star3);
            star4 = (ImageView) itemView.findViewById(R.id.star4);
            star5 = (ImageView) itemView.findViewById(R.id.star5);
            stars = new ArrayList<>();
            stars.addAll(Arrays.asList(star1,star2,star3,star4,star5));
            avatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            trainerName = (TextView) itemView.findViewById(R.id.trainer_name);
            ratingNumber = (TextView) itemView.findViewById(R.id.rating_number);
            comment = (TextView) itemView.findViewById(R.id.comment);
            reviewsCount = (TextView) itemView.findViewById(R.id.reviews_count);
        }

        public void bind(TrainerItem trainerItem) {
            //TODO добавить аватар
            trainerName.setText(trainerItem.getTrainerName());
            for(int i=0;i<5;i++){
                if(i<trainerItem.getStarsCount()) stars.get(i).setImageResource(R.drawable.ic_star_accent);
                else stars.get(i).setImageResource(R.drawable.ic_star);
            }
            comment.setText(trainerItem.getComment());
            ratingNumber.setText(String.valueOf(trainerItem.getRatingNumber()));
            reviewsCount.setText(String.valueOf(trainerItem.getReviewsCount()));
        }
    }
}
