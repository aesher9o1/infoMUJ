package info.manipal.aesher.infomuj.Threads.Interface;

import java.util.List;

public class IEEENewsWrapper {

    private List<String> by, Title, URL;
    private long[] time;


    public IEEENewsWrapper(List<String> by, List<String> Title, List<String> URL, long[] time) {
        this.by = by;
        this.Title = Title;
        this.URL = URL;
        this.time = time;
    }


    public long[] getTime() {
        return time;
    }


    public List<String> getBy() {
        return by;
    }


    public List<String> getTitle() {
        return Title;
    }


    public List<String> getURL() {
        return URL;
    }
}
