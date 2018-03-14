package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Chopsticks extends Card{


    public Chopsticks() throws IOException {
        super("Chopsticks", ImageIO.read(new File("../CardImages/Chopsticks.jpg")));
    }
}
