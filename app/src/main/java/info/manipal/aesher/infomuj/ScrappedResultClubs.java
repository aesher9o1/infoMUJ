package info.manipal.aesher.infomuj;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.transitionseverywhere.ChangeText;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.manipal.aesher.infomuj.Constants.Clubs;
import info.manipal.aesher.infomuj.Constants.UtilityFunctions;
import info.manipal.aesher.infomuj.Constants.firebaseData;
import info.manipal.aesher.infomuj.Threads.FetchingAsync;
import info.manipal.aesher.infomuj.Threads.Interface.FetchingInterface;

public class ScrappedResultClubs extends AppCompatActivity {


    @BindView(R.id.revealLayout)
    LinearLayout RevealLayout;

    @BindView(R.id.heading)
    TextView heading;

    @BindView(R.id.body)
    ScrollView body;

    @BindView(R.id.webview)
    WebView webView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.fab_reveal_layout)
    FABRevealLayout fabRevealLayout;

    @BindView(R.id.mainDetails)
    LinearLayout mainDetails;

    @BindView(R.id.contact)
    LinearLayout contact;

    @BindView(R.id.payment)
    LinearLayout payment;

    @BindView(R.id.notifications)
    LinearLayout notifications;

    @BindView(R.id.clubLogo)
    ImageView clubLogo;

    @BindView(R.id.details_registration)
    TextView registration;



    UtilityFunctions utilityFunctions;

    ViewGroup transactionsContainer;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utilityFunctions = new UtilityFunctions(this);
        utilityFunctions.goFullScreen();
        setContentView(R.layout.activity_scrapped_result_clubs);
        ButterKnife.bind(this);

        configureFabReveal();
        transactionsContainer = findViewById(R.id.container);


        mAuth = FirebaseAuth.getInstance();


        loadBottomData();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("manipal");
        ref.child("" + getIntent().getStringExtra("Index")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    firebaseData firebaseData = dataSnapshot.getValue(info.manipal.aesher.infomuj.Constants.firebaseData.class);
                    if(!firebaseData.getDivId().equals("NaN")){
                        final StringBuilder BranchName = new StringBuilder();
                        BranchName.append("<div style=\"text-align:center;font-size:18px;\"><b>").append(firebaseData.getName()).append("</b></div><br>");
                        new FetchingAsync(new FetchingInterface() {
                            @Override
                            public void processFinished(String output) {

                                try{

                                    loadDataToWeb(output);
                                }catch (Exception e){
                                    Log.w("Website",""+e);}
                            }
                        }).execute(firebaseData.getLongOverview(), BranchName.toString(), firebaseData.getShortOverview(), firebaseData.getDivId());
                    }
                    else
                        loadDataToWeb("<div style=\"text-align:center;font-size:18px;\"><b>" + firebaseData.getName() + "</b></div><br>" + firebaseData.getLongOverview());





                } else utilityFunctions.Toast("Invalid Data");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }



    private void prepareBackTransition(final FABRevealLayout fabRevealLayout) {

        utilityFunctions.scale(payment,50);
        utilityFunctions.scale(contact,100);
        utilityFunctions.scale(notifications,150);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabRevealLayout.revealMainView();
            }
        }, 4000);
    }

    private  void configureFabReveal(){
        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {

            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                prepareBackTransition(fabRevealLayout);
            }
        });
    }


    private void loadBottomData(){

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser()!=null){
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.layout_developer_request, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(ScrappedResultClubs.this);
                    alert.setView(alertLayout);
                    AlertDialog dialog = alert.create();
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                   WebView webViewD = dialog.findViewById(R.id.webView);
                    Objects.requireNonNull(webViewD).loadData(utilityFunctions.developerMessage,"text/html", null);
                }
            }
        });


        FirebaseDatabase.getInstance().getReference(getIntent().getStringExtra("Name"))
                                    .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.w("Firebase",""+dataSnapshot);
                final Clubs clubs = dataSnapshot.getValue(Clubs.class);
                Picasso.get().load(Objects.requireNonNull(clubs).getImageUrl()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.mipmap.ic_launcher).into(clubLogo, new Callback() {
                    @Override
                    public void onSuccess() { }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(clubs.getImageUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop() .into(clubLogo);
                    }
                });

                registration.setText(clubs.getRegistration());


                        if(mAuth.getCurrentUser()!=null){
                            contact.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Google(clubs.getContact());
                                }
                            });


                            notifications.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    FirebaseMessaging.getInstance().subscribeToTopic(getIntent().getStringExtra("Name"));
                                    Toast.makeText(getApplicationContext(),"Your are now suscribed to "+getIntent().getStringExtra("Name")+" notification channel",Toast.LENGTH_SHORT).show();


                                }
                            });

                        }else utilityFunctions.Toast("Please sign in to access the function");



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void loadDataToWeb(final String s){
        progressBar.setVisibility(View.GONE);


        utilityFunctions.setRevealLayout(RevealLayout);
        com.transitionseverywhere.TransitionManager.beginDelayedTransition(transactionsContainer,
                new ChangeText().setChangeBehavior(3));



        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                heading.setText(getIntent().getStringExtra("Name"));
                webView.loadData(s,"text/html", null);

                fabRevealLayout.setVisibility(View.VISIBLE);
                RevealLayout.animate().alpha(0).setDuration(500);
                RevealLayout.setVisibility(View.GONE);


                fabRevealLayout.animate().alpha(1).setDuration(300).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        body.animate().alpha(1).setDuration(500);

                    }
                });

            }
        };handler.postDelayed(runnable,800);




    }


    private void Google(String URL) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{URL});

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ScrappedResultClubs.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
