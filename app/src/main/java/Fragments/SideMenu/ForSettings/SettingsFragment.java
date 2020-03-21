package Fragments.SideMenu.ForSettings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.serg.fit.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {

    private SwitchMaterial notifSwitch;
    private TextView selectSound;
    private TextView selectData;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,null);
        notifSwitch = (SwitchMaterial) view.findViewById(R.id.select_switch);
        selectSound = (TextView) view.findViewById(R.id.select_sound);
        selectData = (TextView) view.findViewById(R.id.select_data);
        notifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //TODO дописать
            }
        });
        selectSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pref = getActivity().getSharedPreferences("Pref",MODE_PRIVATE);
        switch (pref.getString("settingsType","only_I")){
            case "only_I":
                selectData.setText(R.string.only_I);
                break;
            case "all_users":
                selectData.setText(R.string.all_users);
                break;
            case "selected":
                selectData.setText(R.string.selected);
                break;
        }
        selectData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(notifSwitch).navigate(R.id.action_side_settings_to_settingsTypeFragment);
            }
        });
        return view;
    }
}
