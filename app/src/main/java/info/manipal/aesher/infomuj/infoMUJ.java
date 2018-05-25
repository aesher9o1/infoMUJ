package info.manipal.aesher.infomuj;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class infoMUJ extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
