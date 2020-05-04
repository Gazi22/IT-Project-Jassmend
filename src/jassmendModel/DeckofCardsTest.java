package jassmendModel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

class DeckofCardsTest {
	private final ArrayList<Card> cards = new ArrayList<>();
	private final ArrayList<Card> handCards = new ArrayList<>();
    private final SimpleIntegerProperty cardsRemaining = new SimpleIntegerProperty();
    public static final int HAND_SIZE = 9;
    
	public static void main(String[] args)
	{
	System.out.println("Testing the DeckOfCards Class:");
	DeckofCardsTest test = new DeckofCardsTest();
		
		test.deckOfCardsConstructor();
		test.dealCards();
		test.dealHands();
		
		System.out.println();
		
	}
	
	
	private void deckOfCardsConstructor()
	{
		System.out.println("\n******* DeckOfCards constructor (default):*******");
		
		System.out.println("Standard 36-card deck");
		DeckOfCards deck = new DeckOfCards();
		System.out.println(deck.toString());
	}
	
	
	private void dealCards()
	{
		System.out.println("\n******* Deal cards: *******");
		
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
	        
	        Card card = (cards.size() > 0) ? cards.remove(cards.size()-1) : null;
	        cardsRemaining.setValue(cards.size());
	        System.out.println(" "+card.toString());
	        System.out.println("Card size: "+cards.size());
	        
	        
	    }
		
	
	private void dealHands()
	{
		System.out.println("\n******* Deal hands: *******");
		
		cards.clear();
       
        // Add all 36 cards
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
        Collections.shuffle(cards);
        cardsRemaining.setValue(cards.size());
        
        for (int i=0;i<HAND_SIZE;i++)
        {
        	Card card = (cards.size() > 0) ? cards.remove(cards.size()-1) : null;
	        cardsRemaining.setValue(cards.size());	       
	        if (handCards.size() < HAND_SIZE) handCards.add(card);
        }
	     
        System.out.println(handCards+" "+"cards size: "+cards.size());
		
	}
	 
	
	
	
	
	
	
	
	
}
