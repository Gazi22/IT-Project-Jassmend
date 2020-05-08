package jassmendView;

import jassmendModel.Card;
import jassmendModel.Player;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerPane extends VBox {
	private Label lblName = new Label();
	private Label lblScore = new Label("Score:");
	private HBox cardBox = new HBox();
	
	// Link to player object
    private Player player1;

    
    public PlayerPane() {
    	super();
    	
    	this.getChildren().addAll(cardBox,lblName, lblScore);
    	
    	for (int i = 0; i < 9; i++) {
    		Button btnCard = new CardView();
    		cardBox.getChildren().add(btnCard);
    		cardBox.setSpacing(2);
    		
    		}
    }


    public void setPlayer (Player player) {
    	this.player1 = player;
    	updatePlayerDisplay();
    }


	public void updatePlayerDisplay() {
	//	lblName.setText(player1.getPlayerName());
		for (int i = 0; i < Player.HAND_SIZE; i++) {
    		Card card = null;
    		if (player1.getCards().size() > i) card = player1.getCards().get(i);
			{
    		CardView cl = (CardView) cardBox.getChildren().get(i);
    		cl.setCard(card);
    		}

	}



	}


}

