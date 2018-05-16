package info.manipal.aesher.infomuj.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
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
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.manipal.aesher.infomuj.MainActivity;
import info.manipal.aesher.infomuj.R;

public class MainPage extends Fragment {



    @BindView(R.id.QRCode)
    LinearLayout QRCode;

    @BindView(R.id.DialogueFlow)
    LinearLayout DialogueFlow;

    @BindView(R.id.developerButton)
    TextView developerButton;

    private GestureDetector gestureDetector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,v);
        scaleDown(QRCode,1);
        scaleDown(DialogueFlow,2);
        scaleDown(developerButton,3);

        gestureDetector = new GestureDetector(getActivity(), new SingleTapConfirm());


        return v;
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if(result!=null&&Integer.valueOf(result.getContents())<100){
            //ToDo Vidhyanshu Jain

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
                            Log.w("Aashis",""+2);
                            break;
                        case 3:Log.w("Aashis",""+2);
                            break;
                    }
                    return true;
                }

                else{
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(V,
                                    "scaleX", 0.95f);
                            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(V,
                                    "scaleY", 0.95f);
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
                                    Log.w("Aashis",""+2);
                                    break;
                                case 3:Log.w("Aashis",""+2);
                                    break;
                            }
                            break;
                    }
                }

                return true;
            }
        });

    }


    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
}
