package Game;

import Cards.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class GameDriver {

    //create the hands
    private  Vector<Player> playerList;
    private int numOfPlayers = 4;

    public void initialize(int numOfPlayers){
        //choose players and hand size
        numOfPlayers = 4;
        int handSize = 8;

        playerList = new Vector<>(numOfPlayers);
        //create a deck
        Deck deck = new Deck();




        //deal the hands
        for (int i = 0; i <numOfPlayers; i++) {
            for (int j = 0; j < handSize; j++) {
//                playerList.get(i).getPlayerHand().addCard(deck.deal());
            }
        }
    }

    public void chooseCard(Player player, Card c){
        player.chooseCard(c);
    }



    public void calculatePoints(Vector<Player> playerList, int roundNum) {
        int points = 0;

        calculateMakiPoints(playerList);
        for (int i = 0; i < numOfPlayers; i++) {
            playerList.get(i).calculateDumplingPoints();
            Player currentPLayer = playerList.get(i);
            currentPLayer.calculateNigiriPoints();
            currentPLayer.calculateSashimiPoints();
            currentPLayer.calculateTempuraPoints();
        }
        if (roundNum == 3) {
            calculatePuddingPoints(playerList);
        }
    }


    public static void calculatePuddingPoints(Vector<Player> playerList){
    }





    protected static void calculateMakiPoints(Vector<Player> playerList) {

        Vector<Player> tmpPlayerList = (Vector) playerList.clone();
        Collections.sort(tmpPlayerList, Comparator.comparingInt(Player::getMakiCount));
        Collections.reverse(tmpPlayerList);
        Vector<Player> makiRollPlayers = new Vector<>();
        boolean firstPlaceGiven = false;

        for (int i = 0; i < playerList.size(); i++) {
            if (i + 1 == tmpPlayerList.size()) {
                makiRollPlayers.add(tmpPlayerList.get(i));
                if (firstPlaceGiven) {
                    for (Player player : makiRollPlayers) {
                        player.setRoundPoints(3 / makiRollPlayers.size());
                    }
                    break;
                } else {
                    for (Player player : makiRollPlayers) {
                        player.setRoundPoints(6 / makiRollPlayers.size());
                    }
                    break;
                }
            }
            if (tmpPlayerList.get(i).getMakiCount() > tmpPlayerList.get(i + 1).getMakiCount()) {
                makiRollPlayers.add(tmpPlayerList.get(i));
                if (!firstPlaceGiven && makiRollPlayers.size() > 0) {
                    for (Player player : makiRollPlayers) {
                        player.setRoundPoints(6 / makiRollPlayers.size());
                    }
                    firstPlaceGiven = true;
                    if (makiRollPlayers.size() > 1) {
                        break;
                    }
                    makiRollPlayers.clear();
                } else if (firstPlaceGiven) {
                    for (Player player : makiRollPlayers) {
                        player.setRoundPoints(3 / makiRollPlayers.size());
                    }
                    break;
                }
            } else if (tmpPlayerList.get(i).getMakiCount() == tmpPlayerList.get(i + 1).getMakiCount()) {
                makiRollPlayers.add(tmpPlayerList.get(i));
            }

        }
        for (Player player : tmpPlayerList) {
            player.setMakiCount(0);
        }
    }


    public static void main(String[] args) {

        int numOfPlayers = ThreadLocalRandom.current().nextInt(4, 5);
        Vector<Player> testPlayers = new Vector<>();
        Vector<Card> testCards1 = new Vector<>();
        Vector<Card> testCards2 = new Vector<>();
        Vector<Card> testCards3 = new Vector<>();
        Vector<Card> testCards4 = new Vector<>();
        testCards1.add(new Pudding());
        testCards1.add(new Pudding());
        testCards1.add(new Pudding());
        for (int i = 0; i < numOfPlayers; i++) {
            testPlayers.add(new Player(String.valueOf(i), String.valueOf((i + 1) * 2)));
            testPlayers.get(i).setHand(testCards1);
        }

        testCards2.add(new Pudding());
        testCards2.add(new Pudding());

        testPlayers.get(0).setHand(testCards2);

        for (Player playa : testPlayers) {
            calculatePuddingPoints(testPlayers);
            System.out.println(playa);
        }

    }

}
