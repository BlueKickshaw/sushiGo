package Cards;

public class MakiRoll3 extends Card {

    private String imagePath = "/Game/CardImages/Maki_Roll_3.jpg";
    private int makiCount = 3;


    public MakiRoll3() {
        super("Maki Roll");
    }

    public MakiRoll3(String name) {
        super(name);
    }

    public int getMakiCount() {
        return makiCount;
    }

    public String getImagePath() {
        return imagePath;
    }
}
