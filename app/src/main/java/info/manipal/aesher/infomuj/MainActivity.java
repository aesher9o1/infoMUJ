package info.manipal.aesher.infomuj;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import info.manipal.aesher.infomuj.Adapters.CustomAlertDialog;
import info.manipal.aesher.infomuj.Constants.NavMenuMain;
import info.manipal.aesher.infomuj.Fragment.DialogueFlow;
import info.manipal.aesher.infomuj.Fragment.MainPage;


public class MainActivity extends AppCompatActivity {


    MainPage fragmentMainPage;
    DialogueFlow dialogueFlow;
    View alertLayout;

      AlertDialog dialog;
    CustomAlertDialog customDialog;


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

    @BindView(R.id.college)
    RecyclerView college;

    @OnClick(R.id.hamburger)
    public void slideUpToggle(){
        sothreeLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    String appPackageName= "";

    TextView heading;
    ImageView blinkingHeart;
    Boolean colored = false;
    @OnClick(R.id.notifications)
    public void infalteDialogue(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.option_dialogue, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        blinkingHeart = alertLayout.findViewById(R.id.heart);
        blink();


        LinearLayout rate,mujfiles,feedback;

        rate = alertLayout.findViewById(R.id.rate);
        mujfiles = alertLayout.findViewById(R.id.muj);
        feedback = alertLayout.findViewById(R.id.feedback);


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPlay(appPackageName);
            }
        });

        mujfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPlay("in.siddharthjaidka.mujfiles");
            }
        });


        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:aashiskumar986@gmail.com")));
            }
        });

    }



    public void OpenPlay(String url){

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + url)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + url)));
        }
    }


    public void initialiseDialog(){
        LayoutInflater inflater = getLayoutInflater();
        alertLayout = inflater.inflate(R.layout.dialogue_qr, null);
//        customDialog.setView(alertLayout);
        customDialog = new CustomAlertDialog(this, alertLayout);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    private void blink(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!colored){
                            blinkingHeart.animate().scaleX(0.4f).scaleY(0.4f).setDuration(500).start();
                            colored= true;
                        }
                        else {
                            blinkingHeart.animate().scaleX(1f).scaleY(1f).setDuration(500).start();
                            colored= false;
                        }
                        if(dialog.isShowing()){
                            blink();
                        }

                    }
                });
            }
        }).start();
    }

    ViewGroup transitionsContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        isCameraPermission();
        replaceFrag();
        initialiseDialog();


        contentFillers = new NavMenuMain();



        //preperaing enginering
        List<ClubProvider> provider = new ArrayList<>();

        ClubAdapter adapter = new ClubAdapter(getApplicationContext(),provider,customDialog, "BRANCH");
        LinearLayoutManager layoutManager =  new GridLayoutManager(getApplicationContext(), 4);
        engineering.setLayoutManager(layoutManager);
        engineering.setHasFixedSize(true);
        engineering.setAdapter(adapter);
        engineering.setNestedScrollingEnabled(false);
        contentFillers.engineering(provider);




        //preperaing clubs
        List<ClubProvider> providerClub = new ArrayList<>();
        ClubAdapter adapterClub = new ClubAdapter(getApplicationContext(),providerClub,customDialog, "CLUB");
        layoutManager =  new GridLayoutManager(getApplicationContext(), 4);
        clubs.setLayoutManager(layoutManager);
        clubs.setHasFixedSize(true);
        clubs.setAdapter(adapterClub);
        clubs.setNestedScrollingEnabled(false);
        contentFillers.clubs(providerClub);


        //prepering college
        List<ClubProvider> providerCollege = new ArrayList<>();
        ClubAdapter adapterCollege = new ClubAdapter(getApplicationContext(),providerCollege,customDialog, "COLLEGE");
        layoutManager =  new GridLayoutManager(getApplicationContext(), 4);
        college.setLayoutManager(layoutManager);
        college.setHasFixedSize(true);
        college.setAdapter(adapterCollege);
        college.setNestedScrollingEnabled(false);
        contentFillers.college(providerCollege);


       transitionsContainer = findViewById(R.id.container);
       heading = transitionsContainer.findViewById(R.id.heading);
       heading.setText(textWhenMainScreen);

        dialogueFlow = (DialogueFlow) getSupportFragmentManager().findFragmentByTag("Chatbot");
        contactListner();








    }

    public void contactListner(){
        dialogueFlowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,PlacesActivity.class);
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,dialogueFlowButton,dialogueFlowButton.getTransitionName()).toBundle();
                startActivity(i,bundle);
            }
        });
    }


    public void reset(){

        if(heading.getText().toString().equals(textWhenDialogueScreen)){
            com.transitionseverywhere.TransitionManager.beginDelayedTransition(transitionsContainer,
                    new ChangeText().setChangeBehavior(3));
                    heading.setText(textWhenMainScreen);
                    replaceButton();
                    contactListner();

        }


    }

    public void replaceButton(){
        dialogueFlowButton.animate().scaleX(0f).scaleY(0f).setDuration(300).start();
        Handler delayForButtonChange = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dialogueFlowButton.setText("Popular Places");
                dialogueFlowButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplicationContext(), R.drawable.navigation), null, null, null);
                dialogueFlowButton.animate().scaleX(1f).scaleY(1f).setDuration(300).start();
            }
        }; delayForButtonChange.postDelayed(runnable,301);
        dialogueFlowButton.setOnTouchListener(null);
        dialogueFlowButton.setOnClickListener(null);

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

        }
        else
            super.onBackPressed();

    }






    public void isCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }

    }




}
