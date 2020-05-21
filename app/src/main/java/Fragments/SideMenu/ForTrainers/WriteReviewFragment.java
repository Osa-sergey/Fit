package Fragments.SideMenu.ForTrainers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.serg.fit.R;

import java.util.Arrays;
import java.util.List;

public class WriteReviewFragment extends Fragment implements View.OnClickListener{

    private List<ImageView> stars;
    private TextInputEditText text;
    private TextView send;
    private int starsCount = 0;
    private int trainerId;
    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.star1:
                    starsCount = 1;
                    break;
                case R.id.star2:
                    starsCount = 2;
                    break;
                case R.id.star3:
                    starsCount = 3;
                    break;
                case R.id.star4:
                    starsCount = 4;
                    break;
                case R.id.star5:
                    starsCount = 5;
                    break;
            }
            for (int i = 0; i < stars.size(); i++) {
                if(i < starsCount) stars.get(i).setImageResource(R.drawable.ic_star_accent);
                else stars.get(i).setImageResource(R.drawable.ic_hollow_star);
            }
        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_review,null);
        ImageView star1,star2,star3,star4,star5;
        star1 = (ImageView) view.findViewById(R.id.star1);
        star2 = (ImageView) view.findViewById(R.id.star2);
        star3 = (ImageView) view.findViewById(R.id.star3);
        star4 = (ImageView) view.findViewById(R.id.star4);
        star5 = (ImageView) view.findViewById(R.id.star5);
        stars = Arrays.asList(star1,star2,star3,star4,star5);
        text = (TextInputEditText) view.findViewById(R.id.text);
        send = (TextView) view.findViewById(R.id.send);
        star1.setOnClickListener(this);
        star2.setOnClickListener(this);
        star3.setOnClickListener(this);
        star4.setOnClickListener(this);
        star5.setOnClickListener(this);
        text.setText("");
        Bundle args = this.getArguments();
        trainerId = args.getInt("trainerId");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),R.string.err_emptyReview,Toast.LENGTH_LONG).show();
                    return;
                }
                sendReview(text.getText().toString());
            }

            public void sendReview(String text){
                //TODO Написать отправку отзыва не забыть про свой ID и ID тренера

            }
        });
        return view;
    }


}
