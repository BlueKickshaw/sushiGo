package Game;

import javafx.scene.control.Button;

import java.util.Vector;

public class Turn implements Runnable {

    private Player primaryPlayer;
    private Vector<Button> screenButtons;

    Turn(Player primaryPlayer) {
        this.screenButtons = screenButtons;
        this.primaryPlayer = primaryPlayer;
    }


    public void run() {
        while (primaryPlayer.firstCardPicked == false){

        }
        System.out.println("True set, loop exited");


    }

}

