package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MakiRoll1 extends Card{
    public MakiRoll1() throws IOException {
        super("Maki Rolls 1", ImageIO.read(new File("\"../CardImages/Maki_Roll_1.jpg\"")));
    }
}
