package Cards;

public class MakiRoll1 extends Card {

    private String imagePath = "/Game/CardImages/Maki_Roll_1.jpg";
    private int makiCount = 1;


    public MakiRoll1() {
        super("Maki Roll");
    }

    public MakiRoll1(String name) {
        super(name);
    }

    public int getMakiCount() {
        return makiCount;
    }

    public String getImagePath() {

        return imagePath;
    }




}
