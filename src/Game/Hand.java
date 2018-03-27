package Game;

import Cards.Card;

import java.util.Vector;

public class Hand {
    private Vector<Card> cards;

    public Hand(Vector<Card> cards) {

    }

    public Vector<Card> getCards() {
        return cards;
    }


    public void setCards(Vector<Card> cards) {
        this.cards = cards;
    }

    public Card getCard(int i) {
        return cards.get(i);
    }

    public void removeCard(Card c) {
        cards.remove(c);
    }

    public void addCard(Card c) {
        cards.add(c);
    }
}
