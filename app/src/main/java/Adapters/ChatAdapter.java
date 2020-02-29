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
import java.util.Collection;
import java.util.List;

import Pojo.ChatItem;
import Utils.SupportUtils;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatItem> chatItemList =  new ArrayList<>();

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_chat_simple_item,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.bind(chatItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatItemList.size();
    }

    public void addItems(Collection<ChatItem> items){
        chatItemList.addAll(items);
        notifyDataSetChanged();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{

        private ImageView avatar;
        private TextView fullName;
        private ImageView selected;
        private TextView lastMsg;
        private TextView date;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            fullName = (TextView) itemView.findViewById(R.id.fullName);
            selected = (ImageView) itemView.findViewById(R.id.selected);
            lastMsg = (TextView) itemView.findViewById(R.id.lastMsg);
            date = (TextView) itemView.findViewById(R.id.date);
        }

        public void bind(ChatItem chatItem){
            //TODO заггузка аватара
            fullName.setText(chatItem.getFullName());
            if(chatItem.isSelected()) selected.setVisibility(View.VISIBLE);
            else selected.setVisibility(View.INVISIBLE);
            if (chatItem.getLastMsg() == null) lastMsg.setText("");
            else lastMsg.setText(chatItem.getLastMsg());
            date.setText(SupportUtils.getDateTimeForChat(chatItem.getDate()));
        }
    }
}
