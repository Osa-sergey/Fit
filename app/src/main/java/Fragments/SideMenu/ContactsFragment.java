package Fragments.SideMenu;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.serg.fit.R;

public class ContactsFragment extends Fragment {

    TextInputEditText title, text;
    MaterialButton submit;
    TextView aboutApp;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts,null);

        title = (TextInputEditText) view.findViewById(R.id.contacts_them);
        text = (TextInputEditText) view.findViewById(R.id.contacts_text);
        aboutApp = (TextView) view.findViewById(R.id.about_app);

        submit = (MaterialButton) view.findViewById(R.id.send);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO формирование репорта
             dialog = new Dialog(getContext());
             dialog.setCancelable(false);
             dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
             dialog.setContentView(R.layout.dialog_contacts);
             dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
             MaterialButton button = (MaterialButton)dialog.findViewById(R.id.btn_submit);
             button.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Navigation.findNavController(title).popBackStack();
                     dialog.dismiss();
                 }
             });
             dialog.show();
            }
        });

        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_side_contacts_to_fragmentAbout);
            }
        });
        return view;
    }
}
