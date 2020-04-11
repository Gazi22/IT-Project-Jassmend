package jassmendPackage;

import java.util.ArrayList;

public class JassmendModel {
	 
		private final ArrayList<Player> players = new ArrayList<>();
		private DeckOfCards deck;
		
	/**	public JassmendModel() {
			for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
				players.add(new Player("Player " + i));
			}
			
			deck = new DeckOfCards();
		}
		*/
		public Player getPlayer(int i) {
			return players.get(i);
		}
		
		public DeckOfCards getDeck() {
			return deck;
		}
	 }