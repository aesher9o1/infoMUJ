package info.manipal.aesher.infomuj.Threads.Interface;

import java.util.List;

public class LitmusPoemWrapper {

    private List<String>  title, poems;

    public LitmusPoemWrapper(List<String> title, List<String> poems){
        this.title = title;
        this.poems = poems;
    }

    public List<String> getPoems() {
        return poems;
    }

    public List<String> getTitle() {
        return title;
    }
}
