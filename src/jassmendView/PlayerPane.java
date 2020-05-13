package jassmendView;

import client_v0.ClientController;
import jassmendModel.Card;
import jassmendModel.Player;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PlayerPane extends VBox {
	private Label lblName = new Label();
	private Label lblScore = new Label("");
	private HBox cardBox = new HBox();
	private ClientController clientController;
	ArrayList<Card> cardsHolder = new ArrayList<>();
	
	// Link to player object
    private Player player;

    
    public PlayerPane() {
    	super();
    	
    	this.getChildren().addAll(cardBox,lblScore,lblName);
    	
    	for (int i = 0; i < 9; i++) {
    		Button btnCard = new CardView();
    		cardBox.getChildren().add(btnCard);
    		btnCard.getStyleClass().add("btnCard");
    		cardBox.setSpacing(2);
    		
    		}
    }


    public void setPlayer (Player player) {
    	this.player = player;
    	updatePlayerDisplay();
    }

    public HBox getCardBox(){
    	return cardBox;
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
						if(card!=null) {
							cl.setCard(card);
							cardsHolder.add(card);
						}

					}
				}
			}
	});



	}

	public Card getCardsHolder(int i){
		return cardsHolder.get(i);
	}

	public void setCardsHolder(Card card){
		if ( card!=null) {
			cardsHolder.add(card);
		}

	}

	public int getCardsHolderSize(){
		return cardsHolder.size();
	}

	//Execute when dealCards is executed
	public void clearCardsHolder(){
		cardsHolder.clear();
	}
}

