package jassmendView;

import jassmendModel.Card;
import jassmendModel.Player;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerPane extends VBox {
	private Label lblName = new Label();
	private Label lblScore = new Label("Score:");
	private HBox cardBox = new HBox();
	
	// Link to player object
    private Player player;

    
    public PlayerPane() {
    	super();
    	
    	this.getChildren().addAll(cardBox,lblName, lblScore);
    	
    	for (int i = 0; i < 9; i++) {
    		Button btnCard = new CardView();
    		cardBox.getChildren().add(btnCard);
    		btnCard.getStyleClass().add("button");
    		cardBox.setSpacing(2);
    		
    		}
    }


    public void setPlayer (Player player) {
    	this.player = player;
    	updatePlayerDisplay();
    }


	public void updatePlayerDisplay() {
    	//added new fx thread
		Platform.runLater(new Runnable() {
			@Override public void run() {
				lblName.setText(Player.getPlayerName());
				for (int i = 0; i < Player.HAND_SIZE; i++) {
					Card card = null;
					if (player.getCards().size() > i) card = player.getCards().get(i);
					{

						CardView cl = (CardView) cardBox.getChildren().get(i);
						cl.setCard(card);
					}
				}
			}
	});



	}


}

