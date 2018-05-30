package info.manipal.aesher.infomuj.Fragment;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.manipal.aesher.infomuj.Adapters.CustomAlertDialog;
import info.manipal.aesher.infomuj.Constants.firebaseData;
import info.manipal.aesher.infomuj.Developers;
import info.manipal.aesher.infomuj.MainActivity;
import info.manipal.aesher.infomuj.R;

import static android.content.Context.MODE_PRIVATE;

public class MainPage extends Fragment implements TextToSpeech.OnInitListener{



    @BindView(R.id.QRCode)
    LinearLayout QRCode;

    @BindView(R.id.DialogueFlow)
    LinearLayout DialogueFlow;

    @BindView(R.id.developerButton)
    TextView developerButton;

    @BindView(R.id.webredirect)
    LinearLayout gotoWeb;


    private GestureDetector gestureDetector;

    View alertLayout;

    CustomAlertDialog customDialog;



    private TextToSpeech tts;
    DatabaseReference ref;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        scaleDown(QRCode, 1);
        scaleDown(DialogueFlow, 2);
        scaleDown(developerButton, 3);
        scaleDown(gotoWeb, 4);

        tts = new TextToSpeech(getContext(), this);

        gestureDetector = new GestureDetector(getActivity(), new SingleTapConfirm());
        ref = FirebaseDatabase.getInstance().getReference("manipal");
        ref.keepSynced(true);


        return v;


    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if(requestCode!=0&&data!=null&&result!=null) {
            ref.child(""+result.getContents()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        firebaseData firebaseData = dataSnapshot.getValue(info.manipal.aesher.infomuj.Constants.firebaseData.class);
                        LayoutInflater inflater = getLayoutInflater();
                        alertLayout = inflater.inflate(R.layout.dialogue_qr, null);
                        customDialog = new CustomAlertDialog(getContext(), alertLayout);
                        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        customDialog.show();

                        try{
                            if(!firebaseData.getDivId().equals("NaN")){
                                final StringBuilder BranchName = new StringBuilder();
                                BranchName.append("<div style=\"text-align:center;font-size:18px;\"><b>" + firebaseData.getName() + "</b></div><br>");
                                newTask myTask = new newTask();
                                Log.w("Aashis",firebaseData.getLongOverview());
                                Log.w("Aashis",firebaseData.getShortOverview());
                                myTask.execute(firebaseData.getLongOverview(),BranchName.toString(),firebaseData.getShortOverview(),firebaseData.getDivId());
                            }
                            else {
                                final StringBuilder LongInfo = new StringBuilder();
                                LongInfo.append("<div style=\"text-align:center;font-size:18px;\"><b>" + firebaseData.getName() + "</b></div><br>");
                                LongInfo.append(firebaseData.getLongOverview());
                                customDialog.SetWebView(LongInfo.toString());
                                speakOut(firebaseData.getShortOverview());

                            }

                        }
                        catch (Exception e){
                            Log.w("Firebase Infalting",""+e);
                        }
                    }
                    else {
                        Toast.makeText(getContext(),"Invalid QR Code",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) { }
            });


        }

    }




    public void scaleDown(final View V, final int id){

        V.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (gestureDetector.onTouchEvent(motionEvent)) {
                    switch (id){
                        case 1:
                            IntentIntegrator.forSupportFragment(MainPage.this).initiateScan();
                            break;

                        case 2:
                            gotoMUJBot();
                            break;
                        case 3:
                            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),developerButton,developerButton.getTransitionName()).toBundle();
                            startActivity(new Intent(getActivity(), Developers.class),bundle);
                            break;
                        case 4:
                            startActivity(  new Intent( Intent.ACTION_VIEW , Uri.parse( "https://jaipur.manipal.edu/" ) ) );
                            break;
                    }
                    return true;
                }

                else{
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(V,
                                    "scaleX", 0.9f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(V,
                                    "scaleY", 0.9f);
                            scaleDownX.setDuration(300);
                            scaleDownY.setDuration(300);

                            AnimatorSet scaleDown = new AnimatorSet();
                            scaleDown.play(scaleDownX).with(scaleDownY);

                            scaleDown.start();


                            break;

                        case MotionEvent.ACTION_UP:
                            ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(
                                    V, "scaleX", 1f);
                            ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(
                                    V, "scaleY", 1f);
                            scaleDownX2.setDuration(300);
                            scaleDownY2.setDuration(300);

                            AnimatorSet scaleDown2 = new AnimatorSet();
                            scaleDown2.play(scaleDownX2).with(scaleDownY2);

                            scaleDown2.start();
                            switch (id){
                                case 1:
                                    IntentIntegrator.forSupportFragment(MainPage.this).initiateScan();
                                    break;

                                case 2:
                                    gotoMUJBot();
                                    break;
                                case 3:startActivity(new Intent(getActivity(), Developers.class));
                                    break;
                                case 4: startActivity(  new Intent( Intent.ACTION_VIEW , Uri.parse( "https://jaipur.manipal.edu/" ) ) );
                                break;
                            }
                            break;
                    }
                }

                return true;
            }
        });

    }




    public  void gotoMUJBot(){
        DialogueFlow dialogueFlow = new DialogueFlow();
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.mainFragment, dialogueFlow,"Chatbot")
                .commit();
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            Locale loc = new Locale ("en", "IN");
            tts.setLanguage(loc);
            tts.setPitch(1.0f);
            tts.setSpeechRate(1.0f);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }

    private void speakOut(final String text) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
            }
        };handler.postDelayed(runnable,1000);

    }



    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }


    public class  newTask extends AsyncTask<String, Void, String> {

        String info= "";
        String shortOverview="";

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            shortOverview = strings[2];
            try {
                info+=strings[1];
                Document doc = Jsoup.connect(url).get();
                Log.w("Website","Website to fetch "+strings[3]);
                Log.w("Website",""+doc);
                String CssQuery = "div[id="+strings[3]+"]";
                Element text = doc.select(CssQuery).first();
                Log.w("Website",""+text);
                info += ""+text;
                return info;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(info!=null){
                customDialog.SetWebView(info);
                speakOut(shortOverview);

            }




        }

    }


}
