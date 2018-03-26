package Game;

import Cards.Card;
//import com.sun.xml.internal.bind.v2.TODO;


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

    public void setRoundPoints(int points) {
        this.roundPoints += points;
    }

    public void setTotalPoints(int points) {
        this.totalPoints += points;
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
        this.setDumplingCount(0);
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
}
