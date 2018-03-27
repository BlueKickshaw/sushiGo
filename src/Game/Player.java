package Game;

import Cards.*;
//import com.sun.xml.internal.bind.v2.TODO;

import java.util.Vector;


public class Player {

    private String name;
    private String IP;
    private Hand hand;
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

    public Hand getPlayerHand() {
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

    public void clearRoundPoints() {this.roundPoints = 0;}

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

    public void updatePuddingCount(){
        for (Card card: hand.getCards()) {
            if(card instanceof Pudding){
                puddingCount++;
            }
        }
    }

    public String toString() {
        return ("Name: " + this.name +
                " IP: " + this.IP +
                " MakiCount: " + this.makiCount +
                " DumplingCount: " + this.dumplingCount +
                " PuddingCounnt: " + this.puddingCount +
                " Round Points: " + this.roundPoints +
                " Total Points: " + this.totalPoints);
    }

}
