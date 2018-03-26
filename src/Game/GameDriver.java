package Game;

import Cards.Card;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class GameDriver {

    //create the hands
    private Vector<Player> playerList;
    private int numOfPlayers = 4;

    public void initialize(int numOfPlayers) {
        //choose players and hand size
        numOfPlayers = 4;
        int handSize = 8;

        playerList = new Vector<>(numOfPlayers);
        //create a deck
        Deck deck = new Deck();


        //deal the hands
        for (int i = 0; i < numOfPlayers; i++) {
            for (int j = 0; j < handSize; j++) {
//                playerList.get(i).getPlayerHand().addCard(deck.deal());
            }
        }
    }

    public void chooseCard(Player player, Card c) {
        player.chooseCard(c);
    }


    public void calculatePoints(Vector<Player> playerList, int roundNum) {
        int points = 0;

        calculateMakiPoints(playerList);
        for (int i = 0; i < numOfPlayers; i++) {
            Player currentPLayer = playerList.get(i);
            points += currentPLayer.dumplingPoints();
            points += currentPLayer.nigiriPoints();
            points += currentPLayer.sashimiPoints();
            points += currentPLayer.tempuraPoints();
            currentPLayer.setTotalPoints(points);
        }
        if (roundNum == 3) {
            calculatePuddingPoints(playerList);
        }
    }

    public void calculatePuddingPoints(Vector<Player> playerList) {
        int points = 0;
        int high = 0;
        int low = 1000;
        Vector<Player> highPlayer = new Vector<>();
        Vector<Player> lowPlayer = new Vector<>();


        //find highest and lowest amount of puddings
        for (int i = 0; i < numOfPlayers; i++) {
            Player currentPlayer = playerList.get(i);
            int puddings = playerList.get(i).getPudding();
            if (puddings > high) {
                highPlayer.clear();
                highPlayer.add(currentPlayer);
            } else if (puddings == high) {
                highPlayer.add(currentPlayer);
            }

            if (puddings < low) {
                lowPlayer.clear();
                lowPlayer.add(currentPlayer);
            } else if (puddings == low) {
                lowPlayer.add(currentPlayer);
            }
        }

        if (highPlayer.size() == numOfPlayers) {//everyone had the same amount of pudding
            return;
        }

        points = 6 / highPlayer.size();
        for (Player aHighPlayer : highPlayer) {
            aHighPlayer.setTotalPoints(points);
        }
        points = -6 / highPlayer.size();
        for (Player aLowPlayer : lowPlayer) {
            aLowPlayer.setTotalPoints(points);
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

        int numOfPlayers = ThreadLocalRandom.current().nextInt(2, 5);
        Vector<Player> testPlayers = new Vector<>();
        for (int i = 0; i < numOfPlayers; i++) {
            testPlayers.add(new Player(String.valueOf(i), String.valueOf(i * 5)));
            testPlayers.get(i).setMakiCount(ThreadLocalRandom.current().nextInt(0, 7));
        }

        calculateMakiPoints(testPlayers);
        for (Player playa : testPlayers) {
            System.out.println("Name: " + playa.getName() + " IP: " + playa.getIP() + " MakiCount: " + playa.getMakiCount()
                    + " Points: " + playa.getRoundPoints());
        }

    }

}
