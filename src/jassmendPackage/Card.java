package jassmendPackage;


public class Card {
    public enum Suit { Clubs, Diamonds, Hearts, Spades;
        @Override
        public String toString() {
            String suit = "";
            switch (this) {
            case Clubs: suit = "clubs"; break;
            case Diamonds: suit = "diamonds"; break;
            case Hearts: suit = "hearts"; break;
            case Spades: suit = "spades"; break;
            }
            return suit;
        }
    };
    
    public enum Rank { Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
        @Override
        public String toString() {
            String str = "ace";  // Assume we have an ace, then cover all other cases
            // Get ordinal value, which ranges from 0 to 8
            int ordinal = this.ordinal();
            if (ordinal <= 4) {
                str = Integer.toString(ordinal+2);
            } else if (ordinal == 5) { // Jack
                str = "jack";
            } else if (ordinal == 6) { // Queen
                str = "queen";
            } else if (ordinal == 7) { // King
                str = "king";
            }
            return str;
        }
    };
    
    private final Suit suit;
    private final Rank rank;
	private final String cardValue = null;
	          
	  
    
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
     }

    //QUELLE ANGEBEN
    public boolean equals(Object other)
    {
    	if(!(other instanceof Card)) {
    		return false;
    	}
    	Card cardOther =(Card)other;
    	if(cardValue !=cardOther.cardValue) {
    		return false;
    	}
    	return true;
    }
    
    
    public Suit getSuit() {
        return suit;
    }
    

    public Rank getRank() {
        return rank;
    }
    
    public String getCardValue()
    {
    	return cardValue;
    }
    
    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }


}
