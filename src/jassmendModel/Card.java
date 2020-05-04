package jassmendModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class Card implements Collection <Card>, Serializable {
    public enum Suit { Kreuz, Ecke, Herz, Schaufel;
        @Override
        public String toString() {
            String suit = "";
            switch (this) {
            case Kreuz: suit = "Kreuz"; break;
            case Ecke: suit = "Ecke"; break;
            case Herz: suit = "Herz"; break;
            case Schaufel: suit = "Schaufel"; break;
            }
            return suit;
        }
    };
    
    public enum Rank { Sechs, Sieben, Acht, Neun, Zehn, Bube, Dame, Koenig, Ass;
        @Override
        public String toString() {
            String str = "Ass";  // Assume we have an ace, then cover all other cases
            // Get ordinal value, which ranges from 0 to 8
            int ordinal = this.ordinal();
            if (ordinal <= 4) {
                str = Integer.toString(ordinal+6);
            } else if (ordinal == 5) { // Jack
                str = "Bube";
            } else if (ordinal == 6) { // Queen
                str = "Dame";
            } else if (ordinal == 7) { // King
                str = "Koenig";
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
        return suit.toString() + rank.toString();
    }

	@Override
	public boolean add(Card arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Card> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Card> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}


}
