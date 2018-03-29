package Cards;


public class Tempura extends Card {

    private String imagePath = "/Game/CardImages/Tempura.jpg";

    public Tempura() {
        super("Tempura");
    }

    public Tempura(String name) {
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }
}
