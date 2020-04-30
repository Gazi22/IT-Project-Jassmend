package jassmendModel;

import java.util.ArrayList;

import jassmendPackage.DeckOfCards;
import jassmendPackage.Player;
import jassmendMain.JassmendMain;
import jassmendView.JassmendGameView;

public class JassmendModel {
	 
		private final ArrayList<Player> players = new ArrayList<>();
		private DeckOfCards deck;
		
		public JassmendModel() {
			for (int i = 0; i < JassmendMain.NUM_PLAYERS; i++) {
				players.add(new Player("Player " + i, 0));
			}
			
			deck = new DeckOfCards();
		}
		
		public Player getPlayer(int i) {
			return players.get(i);
		}
		
		public DeckOfCards getDeck() {
			return deck;
		}
		/*
		// method for getting the text from the TextFields and putting them in the labels
		public void generatePlayers() {
			for (int i = 0; i < JassmendMain.NUM_PLAYERS; i++) {
				players.add(new Player(JassmendGameView.arrayTextFields[i].getText()));
			}
		}
		
		// method for evaluating the winner or loser of the round
		public ArrayList<Player> evaluateWinner() {
			ArrayList<Player> highestPlayers = new ArrayList<>();
			for (Player p : players) {
				if (highestPlayers.isEmpty()) {
					highestPlayers.add(p);
				} else {
					if (p.compareTo(highestPlayers.get(0)) > 0) {
						highestPlayers.clear();
						highestPlayers.add(p);
					} else if (p.compareTo(highestPlayers.get(0)) == 0) {
						highestPlayers.add(p);
					}
				}
			}
			return highestPlayers;
		}*/
	 }