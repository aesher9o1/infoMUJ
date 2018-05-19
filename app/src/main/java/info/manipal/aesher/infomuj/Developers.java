package info.manipal.aesher.infomuj;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Developers extends AppCompatActivity {

    @BindView(R.id.viduujain)
    CircleImageView vidhyanshuJain;

    @BindView(R.id.vidhyanshu)
    LinearLayout viduName;


    @BindView(R.id.aashiskumar)
    CircleImageView aashiskumar;

    @BindView(R.id.aashisInfo)
    LinearLayout aashisInfo;

    @BindView(R.id.viduContainer)
    LinearLayout viduContainer;


    @BindView(R.id.ashContainer)
    LinearLayout ashContainer;

    @OnClick(R.id.viduGit)
            public void ViduGit(){
        Github("https://github.com/vidu171");
    }


    @OnClick(R.id.viduLink)
            public void ViduLink(){
        linkdln("https://www.linkedin.com/in/vidhyanshu-jain-153876157");
    }

    @OnClick(R.id.viduEmail)
            public void Email(){
        Google("viduuj@gmail.com");
    }


    @OnClick(R.id.ashGit)
    public void ashGit(){
        Github("https://github.com/aesher9o1");
    }


    @OnClick(R.id.ashLink)
    public void ashLink(){
        linkdln("https://www.linkedin.com/in/aashiskumar/");
    }

    @OnClick(R.id.ashEmail)
    public void ashEmail(){
        Google("aashiskumar986@gmail.com");
    }



    Boolean viduVisible, ashVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        ButterKnife.bind(this);
        viduVisible= false;
        ashVisible = false;
        setLocked(vidhyanshuJain);
        setLocked(aashiskumar);

        aashiskumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(ashContainer);
                if(!ashVisible){
                    setUnlocked(aashiskumar);
                    aashisInfo.setVisibility(View.VISIBLE);
                    ashVisible= true;
                }

                else{
                    ashVisible= false;
                    setLocked(aashiskumar);
                    aashisInfo.setVisibility(View.GONE);
                }
            }
        });





        vidhyanshuJain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(viduContainer);
                if(!viduVisible){
                    setUnlocked(vidhyanshuJain);
                    viduName.setVisibility(View.VISIBLE);
                    viduVisible= true;
                }

                else{
                    viduVisible= false;
                    setLocked(vidhyanshuJain);
                    viduName.setVisibility(View.GONE);
                }
            }
        });





    }
    private void Google( String URL){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{  URL});

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Developers.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    private void Github( String URL){
        startActivity(new Intent( Intent.ACTION_VIEW , Uri.parse( URL ) ));
    }


    private void linkdln( String URL){
        startActivity(new Intent( Intent.ACTION_VIEW , Uri.parse( URL ) ));
    }





    public static void  setLocked(CircleImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
        v.setImageAlpha(128);
    }


    public static void  setUnlocked(CircleImageView v)
    {
        v.setColorFilter(null);
        v.setImageAlpha(255);
    }
}
