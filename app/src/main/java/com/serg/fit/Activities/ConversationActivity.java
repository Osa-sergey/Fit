package com.serg.fit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.serg.fit.R;
import com.transitionseverywhere.extra.Scale;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class ConversationActivity extends AppCompatActivity {

    private ImageView ivEmoji;
    private ImageView ivSend;
    private ImageButton ivVoiceMessage;
    private ImageView ivAttach;

    private EditText etMessage;


    private ViewGroup transitionsContainer;


    private ArrayList<String> alMessages;
    private ArrayList<String> alTime;
    private ArrayList<String> alAuthor;

    private TextView tvMessageReceived;
    private TextView tvMessageSent;
    private TextView tvTimeReceived;
    private TextView tvTimeSent;

    private RelativeLayout rlSent;
    private RelativeLayout rlReceived;

    private FirebaseAuth mAuth;

    private ListView lvMessages;
    private ListViewAdapter listViewAdapter;

    private String urlGetMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        mAuth = FirebaseAuth.getInstance();

        alMessages = new ArrayList();
        alTime = new ArrayList();
        alAuthor = new ArrayList<>();

        ivEmoji = findViewById(R.id.ivEmoji);
        ivSend = findViewById(R.id.ivSend);
        ivVoiceMessage = findViewById(R.id.ivVoiceMessage);
        ivAttach = findViewById(R.id.ivAttachment);
        etMessage = findViewById(R.id.etMessage);
        transitionsContainer = findViewById(R.id.chat_container);

        ivSend.setVisibility(View.GONE);


        lvMessages = findViewById(R.id.lvChat);
        listViewAdapter = new ListViewAdapter();


        initWebSocket(urlGetMessage);





        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etMessage.getText().toString().length() > 0 && ivSend.getVisibility() == View.GONE) {


                    TransitionSet set = new TransitionSet()
                            .addTransition(new Scale(0.7f))
                            .addTransition(new Fade())
                            .setInterpolator(new FastOutLinearInInterpolator());

                    TransitionManager.beginDelayedTransition(transitionsContainer, set);


                    ivSend.setVisibility(View.VISIBLE);
                    ivVoiceMessage.setVisibility(View.GONE);
                } else if (etMessage.getText().toString().length() == 0 && ivSend.getVisibility() == View.VISIBLE) {

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

    private class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return alMessages.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_chat_item, null);
            tvMessageReceived = view.findViewById(R.id.tvMessageReceived);
            tvMessageSent = view.findViewById(R.id.tvSentMessage);

            tvTimeReceived = view.findViewById(R.id.tvTimeReceived);
            tvTimeSent = view.findViewById(R.id.tvSentTime);

            rlReceived = view.findViewById(R.id.rlReceivedMessage);
            rlSent = view.findViewById(R.id.rlSentMessage);

//          Received
            if (!alAuthor.get(i).equals(mAuth.getCurrentUser().getEmail())) {
                rlSent.setVisibility(View.GONE);
                rlReceived.setVisibility(View.VISIBLE);

                tvTimeReceived.setText(alTime.get(i));
                tvMessageReceived.setText(alMessages.get(i));
//          Sent
            } else {
                rlSent.setVisibility(View.VISIBLE);
                rlReceived.setVisibility(View.GONE);

                tvTimeSent.setText(alTime.get(i));
                tvMessageSent.setText(alMessages.get(i));

            }
            return view;
        }

        public void addItem(JSONObject item){

        }
    }

    private void initWebSocket(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
//        WebSocket webSocket = client.newWebSocket(request,)
        lvMessages.setAdapter(listViewAdapter);
    }

    public class SocketListener extends WebSocketListener{

        public ConversationActivity conversationActivity;

        public SocketListener(ConversationActivity conversationActivity) {
            this.conversationActivity = conversationActivity;

        }

        @Override
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosed(webSocket, code, reason);

            conversationActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(conversationActivity, "Connected successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
        }

//        Сообщение в тексте

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull final String text) {
            super.onMessage(webSocket, text);

            conversationActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("message", text);
                        jsonObject.put("bySerer", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
//      Сообщение в байтах
        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            super.onOpen(webSocket, response);
        }
    }


}
