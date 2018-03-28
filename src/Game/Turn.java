package Game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;



import java.util.Vector;

public class Turn implements Runnable {

    private Player primaryPlayer;
    private Button btn;
    private ImageView imgView;
    private double timeLimit = 5e9;

    Turn(Player player) {
        this.primaryPlayer = player;
    }


    public void run() {
        long startTime = System.nanoTime();
        while (!primaryPlayer.firstCardPicked && System.nanoTime() - startTime < timeLimit){

        }
    }

}

