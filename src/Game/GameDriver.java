package Game;

import Cards.Card;

import java.util.Vector;

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


    public void calculatePoints(Vector<Player> playerList, int roundNum){
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
        if(roundNum == 3){
            calculatePuddingPoints(playerList);
        }
    }

    public void calculatePuddingPoints(Vector<Player> playerList){
        int points =0;
        int high=0;
        int low=1000;
        Vector<Player> highPlayer = new Vector<>();
        Vector<Player> lowPlayer = new Vector<>();

        //find highest and lowest amount of puddings
        for (int i = 0; i < numOfPlayers; i++) {
            Player currentPlayer = playerList.get(i);
            int puddings = playerList.get(i).getPudding();
            if(puddings>high){
                highPlayer.clear();
                highPlayer.add(currentPlayer);
            }else if (puddings==high){
                highPlayer.add(currentPlayer);
            }

            if(puddings<low){
                lowPlayer.clear();
                lowPlayer.add(currentPlayer);
            }else if (puddings==low){
                lowPlayer.add(currentPlayer);
            }
        }

        if(highPlayer.size()==numOfPlayers){//everyone had the same amount of pudding
            return;
        }

        points = 6 / highPlayer.size();
        for (Player aHighPlayer : highPlayer) {
            aHighPlayer.addPoints(points);
        }
        points = -6/ highPlayer.size();
        for (Player aLowPlayer : lowPlayer) {
            aLowPlayer.addPoints(points);
        }
    }

}
