package info.manipal.aesher.infomuj.Constants;

public class firebaseData {

    String longOverview, shortOverview, divId, img1, img2, img3, name;

    public void firebaseData() {
    }




    public void firebaseData(String longOverview, String shortOverview, String divId, String img1, String img2, String img3, String name) {
        this.longOverview = longOverview;
        this.shortOverview = shortOverview;
        this.divId = divId;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.name = name;
    }


    public String getDivId() {
        return divId;
    }

    public String getImg1() {
        return img1;
    }

    public String getImg2() {
        return img2;
    }

    public String getImg3() {
        return img3;
    }

    public String getLongOverview() {
        return longOverview;
    }

    public String getShortOverview() {
        return shortOverview;
    }

    public String getName() {
        return name;
    }
}
