package Cards;


public class EggNigiri extends Card {

    private String imagePath = "/Game/CardImages/Egg_Nigiri.jpg";

    public EggNigiri() {
        super("Egg Nigiri");
    }

    public EggNigiri(String name) {
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }
}
