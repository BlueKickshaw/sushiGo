package Game;

import Cards.Card;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.Vector;


public class Player {

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    private String name, IP;
    private Hand playerHand, rotatingHand;
    private int points = 0;
    private int pudding = 0;

    public Player(int numOfPlayers){
        playerHand = new Hand (8, true);//TODO handsize not hard coded
        rotatingHand = new Hand(8, false);
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getRotatingHand() {
        return rotatingHand;
    }

    public int getPoints() {
        return points;
    }

    public int getPudding() {
        return pudding;
    }


    public void chooseCard(Card c){
        rotatingHand.removeCard(c);
        playerHand.addCard(c);
    }

    public int dumplingPoints() {
        return 0;
        //TODO
    }

    public int makiRollPoints(Vector<Player> playerList) {
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
