package Cards;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Card{
//    private static BufferedImage cardBack;//this is static since all cards have the same back
//
//    {
//        try {
//            cardBack = ImageIO.read(new File("../CardImages/Cardback.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private BufferedImage cardImage;//TODO make this static
    private String name;
//
//    public Card(String name, BufferedImage cardImage) {
//        this.name = name;
//        this.cardImage = cardImage;
//    }
    public Card(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}


