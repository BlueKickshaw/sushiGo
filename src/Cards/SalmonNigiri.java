package Cards;


public class SalmonNigiri extends Card {

    private String imagePath = "/Game/CardImages/Salmon_Nigiri.jpg";

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
