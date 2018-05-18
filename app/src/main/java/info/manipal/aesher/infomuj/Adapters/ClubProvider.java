package info.manipal.aesher.infomuj.Adapters;

public class ClubProvider {
    private String ButtonText,LayoutTitle,WebsiteLink;
    private boolean isMoreInfo;
    private  int index;

    public  ClubProvider(String ButtonText, String LayoutTitle, String WebsiteLink, boolean isMoreInfo,int index){
        this.ButtonText = ButtonText;
        this.LayoutTitle = LayoutTitle;
        this.WebsiteLink = WebsiteLink;
        this.isMoreInfo = isMoreInfo;
        this.index= index;
    }
    public String getButtonText(){
        return ButtonText;
    }
    public String getLayoutTitle(){
        return LayoutTitle;
    }

    public String getWebsiteLink(){
        return  WebsiteLink;
    }

    public  boolean getisMoreInfo(){
        return isMoreInfo;
    }

    public int getIndex(){
        return index;
    }
}
