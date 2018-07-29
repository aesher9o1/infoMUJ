package info.manipal.aesher.infomuj.ClubActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Adapters.Clubs.IEEEAdapter;
import info.manipal.aesher.infomuj.Constants.UtilityFunctions;
import info.manipal.aesher.infomuj.R;
import info.manipal.aesher.infomuj.Threads.IEEENews;
import info.manipal.aesher.infomuj.Threads.Interface.IEEENewsInterface;
import info.manipal.aesher.infomuj.Threads.Interface.IEEENewsWrapper;

public class IEEE extends AppCompatActivity {


    UtilityFunctions utilityFunctions;

    @BindView(R.id.newsRecycler)
    RecyclerView newsReycler;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    IEEENewsWrapper newsWrapperLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utilityFunctions = new UtilityFunctions(this);
        utilityFunctions.goFullScreen();
        setContentView(R.layout.activity_ieee);

        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        newsReycler.setLayoutManager(linearLayoutManager);
        newsReycler.setNestedScrollingEnabled(false);

        newsWrapperLocal = new IEEENewsWrapper(null, null, null, null);
        IEEEAdapter ieeeAdapter = new IEEEAdapter(newsWrapperLocal, getApplicationContext());
        newsReycler.setAdapter(ieeeAdapter);

        new IEEENews(new IEEENewsInterface() {
            @Override
            public void processFinished(IEEENewsWrapper newsWrapper) {

                progressBar.setVisibility(View.GONE);

                IEEEAdapter ieeeAdapter = new IEEEAdapter(newsWrapper, getApplicationContext());
                newsReycler.setAdapter(ieeeAdapter);

                newsReycler.animate().alpha(1).setDuration(500);


            }
        }).execute();

    }
}
