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

import Pojo.ReviewItem;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<ReviewItem> reviewItemList = new ArrayList<>();

    public void addItems(Collection<ReviewItem> items){
        reviewItemList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_review_simple_item,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(reviewItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return reviewItemList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView avatar;
        private TextView fullName;
        private TextView text;
        private List<ImageView> stars;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            fullName = (TextView) itemView.findViewById(R.id.fullName);
            text = (TextView) itemView.findViewById(R.id.text);
            ImageView star1,star2,star3,star4,star5;
            star1 = (ImageView) itemView.findViewById(R.id.star1);
            star2 = (ImageView) itemView.findViewById(R.id.star2);
            star3 = (ImageView) itemView.findViewById(R.id.star3);
            star4 = (ImageView) itemView.findViewById(R.id.star4);
            star5 = (ImageView) itemView.findViewById(R.id.star5);
            stars = new ArrayList<>();
            stars.addAll(Arrays.asList(star1,star2,star3,star4,star5));
        }

        public void bind(ReviewItem reviewItem) {
            //TODO загрузить аватар
            fullName.setText(reviewItem.getFullName());
            text.setText(reviewItem.getText());
            for (int i = 0; i <5 ; i++) {
                if (i<reviewItem.getStarsCount()) stars.get(i).setImageResource(R.drawable.ic_star_accent);
                else stars.get(i).setImageResource(R.drawable.ic_star);
            }
        }
    }
}
