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
    private View.OnClickListener onItemClickListener;

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

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TrainerItem getItem(int pos){
        return trainerItemList.get(pos);
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
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
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
            float starsCount = trainerItem.getStarsCount();
            int i;
            for(i=0;i<(int)starsCount;i++){
                stars.get(i).setImageResource(R.drawable.ic_star_accent);
            }
            if((starsCount - (int)starsCount) < 0.25)
                stars.get(i).setImageResource(R.drawable.ic_star);
            else if((starsCount - (int)starsCount) < 0.75) stars.get(i).setImageResource(R.drawable.ic_half_star);
                else stars.get(i).setImageResource(R.drawable.ic_star_accent);
            i++;
            for (int j = i; j < 5;j ++) {
                stars.get(j).setImageResource(R.drawable.ic_star);
            }
            comment.setText(trainerItem.getComment());
            ratingNumber.setText(String.valueOf(trainerItem.getRatingNumber()));
            reviewsCount.setText(String.valueOf(trainerItem.getReviewsCount()));
        }
    }
}
