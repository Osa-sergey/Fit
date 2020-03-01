package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.devzone.fillprogresslayout.FillProgressLayout;
import com.serg.fit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import Pojo.TargetItem;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.TargetViewHolder> {

    private List<TargetItem> targetItemList = new ArrayList<>();

    @NonNull
    @Override
    public TargetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_target_simple_item,parent,false);
        return new TargetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetViewHolder holder, int position) {
        holder.bind(targetItemList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return targetItemList.size();
    }

    public void addItems(Collection<TargetItem> items){
        targetItemList.addAll(items);
        notifyDataSetChanged();
    }

    public class TargetViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private List<ImageView> stars;
        private FillProgressLayout progressBar;
        private TextView percentage;
        private ImageView expand;
        private ConstraintLayout expanded;
        private TextView trainings;
        private TextView spent;
        private CardView cardView;

        public TargetViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            ImageView star1,star2,star3,star4,star5;
            star1 = (ImageView) itemView.findViewById(R.id.star1);
            star2 = (ImageView) itemView.findViewById(R.id.star2);
            star3 = (ImageView) itemView.findViewById(R.id.star3);
            star4 = (ImageView) itemView.findViewById(R.id.star4);
            star5 = (ImageView) itemView.findViewById(R.id.star5);
            stars = new ArrayList<>();
            cardView = (CardView) itemView.findViewById(R.id.main_card);
            stars.addAll(Arrays.asList(star1,star2,star3,star4,star5));
            progressBar = (FillProgressLayout) itemView.findViewById(R.id.progressBar);
            percentage = (TextView) itemView.findViewById(R.id.percentage);
            expand = (ImageView) itemView.findViewById(R.id.expand);
            expanded = (ConstraintLayout) itemView.findViewById(R.id.expanded);
            trainings = (TextView) itemView.findViewById(R.id.trainings);
            spent = (TextView) itemView.findViewById(R.id.spent);
        }

        public void bind(TargetItem targetItem, final int position) {
            title.setText(targetItem.getTitle());
            for (int i = 0; i < 5 ; i++) {
                if(i<targetItem.getStarsCount()) stars.get(i).setImageResource(R.drawable.ic_star_accent);
                else stars.get(i).setImageResource(R.drawable.ic_star);
            }
            if (targetItem.isExpand()){
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                expanded.setVisibility(View.VISIBLE);
                expand.setImageResource(R.drawable.ic_expand_less);
            }else {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                expanded.setVisibility(View.GONE);
                expand.setImageResource(R.drawable.ic_expand_more);
            }
            expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    targetItemList.get(position).setExpand(!targetItemList.get(position).isExpand());
                    if (targetItemList.get(position).isExpand()){
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expanded.setVisibility(View.VISIBLE);
                        expand.setImageResource(R.drawable.ic_expand_less);
                    }else {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        expanded.setVisibility(View.GONE);
                        expand.setImageResource(R.drawable.ic_expand_more);
                    }
                }
            });
            progressBar.setProgress(targetItem.getProgress(),true);
            percentage.setText(targetItem.getProgressStr());
            trainings.setText(targetItem.getTrainings());
            spent.setText(targetItem.getSpentTime());
        }
    }
}