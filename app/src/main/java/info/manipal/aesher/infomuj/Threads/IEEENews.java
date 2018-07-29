package info.manipal.aesher.infomuj.Threads;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import info.manipal.aesher.infomuj.Threads.Interface.IEEENewsInterface;
import info.manipal.aesher.infomuj.Threads.Interface.IEEENewsWrapper;

public class IEEENews extends AsyncTask<String, Void, IEEENewsWrapper> {

    private IEEENewsInterface ieeeNewsInterface;


    public IEEENews(IEEENewsInterface ieeeNewsInterface) {
        this.ieeeNewsInterface = ieeeNewsInterface;
    }

    @Override
    protected IEEENewsWrapper doInBackground(String... strings) {

        List<String> by = new ArrayList<>();
        List<String> title = new ArrayList<>();
        List<String> URL = new ArrayList<>();
        long[] time = new long[30];

        try {

            String fetchedNewsArray = Jsoup.connect("https://hacker-news.firebaseio.com/v0/topstories.json")
                    .ignoreContentType(true).get().body().text();

            JSONArray newsArray = new JSONArray(fetchedNewsArray);

            for (int i = 0; i < 20; i++) {

                JSONObject newObject = new JSONObject(
                        Jsoup.connect("https://hacker-news.firebaseio.com/v0/item/" + newsArray.get(i) + ".json")
                                .ignoreContentType(true).get().body().text()
                );

                by.add(newObject.getString("by"));
                title.add(newObject.getString("title"));
                URL.add(newObject.getString("url"));
                time[i] = newObject.getLong("time");


            }

            return new IEEENewsWrapper(by, title, URL, time);

        } catch (Exception e) {
            Log.w("IEEE Fetching Exception", "" + e);
        }


        return null;
    }

    @Override
    protected void onPostExecute(IEEENewsWrapper aVoid) {
        super.onPostExecute(aVoid);
        Log.w("IEEE Data Size", "" + aVoid.getTitle().size());
        ieeeNewsInterface.processFinished(aVoid);
    }
}
