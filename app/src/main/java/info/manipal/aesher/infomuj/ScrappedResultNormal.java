package info.manipal.aesher.infomuj;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.transitionseverywhere.ChangeText;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Constants.UtilityFunctions;
import info.manipal.aesher.infomuj.Constants.firebaseData;
import info.manipal.aesher.infomuj.Threads.FetchingAsync;
import info.manipal.aesher.infomuj.Threads.Interface.FetchingInterface;

public class ScrappedResultNormal extends AppCompatActivity {


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

    UtilityFunctions utilityFunctions;

    ViewGroup transactionsContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utilityFunctions = new UtilityFunctions(this);
        utilityFunctions.goFullScreen();
        setContentView(R.layout.activity_scrapped_result_normal);
        ButterKnife.bind(this);

        transactionsContainer = findViewById(R.id.container);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("manipal");


        ref.child("" + getIntent().getStringExtra("Index")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    firebaseData firebaseData = dataSnapshot.getValue(info.manipal.aesher.infomuj.Constants.firebaseData.class);
                    if (!firebaseData.getDivId().equals("NaN")) {
                        final StringBuilder BranchName = new StringBuilder();
                        BranchName.append("<div style=\"text-align:center;font-size:18px;\"><b>").append(firebaseData.getName()).append("</b></div><br>");
                        new FetchingAsync(new FetchingInterface() {
                            @Override
                            public void processFinished(String output) {
                                try {
                                    loadDataToWeb(output);
                                } catch (Exception e) {
                                    Log.w("Website", "" + e);
                                }
                            }
                        }).execute(firebaseData.getLongOverview(), BranchName.toString(), firebaseData.getShortOverview(), firebaseData.getDivId());
                    } else
                        loadDataToWeb("<div style=\"text-align:center;font-size:18px;\"><b>" + firebaseData.getName() + "</b></div><br>" + firebaseData.getLongOverview());


                } else utilityFunctions.Toast("Invalid Data");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void loadDataToWeb(final String s) {
        progressBar.setVisibility(View.GONE);

        utilityFunctions.setRevealLayout(RevealLayout);
        com.transitionseverywhere.TransitionManager.beginDelayedTransition(transactionsContainer,
                new ChangeText().setChangeBehavior(3));


        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                heading.setText(getIntent().getStringExtra("Name"));
                webView.loadData(s, "text/html", null);
                RevealLayout.animate().alpha(0).setDuration(500);
                body.setVisibility(View.VISIBLE);
            }
        };
        handler.postDelayed(runnable, 600);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
