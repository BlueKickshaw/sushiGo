package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SquidNigiri extends Card{


    public SquidNigiri() throws IOException {
        super("Squid Nigiri", ImageIO.read(new File("../CardImages/Squid_Nigiri.jpg")));
    }
}
