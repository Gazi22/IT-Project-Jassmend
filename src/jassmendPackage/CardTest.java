package jassmendPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {

public static void main (String [] args)	
{
	System.out.println("Testing the Card class:");
	CardTest test = new CardTest();
	
	test.cardToString();
	test.cardEquals();
	
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
	
	Card card1 = new Card(Card.Suit.Clubs,Card.Rank.Ten);
	Card card2 = new Card(Card.Suit.Hearts, Card.Rank.Eight);
	System.out.println(card1.toString()+
			((card1.equals(card2))?"==":"!=") +
			card2.toString());
}




}
