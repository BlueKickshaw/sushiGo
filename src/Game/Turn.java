package Game;

import static Game.FourPlayerController.disableButtons;

public class Turn implements Runnable {

    private Player player;
    private long timeLimit = (long) 5e9;

    Turn(Player player) {
        this.player = player;
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

}

