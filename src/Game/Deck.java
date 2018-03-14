package Game;

import Cards.*;

import java.io.IOException;
import java.util.*;

public class Deck {
    Stack<Card> deck;

    Deck(){
        // create an arraylist, shuffle it, the populate the stack
        ArrayList<Card> tmp;
        try {
            deck.add(new Dumpling());
            deck.add(new Dumpling());

        }catch (IOException e) {
            System.out.println("No dumpling added");
        }
    }

    private Card[] getCards(int num){
        Card[] tmp = new Card[num];
        for (int i = 0; i < num; i++){
            tmp[i] = deck.pop();
        }
        return tmp;
    }

    public static void main(String[] args){
        Deck test = new Deck();
        System.out.println(test.getCards(2)[0].getName());
    }

}
