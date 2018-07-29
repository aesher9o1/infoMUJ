package info.manipal.aesher.infomuj.ClubActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Adapters.Clubs.ApertureAdapter;
import info.manipal.aesher.infomuj.Constants.UtilityFunctions;
import info.manipal.aesher.infomuj.R;
import info.manipal.aesher.infomuj.Threads.AperturePhotos;
import info.manipal.aesher.infomuj.Threads.Interface.ApertureInterface;
import info.manipal.aesher.infomuj.Threads.Interface.ApertureWrapper;

public class Aperture extends AppCompatActivity {

    UtilityFunctions utilityFunctions;

    @BindView(R.id.photoRecycler)
    RecyclerView recyclerView;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    ApertureAdapter apertureAdapter;

    ApertureWrapper apertureWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utilityFunctions = new UtilityFunctions(this);
        utilityFunctions.goFullScreen();
        setContentView(R.layout.activity_aperture);

        ButterKnife.bind(this);


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        apertureWrapper = new ApertureWrapper(null, null);
        apertureAdapter = new ApertureAdapter(getApplicationContext(), apertureWrapper);
        recyclerView.setAdapter(apertureAdapter);


        ButterKnife.bind(this);

        new AperturePhotos(new ApertureInterface() {
            @Override
            public void processFinished(ApertureWrapper apertureWrapper) {
                progressBar.setVisibility(View.GONE);

                ApertureAdapter apertureAdapter = new ApertureAdapter(getApplicationContext(), apertureWrapper);
                recyclerView.setAdapter(apertureAdapter);

                recyclerView.animate().alpha(1).setDuration(500);
            }
        }).execute();
    }
}
