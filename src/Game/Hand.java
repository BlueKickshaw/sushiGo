package Game;

import Cards.Card;
import Cards.Wasabi;

import java.util.Vector;

public class Hand {
    private Vector<Card> cards;
    private boolean playerHand;

    public Hand(Vector<Card> cards, boolean playerHand) {
        this.cards = cards;
        this.playerHand = playerHand;
    }

    public Hand(int handSize, boolean playerHand) {
        this.cards = new Vector<>(handSize);
        this.playerHand = playerHand;
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

    public boolean isPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(boolean playerHand) {
        this.playerHand = playerHand;
    }

    public void removeCard(Card c) {
        cards.remove(c);
    }

    public void addCard(Card c) {
        cards.add(c);
    }
}
