package Cards;


public class Sashimi extends Card {

    private String imagePath = "/CardImages/Sashimi.jpg";

    public Sashimi() {
        super("Sashimi");
    }

    public Sashimi(String name) {
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }
}
