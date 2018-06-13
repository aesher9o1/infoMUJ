package info.manipal.aesher.infomuj.Threads;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import info.manipal.aesher.infomuj.Threads.Interface.FetchingInterface;

public class FetchingAsync extends AsyncTask<String, Void, String> {

    private FetchingInterface fetchingInterface;
    private  String info = "";

    public FetchingAsync(FetchingInterface fetchingInterface) {
        this.fetchingInterface = fetchingInterface;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        try {
            info += strings[1];
            Document doc = Jsoup.connect(url).get();
            Log.w("Website", "Website to fetch " + strings[3]);
            Log.w("Website", "" + doc);
            String CssQuery = "div[id=" + strings[3] + "]";
            Element text = doc.select(CssQuery).first();
            Log.w("Website", "" + text);
            info += "" + text;
            return info;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null)
        fetchingInterface.processFinished(s);


    }
}
