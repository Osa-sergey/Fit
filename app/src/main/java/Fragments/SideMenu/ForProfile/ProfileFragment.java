package Fragments.SideMenu.ForProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.serg.fit.R;
import com.serg.fit.databinding.FragmentProfileBinding;

import java.util.Arrays;
import java.util.List;

import Pojo.Profile;
import Utils.SupportUtils;

public class ProfileFragment extends Fragment {

    private Toolbar toolbar;
    private TextView experience;
    private TextView age;
    private TextView weight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Profile profile = new Profile("Овчинников Сергей");
        FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,null,false);
        View view = binding.getRoot();
        List<String>yearType = Arrays.asList(getResources().getStringArray(R.array.year_type));
        age = binding.age;
        if(profile.getAge()!=0)
            age.setText(SupportUtils.editAge(profile.getAge(),yearType));
        experience = binding.experience;
        if(profile.getExperience()!=-1)
            experience.setText(SupportUtils.editAge(profile.getExperience(),yearType));
        weight = binding.weight;
        if(profile.getWeight()!=0.0)
            weight.setText(profile.getWeight() + " " + getResources().getString(R.string.profile_weight_measure));
        final TextView edit = binding.edit;
        binding.setProfile(profile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(edit).navigate(R.id.action_side_profile_to_editProfileFragment);
            }
        });

        return view;
    }
}
