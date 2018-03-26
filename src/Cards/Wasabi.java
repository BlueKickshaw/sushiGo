package Cards;


public class Wasabi extends Card {

    private String imagePath = "/CardImages/SquidNigiri.jpg";

    public Wasabi(String name) {
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }
}
