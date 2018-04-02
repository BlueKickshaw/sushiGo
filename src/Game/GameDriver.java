package Game;

import Cards.*;
import server.Network;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.Network;

import java.util.ArrayList;
import java.sql.Time;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class GameDriver implements Runnable {
    private Network network;

    private volatile boolean dataReceived = false;
    public volatile boolean hostTurnEnded = false;

    private Vector<Player> playerList = null;
    private int playerCount;
    private Deck deck;
    private int roundNum = 0;
    public Player headPlayer;
    private Vector<Vector<ImageView>> rotatingImages = new Vector<>();
    private Vector<Vector<ImageView>> handImages = new Vector<>();
    private Image cardBack = new Image("/Game/CardImages/Cardback.jpg");
    Image rotatedCardBack = new Image("/Game/CardImages/Cardback_Rotated.jpg");
    private Turn turn;
    private Thread turnHandler;
    boolean flag = false;
    private Vector<Player> opponents;//Order is top, left, right
    private Vector<Label> scoreLabels;
    private int handSize;
    int indOfHeadPlayer;

    public ArrayList<String> storedPlayerNames = new ArrayList<>();
    public ArrayList<Hand> storedPlayedHands = new ArrayList<>();
    public ArrayList<Hand> storedRotateHands = new ArrayList<>();
    public ArrayList<Integer> storedPlayerNumbers = new ArrayList<>();
    public int passedCards = 0;

    public GameDriver(Vector<Player> playerList, Vector<Vector<ImageView>> rotatingImages,
                      Vector<Vector<ImageView>> handImages, Network network, Vector<Label> scoreLabels) {
        this.network = network;
        this.playerList = playerList;
        this.scoreLabels = scoreLabels;
        // Set ourselves as the head player
        for (Player player : this.playerList) {
            if (network != null && player.getName().equals(network.username)) {
                this.headPlayer = player;
            }
        }
        indOfHeadPlayer = playerList.indexOf(headPlayer);

        this.rotatingImages = rotatingImages;
        this.handImages = handImages;
        if (this.playerList != null && this.playerList.size() > 0 && this.playerList.size() <= 4) {
            playerCount = this.playerList.size();
            // makes the deck consistent for all players
            deck = new Deck(network.client.getLobby().getSeed());
        } else {
            System.err.println("Invalid Vector");
        }
        opponents = new Vector<>(playerList.size());
        for (int i = 0; i < playerList.size() - 1; i++) {
            int j = (i + 1 + indOfHeadPlayer) % playerList.size();
            opponents.add(i, playerList.get(j));
        }

        switch (playerCount) {
            case 2:
                this.handSize = 10;
                break;
            case 3:
                this.handSize = 9;
                break;
            case 4:
                this.handSize = 8;
                break;
        }

    }


    private void turn() {
        network.gameDriver = this;
        turn = new Turn(headPlayer, rotatingImages.get(0), network);
        turnHandler = new Thread(turn);
        turnHandler.start();
    }


    public void run() {
        while (roundNum < 3) {
            // Update the played hands at the beginning of a round
            playerList.get(indOfHeadPlayer).setHandImages(playerList.get(indOfHeadPlayer), handImages.get(0));
            for (int k = 0; k < opponents.size(); k++) {
                opponents.get(k).setHandImages(opponents.get(k), handImages.get(k + 1));
            }


            for (int i = 0; i < handSize; i++) {
                if (!flag) {
                    startOfRound();
                    flag = true;
                }
                populateImages(rotatingImages.get(0));
                for (int j = 1; j < rotatingImages.size(); j++) {
                    if (j > 1) {
                        populateCardBacks(rotatingImages.get(j), rotatedCardBack);
                        continue;
                    }
                    populateCardBacks(rotatingImages.get(j), cardBack);

                }
                turn();

                try {
                    turnHandler.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (!dataReceived) {
                }

                if (network.server == null) {
                    while (!hostTurnEnded) {
                    }
                }
                dataReceived = false;
                hostTurnEnded = false;

                // Populate the images of the head player and each of the players in the game as well
                playerList.get(indOfHeadPlayer).setHandImages(playerList.get(indOfHeadPlayer),
                        handImages.get(0));
                for (int k = 0; k < opponents.size(); k++) {
                    opponents.get(k).setHandImages(opponents.get(k), handImages.get(k + 1));
                }
            }

            // fixes 1 card getting stuck in primary player hand
            populateImages(rotatingImages.get(0));
            // for loop above doesn't quite cut it, not sure why. no time. this fixes it
            for (int j = 1; j < rotatingImages.size(); j++) {
                if (j > 1) {
                    populateCardBacks(rotatingImages.get(j), rotatedCardBack);
                    continue;
                }
                populateCardBacks(rotatingImages.get(j), cardBack);

            }

            Platform.runLater(() -> calculatePoints(playerList, roundNum));
            Platform.runLater(() -> updateScores());
            flag = false;

            // Update the played hands again AFTER the end of a round
            playerList.get(indOfHeadPlayer).setHandImages(playerList.get(indOfHeadPlayer),
                    handImages.get(0));
            for (int k = 0; k < opponents.size(); k++) {
                opponents.get(k).setHandImages(opponents.get(k), handImages.get(k + 1));
            }
        }
    }

    public void receiveEndOfTurnData(Vector<Hand> tableHands, Hand rotatingHand) {
        int iter = 0;
        for (Player player : playerList) {
            player.setHand(tableHands.get(iter).getCards());
            headPlayer.setRotatingHand(rotatingHand.getCards());
            iter++;
        }
        dataReceived = true;
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
        if (playerList.size() == 2) {
            return;
        }//no low player in two player game
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

    protected void populateImages(Vector<ImageView> images) {
        headPlayer.populateImages(images);
    }

    protected void populateCardBacks(Vector<ImageView> images, Image cardBackType) {
        headPlayer.populateCardBacks(images, cardBackType);
    }

    private void updateScores() {
        Vector<Player> clonePlayerList = new Vector<>(playerList);
        clonePlayerList.sort(Comparator.comparingInt(Player::getTotalPoints));
        Collections.reverse(clonePlayerList);
        for (int i = 0; i < playerCount; i++) {

            scoreLabels.get(i).setText(clonePlayerList.get(i).getName() + "     " +
                    clonePlayerList.get(i).getTotalPoints() + " Total Points");
        }

    }

}
