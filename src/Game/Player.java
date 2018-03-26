package Game;

import Cards.*;
//import com.sun.xml.internal.bind.v2.TODO;

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


    public void chooseCard(Card card){
        rotatingHand.removeCard(card);
        playerHand.addCard(card);
    }




    //every dumpling is worth one more point that the last
    //points 1, 3, 6, 10, 15
    public int getDumplingPoints() {
        int points=0;
        int count=1;
        for (Card card: playerHand.getCards()) {
            if(card.getName().equals("Dumpling")){
                points += count;
                count += 1;
            }
        }
        if(points>15){
            return 15;
        }
        return points;
    }

    @SuppressWarnings("Duplicates")
    public int getMakiRollPoints(Vector<Player> playerList) {//TODO change the way maki roll class is created
        int points =0;
        int mostRolls = -1;
        int secondMostRolls = -1;
        Vector<Player> mostRollPlayers = new Vector<>();
        Vector<Player> secondMostRollPlayers = new Vector<>();
        for (Player player :playerList) {
            int rolls = 0;
            for (Card card :player.getPlayerHand().getCards()) {
                switch (card.getName()) {
                    case "Maki Rolls 1":
                        rolls++;
                        break;
                    case "Maki Rolls 2":
                        rolls += 2;
                        break;
                    case "Maki Rolls 3":
                        rolls += 3;
                        break;
                }
            }
            if(rolls>mostRolls){
                mostRollPlayers.clear();
                mostRollPlayers.add(player);
                mostRolls = rolls;
            }else if(rolls == mostRolls){
                mostRollPlayers.add(player);
            }
            else if(rolls>secondMostRolls){
                secondMostRollPlayers.clear();
                secondMostRollPlayers.add(player);
                secondMostRolls = rolls;
            }else if(rolls == secondMostRolls){
                secondMostRollPlayers.add(player);
            }
        }
        /*
        if(mostRollPlayers.size()>0) {
            for (Player player : mostRollPlayers) {
                int pointsGiven = 6 / mostRollPlayers.size();
                player.points += pointsGiven;
            }
        }

        //if more than one player tied for most rolls, no points for second
        if(mostRollPlayers.size()==1){
            for (Player player : secondMostRollPlayers) {
                int pointsGiven = 3 / secondMostRollPlayers.size();
                player.points += pointsGiven;
            }
        }*/
        if(mostRollPlayers.contains(this)){
            return 6/mostRollPlayers.size();
        }else if(mostRollPlayers.size()==1&&secondMostRollPlayers.contains(this)){
            return  3/secondMostRollPlayers.size();
        }
        return 0;
    }

    public int getNigiriPoints() {//also calculates wasabi points
        int points=0;
        for (Card card: playerHand.getCards()) {
            switch (card.getName()) {
                case "Egg Nigiri":
                    points += 3;
                    break;
                case "Salmon Nigiri":
                    points += 2;
                    break;
                case "Squid Nigiri":
                    points += 1;
                    break;
            }
        }
        return points;
    }

    public int getSashimiPoints() {
        int count =0;
        for (Card card:playerHand.getCards()){
            if(card.getName().equals("Sashimi")){
                count += 1;
            }
        }
        return count/3;
    }

    public int getTempuraPoints() {
        int count =0;
        for (Card card:playerHand.getCards()){
            if(card.getName().equals("Tempura")){
                count += 1;
            }
        }
        return count/2;
    }

    public int getWasabiPoints(){
        int points =0;
        int wasabis = 0;//tracks number of unused wasabis
        for (Card card:playerHand.getCards()){
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
