package info.manipal.aesher.infomuj.Threads;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import info.manipal.aesher.infomuj.Threads.Interface.LitmusInterface;
import info.manipal.aesher.infomuj.Threads.Interface.LitmusPoemWrapper;

public class LitmusPoem extends AsyncTask<String, Void, LitmusPoemWrapper> {

    private List<String> Listpoems, ListTitle;

    private LitmusInterface litmusInterface;


    public LitmusPoem(LitmusInterface litmusInterface) {
        this.litmusInterface = litmusInterface;
    }

    @Override
    protected LitmusPoemWrapper doInBackground(String... strings) {

        try {


            String fetchedPoems = Jsoup.connect("http://poetrydb.org/author/William%20Shakespeare")
                    .ignoreContentType(true).get().body().text();


            JSONArray poems = new JSONArray(fetchedPoems);


            for (int i = 0; i < poems.length(); i++) {
                JSONObject poem = new JSONObject(poems.get(i).toString());
                JSONArray finalPoem = new JSONArray(poem.get("lines").toString());


                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < finalPoem.length(); j++) {
                    stringBuilder.append(finalPoem.get(j));
                    stringBuilder.append("\n");
                }

                Listpoems.add(stringBuilder.toString());
                ListTitle.add(poem.get("title").toString());

                stringBuilder.setLength(0);
            }


            Log.w("Litmus", "" + ListTitle.size());
            return new LitmusPoemWrapper(ListTitle, Listpoems);
        } catch (Exception e) {
            Log.w("Litmus Exception", "" + e);
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Listpoems = new ArrayList<>();
        ListTitle = new ArrayList<>();
    }

    @Override
    protected void onPostExecute(LitmusPoemWrapper strings) {
        super.onPostExecute(strings);
        litmusInterface.processFinished(strings);

    }
}
