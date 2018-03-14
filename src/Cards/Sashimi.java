package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Sashimi extends Card{


    public Sashimi() throws IOException {
        super("Sashimi", ImageIO.read(new File("../CardImages/Sashimi.jpg")));
    }
}