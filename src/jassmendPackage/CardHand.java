package jassmendPackage;

import java.util.ArrayList;
import java.util.Random;

import jassmendPackage.Card.Rank;
import jassmendPackage.Card.Suit;

public class CardHand {

	private final String EMPTY_HAND = "Empty hand";
	
	private ArrayList<Card> cards = new ArrayList<>();
	
	//Create random number
	private Random random = new Random();
	
	
	public CardHand() {}
	
	
	public CardHand(int [] deckValues)
	{
		for (int i=0;i<deckValues.length;i++)
		{
			Card card = new Card(null, null);
			cards.add(card);
		}
	}



	public void addCard(Card card)
	{
		cards.add(card);
	}
	
	
	public int getSize() {
		return cards.size();
	}
	
	
	
	public boolean hasRank ()
	{
		for (Card card:cards)
	{	
		if (card.getRank()!=null) {
			return true;
		}
	}
	return false;
	}
	
	public boolean hasSuit ()
	{
		for (Card card:cards)
	{	
		if (card.getSuit()!=null) {
			return true;
		}
	}
	return false;
	}
	
	public Card playCardAt (int index) {
		int sizeHand = cards.size();
		if(index>sizeHand -1) {
			return null;
		}
		Card card = cards.get(index);
		cards.remove(index);
		return card;
	}
	
	public Card playCard (Suit suit, Rank rank) {
		int sizeHand = cards.size();
		for(int i=0;i<sizeHand;i++) {
			Card card = cards.get(i);
			if(card.getRank()==rank&&card.getSuit()==suit) {
				Card playCard =new Card(suit, rank);
				cards.remove(i);
				return playCard;
			}
		}
		return null;
	}	
	
	public Card playCardRandom()
	{
		int sizeHand = cards.size();
		if(sizeHand==0) {
			return null;
		}
		
	int randomNumber = random.nextInt(sizeHand);
	Card card = cards.get(randomNumber);
	cards.remove(randomNumber);
	return card;
	}
		
	public String toString() {
		if(cards.isEmpty()) {
			return EMPTY_HAND;
		}
		
		
		StringBuilder sb = new StringBuilder("");
		for (Card card:cards) {
			sb.append(card.toString());
			sb.append("\n");
	}
		return sb.toString();
	}
	

		
		
		
	}
	

