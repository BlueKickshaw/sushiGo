package Cards;


public class Pudding extends Card {

    private String imagePath = "/Game/CardImages/Pudding.jpg";

    public Pudding() {
        super("Pudding");
    }

    public Pudding(String name) {
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }
}
