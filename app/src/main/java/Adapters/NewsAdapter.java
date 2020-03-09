package Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.serg.fit.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Pojo.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private List<NewsItem> newsItemList = new ArrayList<>();
    private FragmentActivity activity;

    public NewsAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_news_simple_item,parent,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.bind(newsItemList.get(position),position,holder);
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public void addItems(Collection<NewsItem> items){
        newsItemList.addAll(items);
        notifyDataSetChanged();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {

        private CardView mainFrame;
        private TextView title;
        private TextView text;
        private TextView readMore;
        private ImageButton options;
        private ImageView image;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            mainFrame = (CardView) itemView.findViewById(R.id.main_frame);
            title = (TextView) itemView.findViewById(R.id.title);
            text = (TextView) itemView.findViewById(R.id.text);
            readMore = (TextView) itemView.findViewById(R.id.read_more);
            options = (ImageButton) itemView.findViewById(R.id.options);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        public void bind(NewsItem newsItem, final int position, final NewsHolder holder) {
            title.setText(newsItem.getTitle());
            text.setText(newsItem.getText());

            if(newsItem.getImgUrl().equals("")){
                image.setVisibility(View.GONE);
            }else{
                image.setVisibility(View.VISIBLE);
            }

            if(newsItem.isExpanded()){
                readMore.setVisibility(View.GONE);
                text.setMaxLines(1000);
                text.setEllipsize(null);
            }else {
                readMore.setVisibility(View.VISIBLE);
                text.setMaxLines(4);
                text.setEllipsize(TextUtils.TruncateAt.END);
            }
            readMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newsItemList.get(position).setExpanded(true);
                    TransitionManager.beginDelayedTransition(mainFrame, new AutoTransition());
                    readMore.setVisibility(View.GONE);
                    text.setMaxLines(1000);
                    text.setEllipsize(null);
                }
            });
            options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(holder.options);
                }
            });
            //TODO загрузить картинку
        }

        private void showPopupMenu(final View view) {
            PopupMenu menu = new PopupMenu(view.getContext(),view);
            MenuInflater inflater = menu.getMenuInflater();
            inflater.inflate(R.menu.news_options_menu,menu.getMenu());
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.copy) {
                        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                        //TODO разобраться как копировать новость
                        ClipData clip = ClipData.newPlainText("save","my text");
                        clipboard.setPrimaryClip(clip);
                        return true;
                    }
                    return false;
                }
            });
            menu.show();
        }
    }
}
