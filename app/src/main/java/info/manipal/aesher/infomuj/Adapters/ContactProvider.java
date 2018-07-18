package info.manipal.aesher.infomuj.Adapters;

public class ContactProvider {
    private String Name, Number;
    private int image;

    public ContactProvider(String Name, String Number, int image) {
        this.Name = Name;
        this.Number = Number;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public int getImage() {
        return image;
    }

    public String getNumber() {
        return Number;
    }
}
