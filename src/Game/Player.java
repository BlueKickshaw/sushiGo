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

    public void setHand(Vector<Card> cards){
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

    public void calculateNigiriPoints() {
        for (Card card: hand.getCards()) {
            if (card instanceof EggNigiri) {
                this.roundPoints += 1;

            } else if (card instanceof SalmonNigiri) {
                this.roundPoints += 2;

            } else if (card instanceof  SquidNigiri) {
                this.roundPoints += 3;
            }
        }
    }

    public void calculateSashimiPoints() {
        int count = 0;
        for (Card card:hand.getCards()){
            if(card instanceof Sashimi){
                count += 1;
            }
        }
        this.roundPoints += (count/3)*10;
    }

    public void calculateTempuraPoints() {
        int count = 0;
        for (Card card:hand.getCards()){
            if(card instanceof Tempura){
                count += 1;
            }
        }
        this.roundPoints += (count/2)*5;
    }

    public String toString() {
        return ("Name: " + this.name +
                " IP: " + this.IP +
                " MakiCount: " + this.makiCount +
                " DumplingCount: " + this.dumplingCount
                + " Points: " + this.roundPoints);
    }


    public int getWasabiPoints(){
        int points =0;
        int wasabis = 0;//tracks number of unused wasabis
        for (Card card:hand.getCards()){
            if(card.getName().equals("Wasabi")){
                wasabis += 1;
            }else if(wasabis>1) {
                if(card.getName().equals("Egg Nigiri")) {
                    points+=2;
                    wasabis--;
                } else if(card.getName().equals("Salmon Nigiri")) {
                    points+=4;
                    wasabis--;
                } else if(card.getName().equals("Squid Nigiri")) {
                    points+=6;
                    wasabis--;
                }
            }
        }
        return points;
    }
}
