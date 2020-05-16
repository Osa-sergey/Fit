package com.serg.fit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.serg.fit.R;
import com.transitionseverywhere.extra.Scale;

public class ConversationActivity extends AppCompatActivity {

    private ImageView ivEmoji;
    private ImageView ivSend;
    private ImageButton ivVoiceMessage;
    private ImageView ivAttach;

    private EditText etMessage;


    private ViewGroup transitionsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        ivEmoji = findViewById(R.id.ivEmoji);
        ivSend = findViewById(R.id.ivSend);
        ivVoiceMessage = findViewById(R.id.ivVoiceMessage);
        ivAttach = findViewById(R.id.ivAttachment);
        etMessage = findViewById(R.id.etMessage);
        transitionsContainer = findViewById(R.id.chat_container);

        ivSend.setVisibility(View.GONE);

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etMessage.getText().toString().length()>0 && ivSend.getVisibility() == View.GONE){


                    TransitionSet set = new TransitionSet()
                            .addTransition(new Scale(0.7f))
                            .addTransition(new Fade())
                            .setInterpolator(new FastOutLinearInInterpolator());

                    TransitionManager.beginDelayedTransition(transitionsContainer, set);


                    ivSend.setVisibility(View.VISIBLE);
                    ivVoiceMessage.setVisibility(View.GONE);
                }else if (etMessage.getText().toString().length()==0 && ivSend.getVisibility() == View.VISIBLE){

                    TransitionSet set = new TransitionSet()
                            .addTransition(new Scale(0.7f))
                            .addTransition(new Fade())
                            .setInterpolator(new FastOutLinearInInterpolator());

                    TransitionManager.beginDelayedTransition(transitionsContainer, set);

                    ivSend.setVisibility(View.GONE);
                    ivVoiceMessage.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
