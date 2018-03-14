package Cards;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Card{
    private static BufferedImage cardBack;//this is static since all cards have the same back

    {
        try {
            cardBack = ImageIO.read(new File("../CardImages/Cardback.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected BufferedImage cardImage;
    protected String name;

    public Card(String name, BufferedImage cardImage) {
        this.name = name;
        this.cardImage = cardImage;
    }

    public class Chopsticks extends Card{


        public Chopsticks() throws IOException {
            super("Chop Sticks", ImageIO.read(new File("../CardImages/Cardback.jpg")));
        }
    }

    public class Dumpling extends Card{


        public Dumpling() throws IOException {
            super("Dumpling", ImageIO.read(new File("../CardImages/Cardback.jpg")));
        }
    }

    public class EggNigiri extends Card{


        public EggNigiri() throws IOException {
            super("Egg Nigiri", ImageIO.read(new File("../CardImages/Cardback.jpg")));
        }
    }
    public class MakiRoll extends Card{
        int num;

        public MakiRoll() throws IOException {
            super("Maki Rolls", ImageIO.read(new File("../CardImages/Cardback.jpg")));
            this.num = num;
        }
    }
    public class Pudding extends Card{


        public Pudding() throws IOException {
            super("Chop Sticks", ImageIO.read(new File("../CardImages/Cardback.jpg")));
        }
    }
    public class SalmonNigiri extends Card{


        public SalmonNigiri() throws IOException {
            super("Salmon Nigiri", ImageIO.read(new File("../CardImages/Cardback.jpg")));
        }
    }
    public class Sashimi extends Card{


        public Sashimi() throws IOException {
            super("Sashimi", ImageIO.read(new File("../CardImages/Cardback.jpg")));
        }
    }
    public class SquidNigiri extends Card{


        public SquidNigiri() throws IOException {
            super("Squid Nigiri", ImageIO.read(new File("../CardImages/Cardback.jpg")));
        }
    }
    public class Tempura extends Card{


        public Tempura() throws IOException {
            super("Tempura", ImageIO.read(new File("../CardImages/Cardback.jpg")));
        }
    }
    public class Wasabi extends Card{
        private boolean used;

        public Wasabi() throws IOException {
            super("Wasabi", ImageIO.read(new File("../CardImages/Cardback.jpg")));
            used = false;
        }
    }
}


