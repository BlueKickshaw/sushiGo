package Game;

import Cards.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class GameDriver {

    //create the hands
    private Vector<Player> playerList;
    private int playerCount = 4;
    private Deck deck;
    private int roundNum = 0;

    public void initialize(int playerCountArg, String[] playerNames, String[] playerIPs) {
        playerCount = playerCountArg;

        deck = new Deck();
        playerList = new Vector<>(playerCount);
        for (int i = 0; i < playerCount; i++) {//create players but doesn't deal hands
            playerList.add(new Player(playerNames[i], playerIPs[i]));
        }

    }

    public void startOfRound() {
        roundNum++;
        for (Player player : playerList) {
            player.clearHand();//clears player hand
            player.drawHand(deck, playerCount);//populates rotating hand
        }
    }

    public void chooseCard(Player player, Card c) {
        player.chooseCard(c);
    }


    //if no card is chosen in time it will automatically take the left most card.
    public void chooseCard(Player player) {
        player.chooseCard(player.getRotatingHand().getCards().firstElement());
    }


    public static void calculatePoints(Vector<Player> playerList, int roundNum) {
        for (Player player : playerList) {
            player.clearRoundPoints();
            player.updatePuddingCount();
            player.updateDumplingCount();
            player.updateMakiCount();
        }


        calculateMakiPoints(playerList);
        if (roundNum == 3) {
            calculatePuddingPoints(playerList);
        }
        for (Player player : playerList) {
            player.calculateDumplingPoints();
            player.calculateNigiriPoints();
            player.calculateTempuraPoints();
            player.calculateSashimiPoints();
            player.calculateWasabiPoints();
            player.addRoundPointsToTotal();
        }


    }


    public static void calculatePuddingPoints(Vector<Player> playerList) {
        int points;
        int high = -1;
        int low = 1000;
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


        if (highPlayer.size() == playerList.size()) {//everyone had the same amount of pudding
            return;
        }

        points = 6 / highPlayer.size();
        for (Player aHighPlayer : highPlayer) {
            aHighPlayer.addRoundPoints(points);
        }
        points = -6 / lowPlayer.size();
        for (Player aLowPlayer : lowPlayer) {
            aLowPlayer.addRoundPoints(points);
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
        GameDriver gameDriver = new GameDriver();
        String[] names = new String[]{"0","1","2","3"};
        String[] IPs = new String[]{"0","2","4","6"};
        gameDriver.initialize(4, names, IPs);
        gameDriver.startOfRound();
        for(Player player: gameDriver.playerList){
            player.getHand().setCards(player.getRotatingHand().getCards());
        }
        calculatePoints(gameDriver.playerList, gameDriver.roundNum);

        for (Player playa : gameDriver.playerList) {

            System.out.println(playa);
        }
        gameDriver.startOfRound();
        for(Player player: gameDriver.playerList){
            player.getHand().setCards(player.getRotatingHand().getCards());
        }
        calculatePoints(gameDriver.playerList, gameDriver.roundNum);;

        for (Player playa : gameDriver.playerList) {

            System.out.println(playa);
        }
        gameDriver.startOfRound();
        for(Player player: gameDriver.playerList){
            player.getHand().setCards(player.getRotatingHand().getCards());
        }
        calculatePoints(gameDriver.playerList, gameDriver.roundNum);

        for (Player playa : gameDriver.playerList) {

            System.out.println(playa);
        }

    }

}
