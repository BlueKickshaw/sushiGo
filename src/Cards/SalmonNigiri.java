package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SalmonNigiri extends Card{


    public SalmonNigiri() throws IOException {
        super("Salmon Nigiri", ImageIO.read(new File("../CardImages/Salmon_Nigiri.jpg")));
    }
}
