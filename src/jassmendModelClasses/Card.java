package jassmendModelClasses;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

// Code from Poker project || adjustements by Florian Jäger, Davide Seabra

public class Card implements Collection <Card>, Serializable, Comparable<Card> {
	int herzValue =0;
	int eckeValue =0;
	int schaufelValue =0;
	int kreuzValue =0;

	@Override
	public int compareTo(Card o) {
		if (this.getSuitValue() < o.getSuitValue()) {
			return -1;
		} else if (this.getSuitValue() > o.getSuitValue()) {
			return 1;
		} else {

// suit is identical: compare number
			if (this.getSuitValue() == 3&&o.getSuitValue()==3) {
				if (this.rank.ordinal() == 5) {
					return 1;
				}else if (o.rank.ordinal() == 5) {
					return -1;
				}else if (this.rank.ordinal() == 3 && o.rank.ordinal() != 5) {
					return 1;
				}else if (o.rank.ordinal() == 3 && this.rank.ordinal() != 5) {
					return -1;
				} else if (this.rank.ordinal() > o.rank.ordinal()) {
					return 1;
				} else if (this.rank.ordinal() < o.rank.ordinal()) {
					return -1;
				} else {
					return 0;
			}


			} else {
				if (this.rank.ordinal() < o.rank.ordinal()) {
					return -1;
				} else if (this.rank.ordinal() > o.rank.ordinal()) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}


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
    }

    public enum Rank { Sechs, Sieben, Acht, Neun, Zehn, Bube,Dame,Koenig,Ass;
        @Override
        public String toString() {
            String str = "Ass";  // Assume we have an Ass, then cover all other cases
            // Get ordinal value, which ranges from 0 to 8
            int ordinal = this.ordinal();
            if (ordinal <= 4) {
                str = Integer.toString(ordinal+6);
			} else if (ordinal == 5) { // Bube
                str = "Bube";
            } else if (ordinal == 6) { // Dame
                str = "Dame";
            }else if (ordinal == 7) { // König
				str = "Koenig";
			}
            return str;
        }
    }

    private final Suit suit;
    private final Rank rank;

	          
	  
    
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
     }


    public Suit getSuit() {
        return suit;
    }
    

    public Rank getRank() {
        return rank;
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

public void completeSuitValue(){
		herzValue =3;
		eckeValue =3;
		schaufelValue =3;
		kreuzValue =3;
	}


	public void trumpfHerzSuitValue(){
		herzValue =3;
		eckeValue =0;
		schaufelValue =0;
		kreuzValue =0;
	}
	public void trumpfKreuzSuitValue(){
		herzValue =0;
		eckeValue =0;
		schaufelValue =0;
		kreuzValue =3;
	}
	public void trumpfEckeSuitValue(){
		herzValue =0;
		eckeValue =3;
		schaufelValue =0;
		kreuzValue =0;
	}
	public void trumpfSchaufelSuitValue(){
		herzValue =0;
		eckeValue =0;
		schaufelValue =3;
		kreuzValue =0;
	}

	public void firstCardHerzSuitValue(){
		herzValue =2;
		eckeValue =0;
		schaufelValue =0;
		kreuzValue =0;
	}
	public void firstCardKreuzSuitValue(){
		herzValue =0;
		eckeValue =0;
		schaufelValue =0;
		kreuzValue =2;
	}
	public void firstCardEckeSuitValue(){
		herzValue =0;
		eckeValue =2;
		schaufelValue =0;
		kreuzValue =0;
	}
	public void firstCardSchaufelSuitValue(){
		herzValue =0;
		eckeValue =0;
		schaufelValue =2;
		kreuzValue =0;
	}




	public int getSuitValue() {
		if (suit == suit.Herz) {
			return herzValue;}
		if (suit == suit.Kreuz) {
			return kreuzValue;}
		if (suit == suit.Ecke) {
			return eckeValue;}
		if (suit == suit.Schaufel) {
			return schaufelValue;}
		else return 0;
	}

}
