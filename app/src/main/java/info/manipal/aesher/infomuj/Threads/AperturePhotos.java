package info.manipal.aesher.infomuj.Threads;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import info.manipal.aesher.infomuj.Threads.Interface.ApertureInterface;
import info.manipal.aesher.infomuj.Threads.Interface.ApertureWrapper;

public class AperturePhotos extends AsyncTask<String, Void, ApertureWrapper> {

    private List<String> Names, URL;

    private ApertureInterface apertureInterface;

    public AperturePhotos(ApertureInterface apertureInterface) {
        this.apertureInterface = apertureInterface;
    }

    @Override
    protected ApertureWrapper doInBackground(String... strings) {

        Names = new ArrayList<>();
        URL = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("http://www.aperturemuj.com/index.php").get();
            Elements text = doc.select("img[src]");
            Elements names = doc.select("p");


            for (int i = 3; i < text.size() - 1; i++) {
                String url = text.get(i).toString().replace("<img src=\"", "");
                url = url.replace("\" alt=\"#\">", "");
                url = url.replace("amp;", "");
                URL.add(url);
            }

            for (int i = 1; i < names.size() - 5; i++)
                Names.add(names.get(i).html());


            return new ApertureWrapper(URL, Names);


        } catch (Exception e) {
            Log.w("Aperture", "" + e);
        }


        return null;
    }

    @Override
    protected void onPostExecute(ApertureWrapper apertureWrapper) {
        super.onPostExecute(apertureWrapper);
        apertureInterface.processFinished(apertureWrapper);
    }
}
