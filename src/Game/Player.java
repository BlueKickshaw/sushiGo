package Game;

import Cards.*;

import java.util.Vector;

public class Player implements Runnable {

    private String name;
    private String IP;
    private Hand hand;
    private Hand rotatingHand;
    private int roundPoints;
    private int totalPoints;
    private int puddingCount;
    private int dumplingCount;
    private int makiCount;
    public volatile Boolean firstCardPicked = false;
    public volatile Boolean secondCardPicked = false;
    public Card selectedCard;


    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {

        this.selectedCard = selectedCard;
    }


    public Player(String name, String IP) {
        this.name = name;
        this.IP = IP;
        this.hand = new Hand();
        this.rotatingHand = null;
        this.roundPoints = 0;
        this.totalPoints = 0;
        this.puddingCount = 0;
        this.dumplingCount = 0;
        this.makiCount = 0;
    }

    public void setHand(Vector<Card> cards) {
        this.hand = new Hand(cards);
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

    public void clearHand() {
        this.hand.clearCards();
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

    public void clearRoundPoints() {
        this.roundPoints = 0;
    }

    public void setRoundPoints(int points) {
        this.roundPoints = points;
    }

    public void addRoundPoints(int points) {
        this.roundPoints += points;
    }


    public void addRoundPointsToTotal() {
        this.totalPoints += this.roundPoints;
    }

    public void addTotalPoints(int points) {
        this.totalPoints += points;
    }

    public void setTotalPoints(int points) {
        this.totalPoints = points;
    }

    public void setPuddingCount(int puddingCount) {
        this.puddingCount = puddingCount;
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

    public void chooseCard(Card c) {
        rotatingHand.removeCard(c);
        hand.addCard(c);
    }

    public void calculateDumplingPoints() {
        if (this.dumplingCount >= 5) {
            this.roundPoints += 15;
        } else if (this.dumplingCount == 4) {
            this.roundPoints += 10;
        } else if (this.dumplingCount == 3) {
            this.roundPoints += 6;
        } else if (this.dumplingCount == 2) {
            this.roundPoints += 3;
        } else if (this.dumplingCount == 1) {
            this.roundPoints += 1;
        }
        this.setDumplingCount(0);
    }

    public void calculateNigiriPoints() {//points from wasabi are calculated separately
        for (Card card : hand.getCards()) {
            if (card instanceof EggNigiri) {
                this.roundPoints += 1;

            } else if (card instanceof SalmonNigiri) {
                this.roundPoints += 2;

            } else if (card instanceof SquidNigiri) {
                this.roundPoints += 3;
            }
        }
    }

    public void calculateSashimiPoints() {
        int count = 0;
        for (Card card : hand.getCards()) {
            if (card instanceof Sashimi) {
                count += 1;
            }
        }
        this.roundPoints += (count / 3) * 10;
    }

    public void calculateTempuraPoints() {
        int count = 0;
        for (Card card : hand.getCards()) {
            if (card instanceof Tempura) {
                count += 1;
            }
        }
        this.roundPoints += (count / 2) * 5;
    }

    public void calculateWasabiPoints() {//the base points from nigirs are calculated separately
        int wasabis = 0;//tracks number of unused wasabis
        for (Card card : hand.getCards()) {
            if (card instanceof Wasabi) {
                wasabis += 1;
            } else if (wasabis > 0) {
                if (card instanceof EggNigiri) {
                    this.roundPoints += 2;
                    wasabis--;
                } else if (card instanceof SalmonNigiri) {
                    this.roundPoints += 4;
                    wasabis--;
                } else if (card instanceof SquidNigiri) {
                    this.roundPoints += 6;
                    wasabis--;
                }
            }
        }

    }

    public void updatePuddingCount() {
        for (Card card : hand.getCards()) {
            if (card instanceof Pudding) {
                puddingCount++;
            }
        }
    }

    public void updateMakiCount() {
        for (Card card : hand.getCards()) {
            if (card instanceof MakiRoll1) {
                makiCount++;
            }
            else if (card instanceof MakiRoll2) {
                makiCount += 2;
            }
            else if (card instanceof MakiRoll3) {
                makiCount += 3;
            }
        }
    }

    public void updateDumplingCount() {
        for (Card card : hand.getCards()) {
            if (card instanceof Dumpling) {
                dumplingCount++;
            }
        }
    }

    public String toString() {
        return ("Name: " + this.name +
                " IP: " + this.IP +
                " Round Points: " + this.roundPoints
                + " Total Points: " + this.totalPoints);
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
                hand.addCard(rotatingHand.selectAndRemoveCard(selectedCard));
                if (selectedCard instanceof Dumpling) {
                    this.dumplingCount++;
                }
                cardConfirmed = true;
                break;
            }

        }
        if (!cardConfirmed) {

        }

    }

    public boolean hasChopsticks(){
        for (Card card :
                this.hand.getCards()) {
            if(card instanceof Chopsticks){return true;}
        }
        return false;
    }

    public void useChopsticks() {
        Card selectedCard = null;
        boolean cardConfirmed = false;
        long startTime = System.nanoTime();
        // wait 10 seconds (1e10) for a player to take their turn
        while (!cardConfirmed && System.nanoTime() - startTime < 1e10) {

            if (selectedCard != null && cardConfirmed) {
                rotatingHand.addCard(new Chopsticks());
                hand.addCard(rotatingHand.selectAndRemoveCard(selectedCard));

                if (selectedCard instanceof Dumpling) {
                    this.dumplingCount++;
                }
                cardConfirmed = true;
                break;
            }

        }
        if (!cardConfirmed) {

        }

    }

    public void useChopsticksTest(Card c) {
                rotatingHand.addCard(new Chopsticks());
                hand.addCard(rotatingHand.selectAndRemoveCard(c));

                if (c instanceof Dumpling) {
                    this.dumplingCount++;
                }

    }


}
