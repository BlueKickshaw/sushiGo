package Cards;


public class SalmonNigiri extends Card {

    private String imagePath = "/CardImages/SalmonNigiri.jpg";

    public SalmonNigiri() {
        super("Salmon Nigiri");
    }

    public SalmonNigiri(String name) {
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }
}
