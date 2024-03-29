package info.manipal.aesher.infomuj.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.transitionseverywhere.ChangeText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ai.api.AIConfiguration;
import ai.api.AIListener;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Adapters.Author;
import info.manipal.aesher.infomuj.Adapters.Message;
import info.manipal.aesher.infomuj.R;


public class DialogueFlow extends Fragment implements AIListener, TextToSpeech.OnInitListener, MessagesListAdapter.OnLoadMoreListener {

    public static final String textWhenMainScreen = "Info Muj";
    public static final String textWhenDialogueScreen = "Welcome To MUJ ";
    TextView heading;
    ViewGroup transitionsContainer;
    Button dialogueFlowButton;
    AIService aiService;
    @BindView(R.id.messagesList)
    MessagesList messagesList;
    String[] parts;
    Author author;
    Message message;
    MessagesListAdapter<Message> adapter;
    private TextToSpeech tts;
    private GestureDetector gestureDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chatbot, container, false);
        ButterKnife.bind(this, v);

        isSoundPermission();

        //getting the container and the buttons of the main activity
        transitionsContainer = Objects.requireNonNull(getActivity()).getWindow().getDecorView().findViewById(R.id.container);
        heading = getActivity().getWindow().getDecorView().findViewById(R.id.heading);
        dialogueFlowButton = getActivity().getWindow().getDecorView().findViewById(R.id.contactButton);
        //replaces the text heding to welcome to muj
        replaceText();
        replaceButton();
        tts = new TextToSpeech(getContext(), this);


        final ai.api.android.AIConfiguration config = new ai.api.android.AIConfiguration("e0d78e0c11bc4c519a0961f81f23576b",
                AIConfiguration.SupportedLanguages.English,
                ai.api.android.AIConfiguration.RecognitionEngine.System);

        config.setRecognizerStartSound(getResources().openRawResourceFd(R.raw.test_start));
        config.setRecognizerStopSound(getResources().openRawResourceFd(R.raw.test_stop));
        config.setRecognizerCancelSound(getResources().openRawResourceFd(R.raw.test_cancel));

        aiService = AIService.getService(Objects.requireNonNull(getContext()), config);
        aiService.setListener(this);

        gestureDetector = new GestureDetector(getActivity(), new SingleTapConfirm());


        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                welcomeMessage();
            }
        };
        handler.postDelayed(runnable, 500);

        dialogueFlowButton.setOnTouchListener(null);
        dialogueFlowButton.setOnClickListener(null);

        dialogueFlowButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (gestureDetector.onTouchEvent(motionEvent)) {
                    dialogueFlowButton.setBackgroundResource(R.drawable.roundedbutton);
                    dialogueFlowButton.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    return true;
                } else {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            aiService.startListening();
                            dialogueFlowButton.animate().scaleX(1.19f).scaleY(1.19f).setDuration(100).start();
                            dialogueFlowButton.setBackgroundResource(R.drawable.roundedbutton2);

                            break;

                        case MotionEvent.ACTION_UP:
                            dialogueFlowButton.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                            dialogueFlowButton.setBackgroundResource(R.drawable.roundedbutton);
                            break;
                    }

                }

                return true;
            }
        });

        return v;

    }


    public void isSoundPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
        }

    }

    public void welcomeMessage() {

        List<Message> randomShit;
        randomShit = new ArrayList<>();
        author = new Author("1", "Aashis", "hey");
        message = new Message("1", "Welcome User, Tap on initiate chat to get a conversation started", author);
        speakOut("Welcome User, Tap on initiate chat to get a conversation started");
        randomShit.add(message);
        adapter = new MessagesListAdapter<>("2", null);
        messagesList.setAdapter(adapter);
        adapter.addToEnd(randomShit, true);

    }


    public void replaceText() {
        com.transitionseverywhere.TransitionManager.beginDelayedTransition(transitionsContainer,
                new ChangeText().setChangeBehavior(3));

        if (heading.getText().toString().equals(textWhenMainScreen))
            heading.setText(textWhenDialogueScreen);
        else
            heading.setText(textWhenMainScreen);

    }

    public void replaceButton() {
        dialogueFlowButton.animate().scaleX(0f).scaleY(0f).setDuration(300).start();
        Handler delayForButtonChange = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dialogueFlowButton.setText("Initiate a Chat");
                dialogueFlowButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.ic_mic), null, null, null);
                dialogueFlowButton.animate().scaleX(1f).scaleY(1f).setDuration(300).start();
            }
        };
        delayForButtonChange.postDelayed(runnable, 301);
    }

    @Override
    public void onResult(AIResponse result) {

        String string = "" + result;

        if (string.contains("resolvedQuery")) {
            parts = string.split(",");
            parts[3] = parts[3].replace("resolvedQuery='", "");
            parts[3] = parts[3].replace("'}", "");
        }


        Log.w("API_query", string);


        if (parts[3] != null && result.getResult() != null) {
            author = new Author("2", "Chatbot", "hey");
            message = new Message("2", parts[3], author);
            adapter.addToStart(message, true);
            ai.api.model.Result RESULT = result.getResult();
            String textS = RESULT.getFulfillment().getSpeech();
            Log.w("API_result", textS);
            author = new Author("1", "Chatbot", "hey");
            message = new Message("1", textS, author);
            adapter.addToStart(message, true);
            speakOut(textS);
        }


    }

    @Override
    public void onError(AIError error) {
        try {
            dialogueFlowButton.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
            dialogueFlowButton.setBackgroundResource(R.drawable.roundbutton3);
        } catch (Exception e) {
            Log.w("DialogueFlow Error", "" + e);
        }
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {
        try {
            dialogueFlowButton.animate().scaleX(1.19f).scaleY(1.19f).setDuration(100).start();
            dialogueFlowButton.setBackgroundResource(R.drawable.roundedbutton2);
        } catch (Exception e) {
            Log.w("DialogueFlow Error", "" + e);
        }
    }

    @Override
    public void onListeningCanceled() {
        try {
            dialogueFlowButton.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
            dialogueFlowButton.setBackgroundResource(R.drawable.roundbutton3);
        } catch (Exception e) {
            Log.w("DialogueFlow Error", "" + e);
        }
    }

    private void speakOut(String text) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !TextUtils.isEmpty(text)) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }


    @Override
    public void onListeningFinished() {
        try {
            dialogueFlowButton.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
            dialogueFlowButton.setBackgroundResource(R.drawable.roundedbutton);
        } catch (Exception e) {
            Log.w("DialogueFlow Error", "" + e);
        }
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale loc = new Locale("en", "IN");
            tts.setLanguage(loc);
            tts.setPitch(1.0f);
            tts.setSpeechRate(1.0f);
        }
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {

    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }


}
