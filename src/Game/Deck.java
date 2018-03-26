package Game;

import Cards.*;

import java.util.*;

public class Deck {

    private Stack<Card> deck = new Stack<>();

    Deck() {
        // create a vector, shuffle it, then populate the stack
        Vector<Card> tmp = new Vector<>();
        for (int i = 0; i < 4; i++) {
            tmp.add(new Chopsticks("Chopsticks"));
        }
        for (int i = 0; i < 14; i++) {
            tmp.add(new Dumpling("Dumpling"));
        }
        for (int i = 0; i < 5; i++) {
            tmp.add(new EggNigiri("Egg Nigiri"));
        }
        for (int i = 0; i < 6; i++) {
            tmp.add(new MakiRoll1("Maki Roll 1"));
        }
        for (int i = 0; i < 12; i++) {
            tmp.add(new MakiRoll2("Maki Roll 2"));
        }
        for (int i = 0; i < 8; i++) {
            tmp.add(new MakiRoll3("Maki Roll 3"));
        }
        for (int i = 0; i < 10; i++) {
            tmp.add(new Pudding("Pudding"));
        }
        for (int i = 0; i < 10; i++) {
            tmp.add(new SalmonNigiri("Salmon Nigiri"));
        }
        for (int i = 0; i < 14; i++) {
            tmp.add(new Sashimi("Sashimi"));
        }
        for (int i = 0; i < 5; i++) {
            tmp.add(new SquidNigiri("Squid Nigiri"));
        }
        for (int i = 0; i < 14; i++) {
            tmp.add(new Tempura("Tempura"));
        }
        for (int i = 0; i < 6; i++) {
            tmp.add(new Wasabi("Wasabi"));
        }

        Collections.shuffle(tmp);
        for (Card card : tmp) {
            deck.push(card);
        }
    }

    public Card[] getCards(int num)  {
        Card[] tmp = new Card[num];
        for (int i = 0; i < num; i++) {
            tmp[i] = deck.pop();
        }
        return tmp;
    }
}
