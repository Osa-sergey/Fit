package Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serg.fit.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Pojo.SettingsUserItem;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {

    private List<SettingsUserItem>  userItemList = new ArrayList<>();

    public List<SettingsUserItem> getUserItemList() {
        return userItemList;
    }

    public void addItems(Collection<SettingsUserItem> items){
        userItemList.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_users_settings_simple_item,parent,false);
        return new SettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        holder.bind(userItemList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return userItemList.size();
    }

    public class SettingsViewHolder extends RecyclerView.ViewHolder{

       private ImageView avatar;
       private TextView username;
       private CheckBox state;

       public SettingsViewHolder(@NonNull View itemView) {
           super(itemView);
           avatar = (ImageView) itemView.findViewById(R.id.avatar);
           username = (TextView) itemView.findViewById(R.id.username);
           state = (CheckBox) itemView.findViewById(R.id.state);
       }

       public void bind(SettingsUserItem settingsUserItem, final int index){
           username.setText(settingsUserItem.getUsername());
           state.setChecked(settingsUserItem.isState());
           //TODO добавить загрузку аватара
           state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   Log.d("ChengeCheck","yes");
                   userItemList.get(index).setState(!userItemList.get(index).isState());
                   //TODO Дописать update данных в DB
               }
           });
       }
   }
}
