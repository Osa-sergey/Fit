package Fragments.SideMenu.ForTrainers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serg.fit.R;
import com.serg.fit.databinding.FragmentTrainerProfileBinding;

import java.util.Arrays;
import java.util.List;

import Adapters.ReviewAdapter;
import Pojo.ReviewItem;
import Pojo.TrainerProfile;
import Utils.LinearItemDecoration;
import Utils.SupportUtils;


public class TrainerProfileFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private TextView age;
    private TextView experience;
    private TextView toWriteReview;
    private TextView toWrite;
    private Bundle args;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        TrainerProfile profile = (TrainerProfile) bundle.getSerializable("profile");
        FragmentTrainerProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trainer_profile,null,false);
        binding.setProfile(profile);
        View view = binding.getRoot();
        age = binding.age;
        List<String> yearType = Arrays.asList(getResources().getStringArray(R.array.year_type));
        if(profile.getAge()!=0)
            age.setText(SupportUtils.editAge(profile.getAge(),yearType));
        experience = binding.experience;
        if(profile.getExperience()!=0)
            experience.setText(SupportUtils.editAge(profile.getExperience(),yearType));
        recyclerView = binding.reviewRecyclerView;
        adapter = new ReviewAdapter();
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new LinearItemDecoration(10));
        loadItems(profile.getReviews());
        args = new Bundle();
        args.putInt("trainerId",profile.getId());
        //кнопки
        toWrite = binding.toWrite;
        toWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO дописать переход к диалогу с тренером
            }
        });
        toWriteReview = binding.toWriteReview;
        toWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(toWriteReview).navigate(R.id.action_trainerProfileFragment_to_writeReviewFragment,args);
            }
        });
        return view;
    }

    private void loadItems(List<ReviewItem> reviews) {
        adapter.addItems(reviews);
    }
}
