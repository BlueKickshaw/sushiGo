package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Wasabi extends Card{
    private boolean used;

    public Wasabi() throws IOException {
        super("Wasabi", ImageIO.read(new File("../CardImages/Wasabi.jpg")));
        used = false;
    }
}
