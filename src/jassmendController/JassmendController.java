package jassmendController;

import jassmendModel.JassmendModel;
import jassmendView.JassmendView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class JassmendController {
	
	private JassmendModel model;
	private JassmendView view;
	
	public JassmendController(JassmendModel model, JassmendView view) {
		this.model = model;
		this.view = view;
		
		//view.getShuffleButton().setOnAction( e -> shuffle() );
		//view.getDealButton().setOnAction( e -> deal() );
	}
	


    /**
     * Remove all cards from players hands, and shuffle the deck
     */
   /** private void shuffle() {
    	for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
    		Player p = model.getPlayer(i);
    		p.discardHand();
    		PlayerPane pp = view.getPlayerPane(i);
    		pp.updatePlayerDisplay();
    	}

    	model.getDeck().shuffle();
    }
    */
	
    /**
     * Deal each player five cards, then evaluate the two hands
     
    private void deal() {
    	int cardsRequired = PokerGame.NUM_PLAYERS * Player.HAND_SIZE;
    	DeckOfCards deck = model.getDeck();
    	if (cardsRequired <= deck.getCardsRemaining()) {
        	for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
        		Player p = model.getPlayer(i);
        		p.discardHand();
        		for (int j = 0; j < Player.HAND_SIZE; j++) {
        			Card card = deck.dealCard();
        			p.addCard(card);
        		}
        		
        		PlayerPane pp = view.getPlayerPane(i);
        		pp.updatePlayerDisplay();
        	}
    	} else {
            Alert alert = new Alert(AlertType.ERROR, "Not enough cards - shuffle first");
            alert.showAndWait();
    	}
    }
    */
}