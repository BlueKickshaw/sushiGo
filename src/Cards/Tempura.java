package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Tempura extends Card{


    public Tempura() throws IOException {
        super("Tempura", ImageIO.read(new File("../CardImages/Tempura.jpg")));
    }
}
