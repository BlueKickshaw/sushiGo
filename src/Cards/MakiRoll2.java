package Cards;

public class MakiRoll2 extends Card{

    private String imagePath = "/CardImages/Maki_Roll_2.jpg";
    private int makiCount = 2;


    public MakiRoll2(String name){
        super(name);
    }

    public int getMakiCount() {
        return makiCount;
    }

    public String getImagePath() {
        return imagePath;
    }
}
