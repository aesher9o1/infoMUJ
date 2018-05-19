package info.manipal.aesher.infomuj.Fragment;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
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



import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.manipal.aesher.infomuj.Developers;
import info.manipal.aesher.infomuj.MainActivity;
import info.manipal.aesher.infomuj.R;

public class MainPage extends Fragment {



    @BindView(R.id.QRCode)
    LinearLayout QRCode;

    @BindView(R.id.DialogueFlow)
    LinearLayout DialogueFlow;

    @BindView(R.id.developerButton)
    TextView developerButton;

    @BindView(R.id.webredirect)
    LinearLayout gotoWeb;


    private GestureDetector gestureDetector;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,v);
        scaleDown(QRCode,1);
        scaleDown(DialogueFlow,2);
        scaleDown(developerButton,3);
        scaleDown(gotoWeb,4);

        gestureDetector = new GestureDetector(getActivity(), new SingleTapConfirm());


        return v;
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);



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

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }



}
