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
        int points;
        int high=-1;
        int low=1000;
        Vector<Player> highPlayer = new Vector<>();
        Vector<Player> lowPlayer = new Vector<>();

        //find highest and lowest amount of puddings
        for (Player currentPlayer : playerList) {
            int puddings = currentPlayer.getPuddingCount();
            if (puddings > high) {
                highPlayer.clear();
                highPlayer.add(currentPlayer);
                high = puddings;
            } else if (puddings == high) {
                highPlayer.add(currentPlayer);
            }

            if (puddings < low) {
                lowPlayer.clear();
                lowPlayer.add(currentPlayer);
                low = puddings;
            } else if (puddings == low) {
                lowPlayer.add(currentPlayer);
            }
        }



        if(highPlayer.size()==playerList.size()){//everyone had the same amount of pudding
            return;
        }

        points = 6 / highPlayer.size();
        for (Player aHighPlayer : highPlayer) {
            aHighPlayer.addTotalPoints(points);
        }
        points = -6/ lowPlayer.size();
        for (Player aLowPlayer : lowPlayer) {
            aLowPlayer.addTotalPoints(points);
        }
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
                        player.addRoundPoints(3 / makiRollPlayers.size());
                    }
                    break;
                } else {
                    for (Player player : makiRollPlayers) {
                        player.addRoundPoints(6 / makiRollPlayers.size());
                    }
                    break;
                }
            }
            if (tmpPlayerList.get(i).getMakiCount() > tmpPlayerList.get(i + 1).getMakiCount()) {
                makiRollPlayers.add(tmpPlayerList.get(i));
                if (!firstPlaceGiven && makiRollPlayers.size() > 0) {
                    for (Player player : makiRollPlayers) {
                        player.addRoundPoints(6 / makiRollPlayers.size());
                    }
                    firstPlaceGiven = true;
                    if (makiRollPlayers.size() > 1) {
                        break;
                    }
                    makiRollPlayers.clear();
                } else if (firstPlaceGiven) {
                    for (Player player : makiRollPlayers) {
                        player.addRoundPoints(3 / makiRollPlayers.size());
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
        testCards1.add(new EggNigiri());
        testCards1.add(new Wasabi());
        testCards1.add(new SalmonNigiri());
        testCards1.add(new Wasabi());
        testCards1.add(new SquidNigiri());
        testCards1.add(new Wasabi());
        testCards1.add(new EggNigiri());

        for (int i = 0; i < numOfPlayers; i++) {
            testPlayers.add(new Player(String.valueOf(i), String.valueOf((i + 1) * 2)));
            testPlayers.get(i).setHand(testCards1);
        }




        for (Player playa : testPlayers) {
            playa.calculateNigiriPoints();
            playa.calculateWasabiPoints();
            System.out.println(playa);
        }

    }

}
