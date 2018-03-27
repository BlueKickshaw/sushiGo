package Game;

import Cards.Card;
import Cards.Chopsticks;


public class Player implements Runnable {

    private String name;
    private String IP;
    private Hand hand = new Hand();
    private Hand rotatingHand;
    private int roundPoints;
    private int totalPoints;
    private int puddingCount;
    private int dumplingCount;
    private int makiCount;

    public Player(String name, String IP) {
        this.name = name;
        this.IP = IP;
    }

    public String getName() {
        return name;
    }

    public String getIP() {
        return IP;
    }

    public void setMakiCount(int makiCount) {
        this.makiCount = makiCount;
    }

    public int getMakiCount() {
        return makiCount;
    }

    public Hand getHand() {
        return hand;
    }

    public Hand getRotatingHand() {
        return rotatingHand;
    }

    public int getRoundPoints() {
        return roundPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setRoundPoints(int points) {
        this.roundPoints += points;
    }

    public void setTotalPoints(int points) {
        this.totalPoints += points;
    }

    public int getPuddingCount() {
        return puddingCount;
    }

    public void drawHand(Deck deck, int playerCount) {
        switch (playerCount) {
            case 2:
                this.rotatingHand = new Hand(deck.drawCards(10));
                break;
            case 3:
                this.rotatingHand = new Hand(deck.drawCards(9));
                break;
            case 4:
                this.rotatingHand = new Hand(deck.drawCards(8));
                break;
        }
    }


    public void setDumplingCount(int dumplingCount) {
        this.dumplingCount = dumplingCount;
    }

    public void incrementDumplingCount() {
        this.dumplingCount++;
    }

    public void chooseCard(Card c) {
        rotatingHand.removeCard(c);
        hand.addCard(c);
    }

    public void calculateDumplingPoints() {
        if (this.dumplingCount >= 5) {
            this.setRoundPoints(15);
        } else if (this.dumplingCount == 4) {
            this.setRoundPoints(10);
        } else if (this.dumplingCount == 3) {
            this.setRoundPoints(6);
        } else if (this.dumplingCount == 2) {
            this.setRoundPoints((3));
        } else if (this.dumplingCount == 1) {
            this.setRoundPoints(1);
        }
//        this.setDumplingCount(0);
    }

    public int nigiriPoints() {
        return 0;
        //TODO
    }

    public int sashimiPoints() {
        return 0;
        //TODO
    }

    public int tempuraPoints() {
        return 0;
        //TODO
    }

    public String toString() {
        return ("Name: " + this.name +
                " IP: " + this.IP +
                " MakiCount: " + this.makiCount +
                " DumplingCount: " + this.dumplingCount
                + " Points: " + this.roundPoints);
    }

    @Override
    public void run() {


    }

    private void turn() {
        Card selectedCard = null;
        boolean cardConfirmed = false;
        long startTime = System.nanoTime();
        // wait 40 seconds (4e10) for a player to take their turn (currently 4e9 = 4 seconds for testing)
        while (!cardConfirmed && System.nanoTime() - startTime < 4e9) {

            if (selectedCard != null && cardConfirmed) {
                if (selectedCard instanceof Chopsticks) {
                    this.useChopsticks();
                }else{
                    hand.addCard(rotatingHand.selectAndRemoveCard(selectedCard));
                    switch (selectedCard.getName()){
                        case "Dumpling": this.dumplingCount++;
                    }
                }
                cardConfirmed = true;
                break;
            }

        }
        if (!cardConfirmed) {

        }

    }

    private void useChopsticks() {
        //TODO
    }


}
