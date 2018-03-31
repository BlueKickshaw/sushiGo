package Game;

import Cards.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Time;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class GameDriver implements Runnable {

    //create the hands
    private Vector<Player> playerList = null;
    private int playerCount;
    private Deck deck;
    private int roundNum = 0;
    private Player headPlayer;
    private Vector<Vector<ImageView>> rotatingImages = new Vector<>();
    private Vector<Vector<ImageView>> handImages = new Vector<>();
    private Image cardBack = new Image("/Game/CardImages/Cardback.jpg");
    Image rotatedCardBack = new Image("/Game/CardImages/Cardback_Rotated.jpg");
    private Turn turn;
    private Thread turnHandler;
    boolean flag = false;

    public GameDriver(int playerCountArg, String[] playerNames, String[] playerIPs) {
        playerCount = playerCountArg;

        deck = new Deck();
        playerList = new Vector<>(playerCount);
        for (int i = 0; i < playerCount; i++) {//create players but doesn't deal hands
            playerList.add(new Player(playerNames[i], playerIPs[i]));
        }

    }

    public GameDriver(Vector<Player> playerList, Vector<Vector<ImageView>> rotatingImages,
                      Vector<Vector<ImageView>> handImages) {
        this.playerList = playerList;
        headPlayer = playerList.get(0);//TODO not hardcoded
        this.rotatingImages = rotatingImages;
        this.handImages = handImages;
        if (this.playerList != null && this.playerList.size() > 0 && this.playerList.size() <= 4) {
            playerCount = this.playerList.size();
            deck = new Deck();
        } else {
            System.err.println("Invalid Vector");
        }
    }

    private void turn() {
        turn = new Turn(headPlayer, rotatingImages.get(0));
        turnHandler = new Thread(turn);
        turnHandler.start();
    }


    public void run() {
        while (roundNum < 3) {
            while (headPlayer.getHand().getCards().size() < 10) {
                if (!flag) {
                    startOfRound();
                    flag = true;
                }
                populateImages(rotatingImages.get(0));
                for (int i = 1; i < rotatingImages.size(); i++) {
                    populateCardBacks(rotatingImages.get(i), cardBack);
                }
                turn();

                try {
                    turnHandler.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Game run thread broken");
                }
                populateImages(rotatingImages.get(0));
                populateCardBacks(rotatingImages.get(1), cardBack);
                playerList.get(1).getHand().addCard(playerList.get(1).getRotatingHand().selectAndRemoveCard(playerList.get(1).getRotatingHand().getCard(0)));
                int tmpIndex = 0;
                for (Player player : playerList) {
                    player.setHandImages(player, handImages.get(tmpIndex++));

                }
            }
            roundNum++;
            flag = false;
            if (roundNum < 3) {
                headPlayer.getHand().clearCards();
            }
            int tmpIndex = 0;
            for (Player player : playerList) {
                player.setHandImages(player, handImages.get(tmpIndex++));

            }
        }
    }

    public void startOfRound() {
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

    protected void populateImages(Vector<ImageView> images) {
        headPlayer.populateImages(images);
    }

    protected void populateCardBacks(Vector<ImageView> images, Image cardBackType) {
        headPlayer.populateCardBacks(images, cardBackType);
    }

    /* updateScore(){
         Vector<Player> clonePlayerList = (Vector) players.clone();
         clonePlayerList.sort(Comparator.comparingInt(Player::getTotalPoints));
         Collections.reverse(clonePlayerList);
         firstPlaceText.setText(clonePlayerList.get(0).getName() + "     " + clonePlayerList.get(0).getTotalPoints() + " Total Points");
         secondPlaceText.setText(clonePlayerList.get(1).getName() + "     " + clonePlayerList.get(1).getTotalPoints() + " Total Points");

     }*/
    public static void main(String[] args) {

    }

}
