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
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView avatar;
    private TextView name, edit, weight, power, experience, age, email, phone, med;
    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Profile profile = new Profile("Овчинников Сергей");
        final FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,null,false);
        View view = binding.getRoot();
        binding.setProfile(profile);
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(binding.edit).navigate(R.id.action_side_profile_to_editProfileFragment);
            }
        });

        return view;
    }
}
