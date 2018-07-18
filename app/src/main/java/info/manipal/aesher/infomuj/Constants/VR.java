package info.manipal.aesher.infomuj.Constants;

public class VR {
    private String url, place,bgImage,credits;
    private Long size;


    public VR() {
    }

    public VR(String url, String place, Long size,String bgImage,String credits) {
        this.url = url;
        this.place = place;
        this.size = size;
        this.bgImage = bgImage;
        this.credits = credits;
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

    public String getBgImage() { return bgImage; }

    public String getCredits() { return credits; }
}
