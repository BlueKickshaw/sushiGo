package Cards;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MakiRoll2 extends Card{
    public MakiRoll2() throws IOException {
        super("Maki Rolls 2", ImageIO.read(new File("\"../CardImages/Maki_Roll_2.jpg\"")));
    }
}
