package info.manipal.aesher.infomuj.Constants;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Objects;

public class UtilityFunctions {

    private Context mContext;

    public UtilityFunctions(Context mContext) {
        this.mContext = mContext;
    }


    public String developerMessage = "<html><head><body><center><h3>Message From Developers</h3></center><br><p text-align:justify><u><b>Sorry,This club is currently not in association with infoMUJ</b></u><br><br>We bring to you a humble efford to unite platforms to maintain tasks and send important notifications regarding events, club etc on a single app.<br>The app would allow club chairs to:<li>Seperate members into teams [executive committee, core,working etc..] to roll out tasks and notifications</li><li>Similarly members will be able to claim the task and update the process of the task </li><br><b>The dashboard will only be available to people registered to the club and data integrity is ensured. </b><br>Similarly we have provided non members feature to register to notification channels of various clubs so that they can receive notifications about the upcoming events of the clubs.<br><br>This feature is currently in development and we request the clubs to send the details of the registration fee, email ID to contact and if they would like to have a dashboard on the app. Further queries and data submissions can directly be addressed to the developer<br>We also welcome suggestions from all through the same channel.<br><br><b>Regards<br>The development community</b></p>";


    /**
     * Get Mat Color
     */
    public int getMatColor() {
        int returnColor = Color.BLACK;
        int arrayId = mContext.getResources().getIdentifier("mdcolor_" + "500", "array", Objects.requireNonNull(mContext).getPackageName());

        if (arrayId != 0) {
            TypedArray colors = mContext.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

    /**
     * Circular Reveal
     */

    public void setRevealLayout(LinearLayout layout) {
        int cx = layout.getWidth();
        int cy = layout.getHeight();
        float finalRadius = (float) Math.hypot(cx, cy) + 100;
        Animator anim = ViewAnimationUtils.createCircularReveal(layout, cx / 2, cy / 2, 0, finalRadius).setDuration(550);
        layout.setVisibility(View.VISIBLE);
        anim.start();
    }


    /**
     * Full Screen
     */
    public void goFullScreen() {
        ((Activity) mContext).requestWindowFeature(Window.FEATURE_NO_TITLE);
        ((Activity) mContext).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


    /**
     * Toast
     */
    public void Toast(String toShow) {
        Toast.makeText(mContext, toShow, Toast.LENGTH_SHORT).show();
    }


    /**
     * Scale
     */

    public void scale(View view, long delay) {
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate()
                .scaleX(1)
                .scaleY(1)
                .setDuration(500)
                .setStartDelay(delay)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }
}
