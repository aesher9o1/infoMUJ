package info.manipal.aesher.infomuj;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import com.transitionseverywhere.ChangeText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.manipal.aesher.infomuj.Adapters.ClubAdapter;
import info.manipal.aesher.infomuj.Adapters.ClubProvider;
import info.manipal.aesher.infomuj.Adapters.CustomAlertDialog;
import info.manipal.aesher.infomuj.Constants.NavMenuMain;
import info.manipal.aesher.infomuj.Constants.Users;
import info.manipal.aesher.infomuj.Fragment.DialogueFlow;
import info.manipal.aesher.infomuj.Fragment.MainPage;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    public static final String textWhenMainScreen = "Info Muj";
    public static final String textWhenDialogueScreen = "Welcome To MUJ ";
    public static final String textWhenVRScreen = "Manipal 360";

    FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;

    MainPage fragmentMainPage;
    DialogueFlow dialogueFlow;
    View alertLayout;
    AlertDialog dialog;
    CustomAlertDialog customDialog;
    NavMenuMain contentFillers;
    TextView heading;
    ImageView blinkingHeart;
    Boolean colored = false;
    ViewGroup transitionsContainer;
    LinearLayoutManager layoutManager;


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

    @BindView(R.id.policies)
    RecyclerView policies;

    @BindView(R.id.background)
    ImageView back;


    int numberOfCards = 4;
    String[] cardNames = {"engineering", "club", "faq", "policies"};


    @OnClick(R.id.hamburger)
    public void slideUpToggle() {
        sothreeLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }


    @OnClick(R.id.notifications)
    public void infalteDialogue() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.option_dialogue, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        dialog = alert.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        blinkingHeart = alertLayout.findViewById(R.id.heart);
        blink();


        LinearLayout rate, mujfiles, feedback;

        rate = alertLayout.findViewById(R.id.rate);
        mujfiles = alertLayout.findViewById(R.id.muj);
        feedback = alertLayout.findViewById(R.id.feedback);


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPlay("info.manipal.aesher.infomuj");
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        isCameraPermission();
        /* Sets the main fragment of the page*/
        replaceFrag();
        Picasso.get().load(R.drawable.back).into(back);
        /* Initializes the main dialogue for use*/
        initialiseDialog();


        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null)
            firebaseSignIn();
        else
            Log.w("Firebase",mAuth.getCurrentUser().getUid());




        /*Setup of the navigation Menu */
        contentFillers = new NavMenuMain();
        layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        for (int i = 1; i <= numberOfCards; i++)
            prepare(cardNames[i - 1], i);


        transitionsContainer = findViewById(R.id.container);
        heading = transitionsContainer.findViewById(R.id.heading);
        heading.setText(textWhenMainScreen);
        dialogueFlow = (DialogueFlow) getSupportFragmentManager().findFragmentByTag("Chatbot");
        contactListener();
    }


    public void OpenPlay(String url) {

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + url)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + url)));
        }
    }


    public void initialiseDialog() {
        LayoutInflater inflater = getLayoutInflater();
        alertLayout = inflater.inflate(R.layout.dialogue_qr, null);
        customDialog = new CustomAlertDialog(this, alertLayout);
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    private void firebaseSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 9001);
    }


    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception ignored) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!colored) {
                            blinkingHeart.animate().scaleX(0.4f).scaleY(0.4f).setDuration(500).start();
                            colored = true;
                        } else {
                            blinkingHeart.animate().scaleX(1f).scaleY(1f).setDuration(500).start();
                            colored = false;
                        }
                        if (dialog.isShowing()) {
                            blink();
                        }

                    }
                });
            }
        }).start();
    }


    public void contactListener() {
        dialogueFlowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PlacesActivity.class);
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, dialogueFlowButton, dialogueFlowButton.getTransitionName()).toBundle();
                startActivity(i, bundle);
            }
        });
    }


    public void reset(Boolean isButtonReplaceneNeeded) {
        if (heading.getText().toString().equals(textWhenDialogueScreen) || heading.getText().toString().equals(textWhenVRScreen)) {
            com.transitionseverywhere.TransitionManager.beginDelayedTransition(transitionsContainer,
                    new ChangeText().setChangeBehavior(3));
            heading.setText(textWhenMainScreen);
            if (isButtonReplaceneNeeded)
                replaceButton();
            contactListener();

        }

    }


    public void replaceButton() {
        dialogueFlowButton.animate().scaleX(0f).scaleY(0f).setDuration(300).start();
        Handler delayForButtonChange = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dialogueFlowButton.setText("Popular Places");
                dialogueFlowButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplicationContext(), R.drawable.navigation), null, null, null);
                dialogueFlowButton.animate().scaleX(1f).scaleY(1f).setDuration(300).start();
            }
        };
        delayForButtonChange.postDelayed(runnable, 301);
        dialogueFlowButton.setOnTouchListener(null);
        dialogueFlowButton.setOnClickListener(null);

    }


    public void replaceFrag() {
        fragmentMainPage = new MainPage();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        dialogueFlowButton.setBackgroundResource(R.drawable.roundedbutton);
        ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.mainFragment, fragmentMainPage, "Dashboard");
        ft.commit();
    }


    public void isCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }

    }

    void prepare(final String category, final int number) {
        final RecyclerView[] cardViews = {engineering, clubs, college, policies};
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ClubProvider> provider = new ArrayList<>();
                ClubAdapter adapterCollege = new ClubAdapter(MainActivity.this, provider, customDialog, category);
                layoutManager = new GridLayoutManager(getApplicationContext(), 4);
                cardViews[number - 1].setLayoutManager(layoutManager);
                cardViews[number - 1].setAdapter(adapterCollege);
                cardViews[number - 1].setNestedScrollingEnabled(false);


                switch (number) {
                    case 1:
                        contentFillers.engineering(provider);
                        break;
                    case 2:
                        contentFillers.clubs(provider);
                        break;
                    case 3:
                        contentFillers.college(provider);
                        break;
                    case 4:
                        contentFillers.policies(provider);
                        break;
                }

            }
        }).run();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/" + mAuth.getUid());
                            databaseReference.setValue(new Users(Objects.requireNonNull(acct).getGivenName(), acct.getEmail()));
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (sothreeLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED) || sothreeLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sothreeLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else if (heading.getText().toString().equals(textWhenDialogueScreen)) {
            replaceFrag();
            reset(true);
        } else if (heading.getText().toString().equals(textWhenVRScreen)) {
            replaceFrag();
            reset(false);
        } else
            super.onBackPressed();

    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(sothreeLayout, "Authentication Requires an Internet Connection", Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                Log.w("Firebase","Signining");
            } catch (ApiException e) {
                Log.w("FirebaseAuth", "Failed " + e);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }
}
