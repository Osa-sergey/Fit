package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serg.fit.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Pojo.HelpItem;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {

    private List<HelpItem> helpItemList = new ArrayList<>();

    public void addItems(Collection<HelpItem> items){
        helpItemList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_help_simple_item,parent,false);
        return new HelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, int position) {
        holder.bind(helpItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return helpItemList.size();
    }

        public class HelpViewHolder extends RecyclerView.ViewHolder{

            private TextView title;
            private TextView mainText;

            public HelpViewHolder(@NonNull View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                mainText = (TextView) itemView.findViewById(R.id.main_text);
            }

            public void bind(HelpItem helpItem) {
                title.setText(helpItem.getTitle());
                mainText.setText(helpItem.getMainText());
            }
        }
}
