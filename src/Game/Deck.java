package Game;

import Cards.*;

import java.io.IOException;
import java.util.*;

public class Deck {
    private Stack<Card> deck = new Stack<>();

    Deck(){
        // create an vector, shuffle it, the populate the stack
        Vector<Card> tmp = new Vector<>();
//        try {
        for (int i = 0; i < 14; i++) {
            tmp.add(new Dumpling("Dumpling " + String.valueOf(i)));
//            tmp.add(new Dumpling());
        }
//        }catch(IOException e){
//            System.out.println("no dumpling created");
//            e.printStackTrace();
//        }
        Collections.shuffle(tmp);
        for (Card card: tmp) {
            deck.push(card);
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

        for (int i = 0; i < 14; i++){
            System.out.println(test.getCards(1)[0].getName());
        }
    }

}
