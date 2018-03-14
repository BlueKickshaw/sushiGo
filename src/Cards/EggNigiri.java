package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EggNigiri extends Card{


    public EggNigiri() throws IOException {
        super("Egg Nigiri", ImageIO.read(new File("../CardImages/Egg_Nigiri.jpg")));
    }
}
