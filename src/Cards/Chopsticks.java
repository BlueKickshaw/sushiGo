package Cards;

//Ran out of time and couldn't get these working
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
