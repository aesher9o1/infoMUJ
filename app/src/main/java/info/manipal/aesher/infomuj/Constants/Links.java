package info.manipal.aesher.infomuj.Constants;

public class Links {

    String name,link,icon;

    Links(){}

    public Links (String name, String link, String icon){
        this.name = name;
        this.link = link;
        this.icon = icon;
    }


    public String getLink() {
        return link;
    }

    public String getIcon() {
        return icon;
    }


    public String getName() {
        return name;
    }
}
