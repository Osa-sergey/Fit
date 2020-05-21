package Fragments.SideMenu.ForSettings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.serg.fit.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsTypeFragment extends Fragment {

    private RadioGroup group;
    private SharedPreferences pref;
    private RadioButton btn1,btn2,btn3;
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.radio0:
                    pref.edit().putString("settingsType","only_I").apply();
                    //TODO Пометить всех как запрещенныхbreak;
                case R.id.radio1:
                    pref.edit().putString("settingsType","all_users").apply();
                    //TODO Пометить всех как разрещенных
                    break;
                case R.id.radio2:
                    Navigation.findNavController(group).navigate(R.id.action_settingsTypeFragment_to_settingsListFragment);
                    pref.edit().putString("settingsType","selected").apply();
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_type,null);
        group = (RadioGroup) view.findViewById(R.id.radioGroup);
        btn1 = (RadioButton) view.findViewById(R.id.radio0);
        btn2 = (RadioButton) view.findViewById(R.id.radio1);
        btn3 = (RadioButton) view.findViewById(R.id.radio2);
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        group.clearCheck();
        pref = getActivity().getSharedPreferences("Pref",MODE_PRIVATE);
        switch (pref.getString("settingsType","only_I")){
            case "only_I":
                group.check(R.id.radio0);
                break;
            case "all_users":
                group.check(R.id.radio1);
                break;
            case "selected":
                group.check(R.id.radio2);
                break;
        }

        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if(pref.getString("settingsType","only_I").equals("selected"))
//        Navigation.findNavController(view).navigate(R.id.action_settingsTypeFragment_to_settingsListFragment);
//    }
}
