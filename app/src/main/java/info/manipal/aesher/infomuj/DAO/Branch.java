package info.manipal.aesher.infomuj.DAO;

public class Branch {

    public String name;
    public String shortOverview;
    public String longOverview;
    public String img_url_1;
    public String img_url_2;
    public String img_url_3;

    public Branch(String mName, String mShortOverView, String mLongOverview, String mImg_url_1, String mImg_url_2, String mImg_url_3) {
        name = mName;
        shortOverview = mShortOverView;
        longOverview = mLongOverview;
        img_url_1 = mImg_url_1;
        img_url_2 = mImg_url_2;
        img_url_3 = mImg_url_3;
    }

    public Branch() {

    }
}
