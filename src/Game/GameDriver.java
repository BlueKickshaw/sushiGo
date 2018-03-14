package Game;

import Cards.Card;

import java.util.Vector;

public class GameDriver {

    //create the hands
    static Vector<Hand> playerHands;
    static Vector<Hand> rotatingHands;

    public void playGame(){
        //choose players and hand size
        int numOfPlayers = 4;
        int handSize = 8;

        playerHands = new Vector<>(handSize);
        rotatingHands = new Vector<>(handSize);
        //create a deck
        Deck deck = new Deck();


        int[] players = {0,1,2,3};



        //deal the hands
        for (int i = 0; i <numOfPlayers; i++) {
            rotatingHands.get(i).addCard(deck.deal());
        }
    }

    public void chooseCard(int playerNum, Card c){
        playerHands.get(playerNum).removeCard(c);
        rotatingHands.get(playerNum).addCard(c);
    }

    public int calculatePoints(){}

    public int calulatePuddingPoints(){}

}
