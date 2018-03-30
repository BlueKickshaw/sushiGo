package Game;

import javafx.scene.image.ImageView;

import java.util.Vector;


public class Turn implements Runnable {

    private Player player;
    private long timeLimit = (long) 5e9;
    private Vector<ImageView> playerCardImages;

    Turn(Player player, Vector<ImageView> playerCardImages) {
        this.player = player;
        this.playerCardImages = playerCardImages;
    }


    public void run() {
        long startTime = System.nanoTime();
        while (!player.isFirstCardPicked && (System.nanoTime() - startTime < timeLimit)) {

        }
        if (player.isFirstCardPicked) {
            disableButtons();
            System.out.println(player.getSelectedCard());
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getSelectedCard()));
        } else if (!player.isFirstCardPicked) {
            disableButtons();
            player.getHand().addCard(player.getRotatingHand().selectAndRemoveCard(player.getRotatingHand().getCard(0)));
            System.out.println("Automatically selected card: " + player.getHand().getCard(player.getHand().getCards().size() - 1));
        }
        player.setSelectedCard(null);
        player.isFirstCardPicked = false;
    }

    private void disableButtons() {
        for (ImageView buttonClick : playerCardImages) {
            buttonClick.setDisable(true);
        }
    }

}

