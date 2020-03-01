package Adapters;

import android.graphics.Color;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.serg.fit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import Pojo.TargetItem;
import Utils.MyValueFormatter;

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
        private BarChart barChart;

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
            barChart = (BarChart) itemView.findViewById(R.id.barChart);
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

            barChart.setDrawBarShadow(false);
            barChart.setDrawValueAboveBar(false);
            barChart.setPinchZoom(false);
            Description description = new Description();
            description.setEnabled(false);
            barChart.setDescription(description);

            ArrayList<BarEntry> entries = new ArrayList<>();
            for (int i = 0; i <12 ; i++) {
                entries.add(new BarEntry((float) i,40f));
            }

            ArrayList<BarEntry> entries1 = new ArrayList<>();


            BarDataSet set = new BarDataSet(entries,"Сделано");
            set.setDrawValues(false);
            set.setColor(Color.rgb(0, 106, 69));

            BarDataSet set1 = new BarDataSet(entries1,"Задано");
            set1.setDrawValues(false);
            set1.setColor(Color.rgb(196,196,196));

            List<IBarDataSet> sets = new ArrayList<>();
            sets.add(set1);
            sets.add(set);
            BarData barData = new BarData(sets);
            barData.setBarWidth(0.90f);
            barChart.setData(barData);

            //оси
            String[] dates = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12"};
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new MyValueFormatter(dates));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setCenterAxisLabels(false);
            xAxis.setAxisMinimum(-0.5f);
            xAxis.setGranularity(1);
            xAxis.setDrawGridLines(false);
            barChart.getAxisRight().setEnabled(false);
        }
    }
}