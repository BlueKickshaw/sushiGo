package Cards;


public class Dumpling extends Card{

    private String imagePath = "/CardImages/Dumpling.jpg";

    public Dumpling(String name){
        super(name);
    }

    public String getImagePath() {
        return imagePath;
    }
}
