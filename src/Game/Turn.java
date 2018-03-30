package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Vector;


public class Turn implements Runnable {

    private Player player;
    private long timeLimit = (long) 5e9;
    private Vector<ImageView> playerCardImages;

    Turn(Player player,Vector<ImageView> playerCardImages) {

        this.player = player;
        this.playerCardImages = playerCardImages;
    }


    public void run() {
        long startTime = System.nanoTime();
        System.out.println(player.getRotatingHand());
        while (!player.firstCardPicked && (System.nanoTime() - startTime < timeLimit)) {

        }
        if (player.firstCardPicked) {
            disableButtons();
            System.out.println(player.getSelectedCard());
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getSelectedCard()));
        } else if (!player.firstCardPicked) {
            disableButtons();
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(0)));
            System.out.println("Automatically selected card: " + player.getHand().getCard(player.getHand().getCards().size() - 1));
        }
        player.setSelectedCard(null);
        player.firstCardPicked = false;
    }
    private void disableButtons() {
        for (ImageView buttonClick : playerCardImages) {
            buttonClick.setDisable(true);
        }
    }

}

