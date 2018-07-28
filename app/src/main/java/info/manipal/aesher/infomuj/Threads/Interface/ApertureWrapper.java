package info.manipal.aesher.infomuj.Threads.Interface;

import java.util.List;

public class ApertureWrapper {

    List<String> Url;
    List<String> Name;

    public  ApertureWrapper(List<String> Url, List<String> Name){
        this.Url = Url;
        this.Name = Name;
    }

    public List<String> getName() {
        return Name;
    }

    public List<String> getUrl() {
        return Url;
    }
}
