package Game;

import Cards.Card;
//import com.sun.xml.internal.bind.v2.TODO;

import java.util.Vector;


public class Player {

    private String name;
    private String IP;
    private Hand playerHand, rotatingHand;
    private int roundPoints = 0;
    private int totalPoints = 0;
    private int pudding = 0;
    private int makiCount = 0;

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
        return playerHand;
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

    public int getPudding() {
        return pudding;
    }


    public void chooseCard(Card c) {
        rotatingHand.removeCard(c);
        playerHand.addCard(c);
    }

    public int dumplingPoints() {
        return 0;
        //TODO
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
}
