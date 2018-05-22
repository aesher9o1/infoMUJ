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

    MainPage fragmentMainPage;
    DialogueFlow dialogueFlow;
    View alertLayout;

    CustomAlertDialog customDialog;

    SharedPreferences prefs = null;

    private TextToSpeech tts;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,v);
        scaleDown(QRCode,1);
        scaleDown(DialogueFlow,2);
        scaleDown(developerButton,3);
        scaleDown(gotoWeb,4);

        tts = new TextToSpeech(getContext(),this);

        gestureDetector = new GestureDetector(getActivity(), new SingleTapConfirm());


        return v;
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if(requestCode!=0&&data!=null&&result!=null) {
            LayoutInflater inflater = getLayoutInflater();
            alertLayout = inflater.inflate(R.layout.dialogue_qr, null);
            customDialog = new CustomAlertDialog(getContext(), alertLayout);
            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            customDialog.show();
            try{
                final int index = Integer.valueOf(result.getContents());
                SharedPreferences prefs = getContext().getSharedPreferences("com.manipal.infomuj", MODE_PRIVATE);
                String branches = prefs.getString("branches", null);
                final String branchArray[] = branches.split("#");


                //Todo Vidhyanshu Jain

                if(((index - 1) * 6) + 3<branchArray.length){
                    if(branchArray[((index - 1) * 6) + 3].contains("http")){
                        final StringBuilder infoFill = new StringBuilder();
                        infoFill.append("<div style=\"text-align:center;font-size:18px;\"><b>" + branchArray[((index - 1) * 6) + 1] + "</b></div><br>");


                        final String url = branchArray[((index - 1) * 6) + 3].trim();
                        newTask myTask = new newTask();
                        myTask.execute(url,infoFill.toString(), branchArray[((index - 1) * 6) + 2]);

                        Log.w("Index",url);

                    }
                    else {
                        StringBuilder infoFill = new StringBuilder();
                        infoFill.append("<div style=\"text-align:center;font-size:18px;\"><b>" + branchArray[((index - 1) * 6) + 1] + "</b></div><br>");
                        infoFill.append(branchArray[((index - 1) * 6) + 3]);
                        customDialog.SetWebView(infoFill.toString());
                        speakOut(branchArray[((index - 1) * 6) + 2]);
                    }
                }
                else {
                    Toast.makeText(getContext(),"Invalid QR Code Scanned",Toast.LENGTH_SHORT).show();
                    customDialog.dismiss();
                }

            }
            catch (NumberFormatException e){
                Toast.makeText(getContext(),"Invalid QR Code Scanned",Toast.LENGTH_SHORT).show();
                customDialog.dismiss();
            }



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
        // Don't forget to shutdown tts!
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


    class  newTask extends AsyncTask<String, Void, String> {




        String info= "";
        String shortOverview="";

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            shortOverview = strings[2];
            try {
                info+=strings[1];
                Document doc = Jsoup.connect(url).get();
                Element text = doc.select("div[id=Overview]").first();
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
