package jassmendPackage;

import java.util.ArrayList;

public class Player {
    public static final int HAND_SIZE = 9;
    
    private static String playerName = "";   // This is the ID 
    private int playerScore;
    private final ArrayList<Card> cards = new ArrayList<>();
  
    public Player(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }
    
    public int getPlayerScore() {
    	return playerScore;
    }
       

    public static String getPlayerName() {
        return playerName;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public void addCard(Card card) {
        if (cards.size() < HAND_SIZE) cards.add(card);
    }
    
    public void discardHand() {
        cards.clear();
       
    }
    
    public int getNumCards() {
        return cards.size();
    }
    
    
}
