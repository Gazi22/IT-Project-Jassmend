package Server.message;

import Server.Client;
import Server.Gamelobby;
import client_v0.ClientModel;
import client_v0.GameView;
import jassmendMain.JassmendMain;
import jassmendModel.Card;
import jassmendModel.Player;
import jassmendView.PlayerPane;
import javafx.scene.control.Alert;

import java.util.ArrayList;

/**
 * Add a user as a member of a gamelobby.
 */
public class DealCards extends Message {
	private String token;
	private String name;
	private String username;





	public DealCards(String[] data) {
		super(data);
		this.token = data[1];
		this.name = data[2];
		this.username = data[3];
	}

	
	
	/**
	 * The owner of a gamelobby can add anyone. A user can add themselves to any
	 * public gamelobby. Otherwise, no one can add anyone.
	 */
	@Override
	public void process(Client client) {
		
		boolean result = false;

		String [] hand= new String[9];
		if (client.getToken().equals(token)) {
			Gamelobby gamelobby = Gamelobby.exists(name);

			gamelobby.deal();
			for(int x = 0;x<9;x++){
				hand[x]=(gamelobby.getPlayerHand(x).toString());
			}
				gamelobby.clearPlayerHand();


		}



	String [] gameInfo = new String[4];
	gameInfo[1]=this.token;
	gameInfo[2]=this.name;
	gameInfo[3]="PlayerHand"+"|"+hand[0]+"|"+hand[1]+"|"+hand[2]+"|"+hand[3]+"|"+hand[4]+"|"+hand[5]+"|"+hand[6]+"|"+hand[7]+"|"+hand[8];

	SendGameMessage msgGame=new SendGameMessage(gameInfo);
	if (result = true){
		msgGame.process(client);
		}
	else client.send(new Result(result));
		
}
}
