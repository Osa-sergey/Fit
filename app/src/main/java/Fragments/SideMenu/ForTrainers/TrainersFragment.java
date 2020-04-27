package Fragments.SideMenu.ForTrainers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serg.fit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapters.TrainerAdapter;
import Pojo.ReviewItem;
import Pojo.TrainerItem;
import Pojo.TrainerProfile;

public class TrainersFragment extends Fragment {

    private RecyclerView recyclerView;
    private TrainerAdapter adapter;
    private View.OnClickListener onItemClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            TrainerItem item = adapter.getItem(position);
            int id = item.getId();
            Bundle args = new Bundle();
            TrainerProfile profile = loadProfile(id);
            item.setUrl(profile.getUrl());
            item.setTrainerName(profile.getFullName());
            item.setRatingNumber(profile.getRating());
            item.setReviewsCount(profile.getReviewsCount());
            float starsCount = 0;
            for (int i = 0; i <profile.getReviewsCount(); i++) {
                starsCount += profile.getReviews().get(i).getStarsCount();
            }
            starsCount /= profile.getReviewsCount();
            item.setStarsCount(starsCount);

            args.putSerializable("profile",profile);
            Navigation.findNavController(recyclerView).navigate(R.id.action_side_trainers_to_trainerProfileFragment,args);
        }
    };

    //TODO скачать данные через async
    private TrainerProfile loadProfile(int id) {
        TrainerProfile profile = new TrainerProfile();
        List<ReviewItem> reviews = new ArrayList<>();
        reviews.add(new ReviewItem("",3,"Jeb Tower","Тренер так себе"));
        reviews.add(new ReviewItem("",3,"Jeb Tower","Тренер так себе"));
        reviews.add(new ReviewItem("",3,"Jeb Tower","Тренер так себе"));
        reviews.add(new ReviewItem("",3,"Jeb Tower","Тренер так себе"));
        reviews.add(new ReviewItem("",3,"Jeb Tower","Тренер так себе"));
        profile.setReviews(reviews);
        return profile;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainers, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.trainer_recyclerView);
        recyclerView.hasFixedSize();
        adapter = new TrainerAdapter();
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        loadItems();
        return view;
    }

    private void loadItems() {
        adapter.addItems(Arrays.asList(new TrainerItem("","Николай Кукин","Сделаю из вас спортсмена",Float.parseFloat("4.3"),Float.parseFloat("3.1"),20)));
    }}
