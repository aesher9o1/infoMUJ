package info.manipal.aesher.infomuj;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.transitionseverywhere.ChangeText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.manipal.aesher.infomuj.Adapters.ClubAdapter;
import info.manipal.aesher.infomuj.Adapters.ClubProvider;
import info.manipal.aesher.infomuj.Constants.NavMenuMain;
import info.manipal.aesher.infomuj.Fragment.DialogueFlow;
import info.manipal.aesher.infomuj.Fragment.MainPage;


public class MainActivity extends AppCompatActivity {


    MainPage fragmentMainPage;
    DialogueFlow dialogueFlow;



    NavMenuMain contentFillers;


    public static final String textWhenMainScreen = "Info Muj";
    public static final String textWhenDialogueScreen = "Welcome To MUJ ";

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sothreeLayout;

    @BindView(R.id.contactButton)
    Button dialogueFlowButton;

    @BindView(R.id.engineering)
    RecyclerView engineering;

    @BindView(R.id.clubs)
    RecyclerView clubs;

    @OnClick(R.id.hamburger)
    public void slideUpToggle(){
        sothreeLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    TextView heading;

    @OnClick(R.id.notifications)
    public void infalteDialogue(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialogue_qr, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    ViewGroup transitionsContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        isCameraPermission();
        isSoundPermission();
        replaceFrag();



        contentFillers = new NavMenuMain();


        //preperaing enginering
        List<ClubProvider> provider = new ArrayList<>();
        ClubAdapter adapter = new ClubAdapter(getApplicationContext(),provider);
        LinearLayoutManager layoutManager =  new GridLayoutManager(getApplicationContext(), 4);
        engineering.setLayoutManager(layoutManager);
        engineering.setHasFixedSize(true);
        engineering.setAdapter(adapter);
        engineering.setNestedScrollingEnabled(false);
        contentFillers.engineering(provider);




        //preperaing clubs
        List<ClubProvider> providerClub = new ArrayList<>();
        ClubAdapter adapterClub = new ClubAdapter(getApplicationContext(),providerClub);
        adapterClub = new ClubAdapter(getApplicationContext(),providerClub);
        layoutManager =  new GridLayoutManager(getApplicationContext(), 4);
        clubs.setLayoutManager(layoutManager);
        clubs.setHasFixedSize(true);
        clubs.setAdapter(adapterClub);
        clubs.setNestedScrollingEnabled(false);
        contentFillers.clubs(providerClub);


       transitionsContainer = findViewById(R.id.container);
       heading = transitionsContainer.findViewById(R.id.heading);
       heading.setText(textWhenMainScreen);

        dialogueFlow = (DialogueFlow) getSupportFragmentManager().findFragmentByTag("Chatbot");
    }


    public void reset(){

        if(heading.getText().toString().equals(textWhenDialogueScreen)){
            com.transitionseverywhere.TransitionManager.beginDelayedTransition(transitionsContainer,
                    new ChangeText().setChangeBehavior(3));
                    heading.setText(textWhenMainScreen);
                    replaceButton();
        }


    }

    public void replaceButton(){
        dialogueFlowButton.animate().scaleX(0f).scaleY(0f).setDuration(300).start();
        Handler delayForButtonChange = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dialogueFlowButton.setText("Useful Contacts");
                dialogueFlowButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_call), null, null, null);
                dialogueFlowButton.animate().scaleX(1f).scaleY(1f).setDuration(300).start();
            }
        }; delayForButtonChange.postDelayed(runnable,301);
        dialogueFlowButton.setOnTouchListener(null);
    }


    public void replaceFrag(){

        fragmentMainPage = new MainPage();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.mainFragment, fragmentMainPage,"Dashboard");
        ft.commit();
    }


    @Override
    public void onBackPressed() {
        if(sothreeLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED) || sothreeLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.ANCHORED))
            sothreeLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        else if(heading.getText().toString().equals(textWhenDialogueScreen)){
            replaceFrag();
            reset();
            dialogueFlowButton.setOnClickListener(null);
        }
        else
            super.onBackPressed();

    }




    public void isSoundPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
        }

    }


    public void isCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }

    }



}
