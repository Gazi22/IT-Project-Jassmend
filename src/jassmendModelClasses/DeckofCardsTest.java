package jassmendModelClasses;

import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.property.SimpleIntegerProperty;

//Author Davide Seabra

class DeckofCardsTest {
	private final ArrayList<Card> cards = new ArrayList<>();
	private final ArrayList<Card> handCards = new ArrayList<>();
	ArrayList<Card> cardsTotal = new ArrayList<>();
    private final SimpleIntegerProperty cardsRemaining = new SimpleIntegerProperty();
    public static final int HAND_SIZE = 9;
    
	public static void main(String[] args)
	{
	System.out.println("Testing the DeckOfCards Class:");
	DeckofCardsTest test = new DeckofCardsTest();
		
		test.deckOfCardsConstructor();
		test.dealCards();
		test.dealHands();
		test.sortCollection();
		
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



	private void sortCollection()
	{
		System.out.println("\n******* SORT COLLECTION: *******");

		cards.clear();

		// Add all 36 cards
		for (Card.Suit suit : Card.Suit.values()) {
			for (Card.Rank rank : Card.Rank.values()) {
				Card card = new Card(suit, rank);
				cards.add(card);
				card.trumpfHerzSuitValue();
			}
		}
		Collections.shuffle(cards);
		cardsRemaining.setValue(cards.size());

		Collections.sort(cards);
		Collections.reverse(cards);
		System.out.println(handCards+" "+"cards size: "+cards.size()+cards);




		int trumpfYN=0;


			Card card1=new Card(Card.Suit.Herz, Card.Rank.Koenig);

			Card card2=new Card(Card.Suit.Herz, Card.Rank.Ass);

			Card card3=new Card(Card.Suit.Kreuz, Card.Rank.Sieben);

			Card card4=new Card(Card.Suit.Herz, Card.Rank.Acht);


			cardsTotal.add(card1);
			cardsTotal.add(card2);
			cardsTotal.add(card3);
			cardsTotal.add(card4);


		for(int y =0;y<4;y++) {
			if (cardsTotal.get(y).toString().startsWith("Herz")) {
				trumpfYN = 1;

			}
		}


		if (trumpfYN==0){
			if(cardsTotal.get(0).toString().startsWith("Kreuz")){
				for(Card card:cardsTotal){
					card.firstCardKreuzSuitValue();
				}}

			else if(cardsTotal.get(0).toString().startsWith("Herz")){
				for(Card card:cardsTotal){
					card.firstCardHerzSuitValue();
				}}

			else if(cardsTotal.get(0).toString().startsWith("Schaufel")){
				for(Card card:cardsTotal){
					card.firstCardSchaufelSuitValue();
				}}

			else if(cardsTotal.get(0).toString().startsWith("Ecke")){
				for(Card card:cardsTotal){
					card.firstCardEckeSuitValue();
				}}
		}

		else for(Card card:cardsTotal){
			card.trumpfHerzSuitValue();
		}








		Collections.sort(cardsTotal);
		Collections.reverse(cardsTotal);
		cardsTotal.get(0).compareTo(cardsTotal.get(2));
		cardsTotal.get(2).compareTo(cardsTotal.get(0));
		String username2="";
		System.out.println(cardsTotal.toString()+"                   "+cardsTotal.get(0).toString());
		System.out.println(cardsTotal.get(0).getSuitValue()+cardsTotal.get(1).getSuitValue());
		System.out.println(cardsTotal.get(3).getRank());



	}






	
	
	
	
	
}
