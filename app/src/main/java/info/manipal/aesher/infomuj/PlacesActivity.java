package info.manipal.aesher.infomuj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import info.manipal.aesher.infomuj.Fragment.Places;

public class PlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        replaceFrag();
    }


    public void replaceFrag() {
        Places fragmentMainPage = new Places();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.listfragments, fragmentMainPage);
        ft.commit();
    }


}
