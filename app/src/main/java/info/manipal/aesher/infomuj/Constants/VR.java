package info.manipal.aesher.infomuj.Constants;

public class VR {
    String url, place;
    Long size;

    public VR() {
    }

    public VR(String url, String place, Long size) {
        this.url = url;
        this.place = place;
        this.size = size;
    }

    public String getPlace() {
        return place;
    }

    public String getUrl() {
        return url;
    }

    public Long getSize() {
        return size;
    }
}
