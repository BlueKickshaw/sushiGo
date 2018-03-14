package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MakiRoll3 extends Card{
    public MakiRoll3() throws IOException {
        super("Maki Rolls 3", ImageIO.read(new File("\"../CardImages/Maki_Roll_3.jpg\"")));
    }
}
