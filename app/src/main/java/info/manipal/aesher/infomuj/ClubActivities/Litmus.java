package info.manipal.aesher.infomuj.ClubActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Adapters.Clubs.LitmusAdapter;
import info.manipal.aesher.infomuj.Constants.UtilityFunctions;
import info.manipal.aesher.infomuj.R;
import info.manipal.aesher.infomuj.Threads.Interface.LitmusInterface;
import info.manipal.aesher.infomuj.Threads.Interface.LitmusPoemWrapper;
import info.manipal.aesher.infomuj.Threads.LitmusPoem;

public class Litmus extends AppCompatActivity {

    UtilityFunctions utilityFunctions;

    @BindView(R.id.poemRecycler)
    RecyclerView poemRecycler;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    LitmusPoemWrapper litmusPoemWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utilityFunctions = new UtilityFunctions(this);
        utilityFunctions.goFullScreen();
        setContentView(R.layout.activity_litmus);

        ButterKnife.bind(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        poemRecycler.setLayoutManager(linearLayoutManager);
        poemRecycler.setNestedScrollingEnabled(false);

        litmusPoemWrapper = new LitmusPoemWrapper(null, null);
        LitmusAdapter litmusAdapter = new LitmusAdapter(litmusPoemWrapper);
        poemRecycler.setAdapter(litmusAdapter);

        new LitmusPoem(new LitmusInterface() {
            @Override
            public void processFinished(LitmusPoemWrapper output) {

                progressBar.setVisibility(View.GONE);

                LitmusAdapter litmusAdapter = new LitmusAdapter(output);
                poemRecycler.setAdapter(litmusAdapter);

                poemRecycler.animate().alpha(1).setDuration(500);

            }
        }).execute();
    }
}
