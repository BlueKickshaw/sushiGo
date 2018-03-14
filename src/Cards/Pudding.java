package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Pudding extends Card{


    public Pudding() throws IOException {
        super("Pudding", ImageIO.read(new File("../CardImages/Pudding.jpg")));
    }
}
