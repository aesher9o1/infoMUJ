package info.manipal.aesher.infomuj;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.vr.cardboard.FullscreenMode;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VRView extends AppCompatActivity {


    @BindView(R.id.pano_view)
    VrPanoramaView panoramaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GoFullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrview);
        ButterKnife.bind(this);


        try {
            loadPanoImage(getIntent().getStringExtra("imageToLoad"), panoramaView);
        } catch (OutOfMemoryError e) {
            Toast.makeText(this, "Not enough RAM", Toast.LENGTH_SHORT).show();
        }
    }

    public synchronized void loadPanoImage(String URL, VrPanoramaView panoWidgetView) {
        ImageLoaderTask task;
        final VrPanoramaView.Options viewOptions = new VrPanoramaView.Options();
        viewOptions.inputType = VrPanoramaView.Options.TYPE_MONO;
        task = new ImageLoaderTask(panoWidgetView, viewOptions, URL);
        task.execute(Objects.requireNonNull(this).getAssets());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.gc();
    }

    void GoFullScreen() {
        FullscreenMode fullscreenMode = new FullscreenMode(this.getWindow());
        fullscreenMode.goFullscreen();
    }

    @Override
    protected void onPause() {
        super.onPause();
        panoramaView.pauseRendering();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        panoramaView.shutdown();
        System.gc();
    }

    @Override
    protected void onResume() {
        super.onResume();
        panoramaView.resumeRendering();
    }


    static class ImageLoaderTask extends AsyncTask<AssetManager, Void, Bitmap> {

        private static final String TAG = "ImageLoaderTask";
        private final String assetName;
        private final WeakReference<VrPanoramaView> viewReference;
        private final VrPanoramaView.Options viewOptions;
        private WeakReference<Bitmap> lastBitmap = new WeakReference<>(null);
        private String lastName;

        ImageLoaderTask(VrPanoramaView view, VrPanoramaView.Options viewOptions, String assetName) {
            viewReference = new WeakReference<>(view);
            this.viewOptions = viewOptions;
            this.assetName = assetName;
        }

        @Override
        protected Bitmap doInBackground(AssetManager... params) {

            final VrPanoramaView vw = viewReference.get();
            vw.setFullscreenButtonEnabled(false);
            vw.setInfoButtonEnabled(false);


            if (assetName.equals(lastName) && lastBitmap.get() != null) {
                return lastBitmap.get();
            }

            try (FileInputStream fileInputStream = new FileInputStream(assetName)) {
                Bitmap b = BitmapFactory.decodeStream(fileInputStream);
                lastBitmap = new WeakReference<>(b);
                lastName = assetName;
                return b;
            } catch (IOException e) {
                Log.e(TAG, "Could not decode default bitmap: " + e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            final VrPanoramaView vw = viewReference.get();


            if (vw != null && bitmap != null) {
                try {
                    vw.loadImageFromBitmap(bitmap, viewOptions);

                } catch (Exception e) {
                    Log.w("panorama exception", "" + e);
                }

            }
        }
    }


}
