package Game;

import Cards.Card;

import java.io.Serializable;
import java.util.Vector;

public class Hand implements Serializable{
    private Vector<Card> cards;

    public Hand(Vector<Card> cards) {
        this.cards = cards;
    }

    public Hand() {
        this.cards = new Vector<>();
    }

    public Vector<Card> getCards() {
        return cards;
    }

    public void clearCards() {
        cards = new Vector<>();
    }

    public void setCards(Vector<Card> cards) {
        this.cards = cards;
    }


    public Card getCard(int i) {
        return cards.get(i);
    }

    public Card selectAndRemoveCard(Card card) {
        int cardIndex = this.cards.indexOf(card);
        return this.cards.remove(cardIndex);
    }


    public void removeCard(Card c) {
        cards.remove(c);
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public String toString() {
        String contents = "";
        for (int i = 0; i < cards.size(); i++) {
            contents += cards.get(i).getName() + " \t ";
        }
        return contents;
    }


}
