package Cards;


public class SquidNigiri extends Card {

    private String imagePath = "/Game/CardImages/Squid_Nigiri.jpg";

    public SquidNigiri() {
        super("Squid Nigiri");
    }

    public SquidNigiri(String name) {
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }
}
