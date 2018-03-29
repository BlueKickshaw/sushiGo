package Game;

public class Turn implements Runnable {

    private Player player;
    private long timeLimit = (long) 5e9;

    Turn(Player player) {
        this.player = player;
    }


    public void run() {
        long startTime = System.nanoTime();
        System.out.println(this.player.getRotatingHand());
        while (!this.player.firstCardPicked && (System.nanoTime() - startTime < timeLimit)) {

        }
        if (this.player.firstCardPicked) {
            System.out.println(this.player.getSelectedCard());
            this.player.getHand().addCard(this.player.getRotatingHand().selectAndRemoveCard(this.player.getSelectedCard()));
        } else if (!player.firstCardPicked) {
            this.player.getHand().addCard(this.player.getRotatingHand().selectAndRemoveCard(this.player.getRotatingHand().getCard(0)));
            System.out.println("Automatically selected card: " + this.player.getHand().getCard(this.player.getHand().getCards().size() - 1));
        }
        this.player.setSelectedCard(null);
        this.player.firstCardPicked = false;
    }

}

