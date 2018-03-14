package sample.Cards;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Card{
    BufferedImage cardBack;

    {
        try {
            cardBack = ImageIO.read(new File("strawberry.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected BufferedImage cardImage;
    protected String name;

    public Card(BufferedImage cardImage, String name) {
        this.cardImage = cardImage;
        this.name = name;
    }

    public class Chopsticks extends Card{


        public Chopsticks(BufferedImage cardImage, String name) {
            super(cardImage, name);
        }
    }

    public class Dumpling extends Card{


        public Dumpling(BufferedImage cardImage, String name) {
            super(cardImage, name);
        }
    }

    public class EggNigiri extends Card{


        public EggNigiri(BufferedImage cardImage, String name) {
            super(cardImage, name);
        }
    }
    public class MakiRoll extends Card{
        int amount;

        public MakiRoll(BufferedImage cardImage, String name, int amount) {
            super(cardImage, name);
            this.amount = amount;
        }
    }
    public class Pudding extends Card{


        public Pudding(BufferedImage cardImage, String name) {
            super(cardImage, name);
        }
    }
    public class SalmonNigiri extends Card{


        public SalmonNigiri(BufferedImage cardImage, String name) {
            super(cardImage, name);
        }
    }
    public class Sashimi extends Card{


        public Sashimi(BufferedImage cardImage, String name) {
            super(cardImage, name);
        }
    }
    public class SquidNigiri extends Card{


        public SquidNigiri(BufferedImage cardImage, String name) {
            super(cardImage, name);
        }
    }
    public class Tempura extends Card{


        public Tempura(BufferedImage cardImage, String name) {
            super(cardImage, name);
        }
    }
    public class Wasabi extends Card{
        private boolean used;

        public Wasabi(BufferedImage cardImage, String name) {
            super(cardImage, name);
            used = false;
        }
    }
}


