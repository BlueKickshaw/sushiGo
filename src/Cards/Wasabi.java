package Cards;


public class Wasabi extends Card {

    private String imagePath = "/Game/CardImages/Wasabi.jpg";
    private boolean isUsed = false;

    public Wasabi() {
        super("Wasabi");
    }

    public Wasabi(String name) {
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

}
