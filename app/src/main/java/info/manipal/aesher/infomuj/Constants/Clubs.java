package info.manipal.aesher.infomuj.Constants;

public class Clubs {

    private String  imageUrl,notification,registration,contact;

    Clubs(){}

    Clubs(String imageUrl,String notification,String registration,String contact){
        this.imageUrl = imageUrl;
        this.notification = notification;
        this.registration = registration;
        this.contact = contact;
    }


    public String getContact() {
        return contact;
    }

    public String getRegistration() {
        return registration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNotification() {
        return notification;
    }
}
