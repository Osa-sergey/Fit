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

import Pojo.Profile;

public class ProfileFragment extends Fragment {

    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Profile profile = new Profile("Овчинников Сергей");
        FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,null,false);
        View view = binding.getRoot();
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
