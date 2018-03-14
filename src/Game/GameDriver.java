package Game;

import Cards.Card;

import java.util.Vector;

public class GameDriver {

    //create the hands
    private static Vector<Player> playerList;
    private static int numOfPlayers;

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
                playerList.get(i).getPlayerHand().addCard(deck.deal());
            }
        }
    }

    public void chooseCard(Player player, Card c){
        player.chooseCard(c);
    }

    public int[] calculatePoints(Vector<Player> playerList){
        int points =0;
        for (int i = 0; i < numOfPlayers; i++) {
            Player currentPLayer = playerList.get(i);
            points += currentPLayer.dumplingPoints();
            points += currentPLayer.makiRollPoints(playerList);
            points += currentPLayer.nigiriPoints();
            points += currentPLayer.sashimiPoints();
            points += currentPLayer.tempuraPoints();
            currentPLayer.addPoints(points);
        }
    }

    public int calulatePuddingPoints(Vector<Player> playerList){
        int points =0;
        int indexOfHighest;
        for (int i = 0; i < numOfPlayers; i++) {
            playerList.get(i).getPudding();//TODO
        }

        return 0;
    }

}
