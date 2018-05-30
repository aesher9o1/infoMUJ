package info.manipal.aesher.infomuj;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Fragment.Places;
import info.manipal.aesher.infomuj.Fragment.WayPoints;

public class PlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        replaceFrag();
    }


    public void replaceFrag(){

        Places fragmentMainPage = new Places();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.listfragments, fragmentMainPage,"ListPlaces");
        ft.commit();
    }




}
