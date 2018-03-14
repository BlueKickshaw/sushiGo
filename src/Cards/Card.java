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


    private BufferedImage cardImage;//TODO make this static
    private String name;

    public Card(String name, BufferedImage cardImage) {
        this.name = name;
        this.cardImage = cardImage;
    }

    public class Chopsticks extends Card{


        public Chopsticks() throws IOException {
            super("Chopsticks", ImageIO.read(new File("../CardImages/Chopsticks.jpg")));
        }
    }


    public class Dumpling extends Card{


        public Dumpling() throws IOException {
            super("Dumpling", ImageIO.read(new File("../CardImages/Dumpling.jpg")));
        }
    }


    public class EggNigiri extends Card{


        public EggNigiri() throws IOException {
            super("Egg Nigiri", ImageIO.read(new File("../CardImages/Egg_Nigiri.jpg")));
        }
    }


    public class MakiRoll extends Card{
        int num;

        public MakiRoll(int num) throws IOException {
            super("Maki Rolls", null);
            this.num = num;
            if(num == 1){
                cardImage = ImageIO.read(new File("../CardImages/Maki_Roll_1.jpg"));
            }
            else if(num == 2){
                cardImage = ImageIO.read(new File("../CardImages/Maki_Roll_2.jpg"));
            }
            else if(num == 3){
                cardImage = ImageIO.read(new File("../CardImages/Maki_Roll_3.jpg"));
            }
            else{
                System.err.print("Invalid Maki Roll Number must be 1-3\nValue passed was " + num);
            }
        }
    }


    public class Pudding extends Card{


        public Pudding() throws IOException {
            super("Pudding", ImageIO.read(new File("../CardImages/Pudding.jpg")));
        }
    }


    public class SalmonNigiri extends Card{


        public SalmonNigiri() throws IOException {
            super("Salmon Nigiri", ImageIO.read(new File("../CardImages/Salmon_Nigiri.jpg")));
        }
    }


    public class Sashimi extends Card{


        public Sashimi() throws IOException {
            super("Sashimi", ImageIO.read(new File("../CardImages/Sashimi.jpg")));
        }
    }


    public class SquidNigiri extends Card{


        public SquidNigiri() throws IOException {
            super("Squid Nigiri", ImageIO.read(new File("../CardImages/Squid_Nigiri.jpg")));
        }
    }


    public class Tempura extends Card{


        public Tempura() throws IOException {
            super("Tempura", ImageIO.read(new File("../CardImages/Tempura.jpg")));
        }
    }


    public class Wasabi extends Card{
        private boolean used;

        public Wasabi() throws IOException {
            super("Wasabi", ImageIO.read(new File("../CardImages/Wasabi.jpg")));
            used = false;
        }
    }
}


