package jassmendModel;

import static org.junit.jupiter.api.Assertions.*;

// Author: Florian Jäger

import org.junit.jupiter.api.Test;

//Author Davide Seabra

class CardTest {
Card card;
public static void main (String [] args)	
{
	System.out.println("Testing the Card class:");
	CardTest test = new CardTest();
	
	test.cardToString();
	test.cardEquals();
	test.comparisonRank();
	test.comparisonSuit();

	System.out.println();
}

private void cardToString()
{
	System.out.println("\n******* Cards.toString(): *******");
	
	 for (Card.Suit suit : Card.Suit.values()) {
         for (Card.Rank rank : Card.Rank.values()) {
             Card card = new Card(suit, rank);
             System.out.println(card.toString());
     		
         }
         }
}


private void cardEquals() {
	System.out.println("\n******* Cards.equals(): *******");
	
	Card card1 = new Card(Card.Suit.Kreuz,Card.Rank.Acht);
	Card card2 = new Card(Card.Suit.Herz, Card.Rank.Acht);
	System.out.println(card1.toString()+
			((card1.equals(card2))?"==":"!=") +
			card2.toString());
}



private void comparisonRank(){
	System.out.println("\n******* Compariso Rank *******");

	Card card1 = new Card(Card.Suit.Kreuz,Card.Rank.Sieben);
	Card card2 = new Card(Card.Suit.Herz, Card.Rank.Acht);
	System.out.println(card1.compareTo(card2));

}


	private void comparisonSuit(){
		System.out.println("\n******* Comparison Suit*******");

		Card card1 = new Card(Card.Suit.Herz,Card.Rank.Sieben);
		Card card2 = new Card(Card.Suit.Kreuz, Card.Rank.Acht);
		card1.trumpfHerzSuitValue();
		card2.trumpfHerzSuitValue();
		System.out.println(card1.compareTo(card2));

	}


}
