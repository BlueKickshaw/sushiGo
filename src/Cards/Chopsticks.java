package Cards;


public class Chopsticks extends Card {

    private String imagePath = "/CardImages/Chopsticks.jpg";

    public Chopsticks(String name) {
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }
}
