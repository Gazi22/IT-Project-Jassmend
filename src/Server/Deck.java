package Server;

import jassmendModel.Card;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

//Pokerproject by Bradley Richards
//Author Florian Jäger

public class Deck  {
    private final ArrayList<Card> cards = new ArrayList<>();
    private final SimpleIntegerProperty cardsRemaining = new SimpleIntegerProperty();


    public Deck() {
        shuffle();
    }

    /**
     * How many cards are left in the deck?
     */
    public SimpleIntegerProperty getCardsRemainingProperty() {
        return cardsRemaining;
    }
    public int getCardsRemaining() {
        return cardsRemaining.get();
    }

    /**
     * Gather all 36 cards, and shuffle them
     */
    public void shuffle() {
        // Remove all cards
        cards.clear();

        // Add all 36 cards
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }

        // Shuffle
        Collections.shuffle(cards);
        cardsRemaining.setValue(cards.size());
    }

    /**
     * Take one card from the deck and return it
     *
     * This is an example of conditional assignment
     */
    public Card dealCard() {
        Card card = (cards.size() > 0) ? cards.remove(cards.size()-1) : null;
        cardsRemaining.setValue(cards.size());

        return card;

    }

    public boolean isEmpty() {
        return cards.size() == 0;
    }






}
