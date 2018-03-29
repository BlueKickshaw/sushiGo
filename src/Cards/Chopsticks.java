package Cards;


public class Chopsticks extends Card {

    private String imagePath = "/Game/CardImages/Chopsticks.jpg";

    public Chopsticks() {
        super("Chopsticks");
    }

    public Chopsticks(String name) {
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }
}
