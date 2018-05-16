package info.manipal.aesher.infomuj;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.manipal.aesher.infomuj.Fragment.DialogueFlow;
import info.manipal.aesher.infomuj.Fragment.MainPage;


public class MainActivity extends AppCompatActivity {


    MainPage fragmentMainPage;


    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sothreeLayout;


    @OnClick(R.id.hamburger)
    public void slideUpToggle(){
        sothreeLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }


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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        isCameraPermission();
        isSoundPermission();
        replaceFrag();

    }



    public void replaceFrag(){

        fragmentMainPage = new MainPage();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFragment, fragmentMainPage);
        ft.commit();
    }


    @Override
    public void onBackPressed() {
        if(sothreeLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED) || sothreeLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.ANCHORED))
            sothreeLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
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
